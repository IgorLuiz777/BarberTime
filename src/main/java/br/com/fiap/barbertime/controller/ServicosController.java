package br.com.fiap.barbertime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.barbertime.model.Servicos;
import br.com.fiap.barbertime.repository.ServicosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/servicos")
@Tag(name = "Serviços", description = "Endpoints para operações relacionadas a serviços")
public class ServicosController {
    
    @Autowired
    private ServicosRepository servicosRepository;

    @GetMapping
    @Operation(
        summary = "Listar todos os serviços",
        description = "Retorna uma lista paginada de todos os serviços, ordenados pelo nome em ordem alfabética"
    )
    public Page<Servicos> listarServicos(
            @Parameter(description = "Página solicitada (começando do 0)", example = "0") @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return servicosRepository.findAll(pageable);
    }

    @GetMapping("/nome")
    @Operation(
        summary = "Buscar um serviço pelo nome",
        description = "Retorna um serviço com base no nome fornecido"
    )
    public Page<Servicos> buscarPorNome(
            @Parameter(description = "Nome do serviço a ser buscado") @RequestParam(required = false) String nome,
            @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return servicosRepository.findByNome(nome, pageable);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar um serviço pelo ID",
        description = "Retorna um serviço com base no ID fornecido"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado", content = @Content)
    })
    public ResponseEntity<Servicos> buscarPorId(
            @Parameter(description = "ID do serviço a ser buscado", example = "1") @PathVariable Long id) {
        return servicosRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar um novo serviço",
        description = "Cria um novo serviço com os dados fornecidos no corpo da requisição e já é associado a uma barbearia"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique o corpo da requisição", content = @Content)
    })
    public Servicos cadastrarServico(
            @Parameter(description = "Dados do serviço a ser cadastrado") @RequestBody Servicos servico) {
        return servicosRepository.save(servico);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Excluir um serviço",
        description = "Exclui um serviço com base no ID fornecido"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Serviço excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado", content = @Content)
    })
    public void excluirServico(
            @Parameter(description = "ID do serviço a ser excluído", example = "1") @PathVariable Long id) {
        verificarExistenciaServico(id);
        servicosRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar um serviço",
        description = "Atualiza um serviço existente com os dados fornecidos no corpo da requisição"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado", content = @Content)
    })
    public Servicos atualizarServico(
            @Parameter(description = "ID do serviço a ser atualizado", example = "1") @PathVariable Long id,
            @Parameter(description = "Novos dados do serviço") @RequestBody Servicos servico) {
        verificarExistenciaServico(id);
        servico.setId(id);
        return servicosRepository.save(servico);
    }

    private void verificarExistenciaServico(Long id) {
        if (!servicosRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Serviço não encontrado com o ID: " + id);
        }
    }

}
