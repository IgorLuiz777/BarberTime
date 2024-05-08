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

import br.com.fiap.barbertime.model.Barbearia;
import br.com.fiap.barbertime.repository.BarbeariaRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/barbearia")
@Slf4j
@Tag(name = "barbearia")
public class BarbeariaController {

    @Autowired
    BarbeariaRepository barbeariaRepository;

    @GetMapping
    public Page<Barbearia> index(@PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return barbeariaRepository.findAll(pageable);
    }

    @GetMapping("/nome")
    public Page<Barbearia> findByNome(
            @RequestParam(required = false) String nome, Pageable pageable) {
        return barbeariaRepository.findByNome(nome, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barbearia> findById(@PathVariable Long id) {
        log.info("buscando barbearia com o {}", id);
        return barbeariaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Barbearia cadastrarBarbearia(@RequestBody Barbearia barbearia) {
        log.info("Cadastrando barbearia com o {}", barbearia);
        return barbeariaRepository.save(barbearia);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void excluirBarbearia(@PathVariable Long id) {
        log.info("Deletando barbearia com o id {}", id);
        verificarBarbearia(id);
        barbeariaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Barbearia editarBarbearia(@PathVariable Long id, @RequestBody Barbearia barbearia) {
        log.info("Atualizando a barbearia com o id {} para a {}", id, barbearia);
        verificarBarbearia(id);
        barbearia.setId(id);
        return barbeariaRepository.save(barbearia);
    }

    private void verificarBarbearia(Long id) {
        barbeariaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe uma barbearia com o ID informado!"));
    }
}
