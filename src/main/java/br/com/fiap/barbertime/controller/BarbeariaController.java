package br.com.fiap.barbertime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.barbertime.model.Barbearia;
import br.com.fiap.barbertime.repository.BarbeariaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/barbearia")
@Slf4j
@Tag(name = "Barbearia", description = "Endpoints para operações relacionadas a barbearias")
public class BarbeariaController {

    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @GetMapping
    @Operation(
        summary = "Listar todas as barbearias",
        description = "Retorna uma lista paginada de todas as barbearias, ordenadas pelo nome em ordem alfabética"
    )
    public Page<Barbearia> listarBarbearias(
            @Parameter(description = "Página solicitada (começando do 0)", example = "0") @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return barbeariaRepository.findAll(pageable);
    }

    @Operation(
        summary = "Burcar uma barbearias pelo nome",
        description = "Retorna uma barbearia com base no nome fornecido"
    )
    @GetMapping("/nome")
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
        @ApiResponse(responseCode = "404", description = "Barbearia não encontrada", content = @Content)
    })
    public ResponseEntity<Barbearia> buscarPorId(
            @Parameter(description = "ID da barbearia a ser buscada", example = "1") @PathVariable Long id) {
        log.info("Buscando barbearia com o ID {}", id);
        return barbeariaRepository.findById(id)
                .map(barbearia -> ResponseEntity.ok().body(barbearia))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar uma nova barbearia",
        description = "Cria uma nova barbearia com os dados fornecidos no corpo da requisição"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Barbearia cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique o corpo da requisição", content = @Content)
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
        @ApiResponse(responseCode = "404", description = "Barbearia não encontrada", content = @Content)
    })
    public Barbearia atualizarBarbearia(
            @Parameter(description = "ID da barbearia a ser atualizada", example = "1") @PathVariable Long id,
            @Parameter(description = "Novos dados da barbearia") @RequestBody Barbearia barbearia) {
        log.info("Atualizando a barbearia com o ID {} para: {}", id, barbearia);
        verificarExistenciaBarbearia(id);
        barbearia.setId(id);
        return barbeariaRepository.save(barbearia);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Excluir uma barbearia",
        description = "Exclui uma barbearia com base no ID fornecido"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Barbearia excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Barbearia não encontrada", content = @Content)
    })
    public void excluirBarbearia(
            @Parameter(description = "ID da barbearia a ser excluída", example = "1") @PathVariable Long id) {
        log.info("Excluindo a barbearia com o ID {}", id);
        verificarExistenciaBarbearia(id);
        barbeariaRepository.deleteById(id);
    }

    private void verificarExistenciaBarbearia(Long id) {
        if (!barbeariaRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Barbearia não encontrada com o ID: " + id);
        }
    }
}
