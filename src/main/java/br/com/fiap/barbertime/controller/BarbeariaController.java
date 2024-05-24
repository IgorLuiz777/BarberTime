package br.com.fiap.barbertime.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.barbertime.model.Barbearia;
import br.com.fiap.barbertime.repository.BarbeariaRepository;
import br.com.fiap.barbertime.repository.ServicosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/barbearia")
@Slf4j
@Tag(name = "Barbearia", description = "Endpoints para operações relacionadas a barbearias")
public class BarbeariaController {

    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    PagedResourcesAssembler<Barbearia> pagedResourcesAssembler;

    @GetMapping
    @Operation(
        summary = "Listar todas as barbearias",
        description = "Retorna uma lista paginada de todas as barbearias, ordenadas pelo nome em ordem alfabética"
    )
    public PagedModel<EntityModel<Barbearia>> listarBarbearias(
            @Parameter(description = "Página solicitada (começando do 0)", example = "0") @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
    
        Page<Barbearia> page = barbeariaRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(page);
    }

    @GetMapping("/nome")
    @Operation(
        summary = "Buscar uma barbearia pelo nome",
        description = "Retorna uma barbearia com base no nome fornecido"
    )
    public Page<Barbearia> findByNome(
            @RequestParam(required = false) String nome, Pageable pageable) {
        return barbeariaRepository.findByNome(nome, pageable);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar uma barbearia por ID",
        description = "Retorna uma barbearia com base no ID fornecido"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Barbearia encontrada"),
        @ApiResponse(responseCode = "404", description = "Barbearia não encontrada", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    public EntityModel<Barbearia> buscarPorId(
            @Parameter(description = "ID da barbearia a ser buscada", example = "1") @PathVariable Long id) {
        log.info("Buscando barbearia com o ID {}", id);

        var barbearia = barbeariaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Barbearia não encontrada"));

        return barbearia.toEntityModel();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar uma nova barbearia",
        description = "Cria uma nova barbearia com os dados fornecidos no corpo da requisição"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Barbearia cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique o corpo da requisição", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    public Barbearia cadastrarBarbearia(
            @Parameter(description = "Dados da barbearia a ser cadastrada") @RequestBody Barbearia barbearia) {
        log.info("Cadastrando nova barbearia: {}", barbearia);

        return barbeariaRepository.save(barbearia);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar uma barbearia",
        description = "Atualiza uma barbearia existente com os dados fornecidos no corpo da requisição"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Barbearia atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Barbearia não encontrada", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    public Barbearia atualizarBarbearia(
            @Parameter(description = "ID da barbearia a ser atualizada", example = "1") @PathVariable Long id,
            @Parameter(description = "Novos dados da barbearia") @RequestBody Barbearia barbearia) {
        log.info("Atualizando a barbearia com o ID {} para: {}", id, barbearia);
        
        if (!barbeariaRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Barbearia não encontrada: ID " + id);
        }

        barbearia.setId(id);
        return barbeariaRepository.save(barbearia);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Excluir uma barbearia",
        description = "Exclui uma barbearia com base no ID fornecido"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Barbearia excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Barbearia não encontrada", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @Transactional
    public void excluirBarbearia(
            @Parameter(description = "ID da barbearia a ser excluída", example = "1") @PathVariable Long id) {
        log.info("Excluindo a barbearia com o ID {}", id);

        if (!barbeariaRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Barbearia não encontrada com o ID: " + id);
        }
        servicosRepository.deleteByBarbeariaId(id);
        barbeariaRepository.deleteById(id);
    }

}
