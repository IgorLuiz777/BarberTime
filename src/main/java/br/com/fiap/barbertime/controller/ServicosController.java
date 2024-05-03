package br.com.fiap.barbertime.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/servicos")
public class ServicosController {
    
    @Autowired
    ServicosRepository servicosRepository;

    @GetMapping
    public Page<Servicos> index(@PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return servicosRepository.findAll(pageable);
    }

    @GetMapping("/nome")
    public Page<Servicos> findByNome(
            @RequestParam(required = false) String nome, Pageable pageable) {
        return servicosRepository.findByNome(nome, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicos> findById(@PathVariable Long id) {
        return servicosRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

     @PostMapping
    @ResponseStatus(CREATED)
    public Servicos cadastrarServico(@RequestBody Servicos servicos) {
        return servicosRepository.save(servicos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void excluirServico(@PathVariable Long id) {
        verificarServico(id);
        servicosRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Servicos editarServico(@PathVariable Long id, @RequestBody Servicos servicos) {
        verificarServico(id);
        servicos.setId(id);
        return servicosRepository.save(servicos);
    }

    private void verificarServico(Long id) {
        servicosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe um servico com o ID informado!"));
    }

}
