package br.com.fiap.barbertime.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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

import br.com.fiap.barbertime.model.Servicos;
import br.com.fiap.barbertime.repository.ServicosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/servicos")
@Tag(name = "Serviços", description = "Endpoints para operações relacionadas a serviços")
public class ServicosController {
    
    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    PagedResourcesAssembler<Servicos> pagedResourcesAssembler;

    @GetMapping
    @Operation(
        summary = "Listar todos os serviços",
        description = "Retorna uma lista paginada de todos os serviços, ordenados pelo nome em ordem alfabética"
    )
    public PagedModel<EntityModel<Servicos>> listarServicos(
            @Parameter(description = "Página solicitada (começando do 0)", example = "0") @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        
        Page<Servicos> page = servicosRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(page);
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
    public EntityModel<Servicos> buscarPorId(
            @Parameter(description = "ID do serviço a ser buscado", example = "1") @PathVariable Long id) {
        
        var servico = servicosRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Serviço não encontrada"));

        return servico.toEntityModel();
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
        if (!servicosRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Barbearia não encontrada com o ID: " + id);
        }
        
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
                if (!servicosRepository.existsById(id)) {
                    throw new ResponseStatusException(NOT_FOUND, "Serviço não encontrada: ID " + id);
                }
        
                servico.setId(id);
                return servicosRepository.save(servico);
    }



}
