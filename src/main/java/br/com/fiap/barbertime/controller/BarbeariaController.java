package br.com.fiap.barbertime.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.barbertime.model.Barbearia;
import br.com.fiap.barbertime.repository.BarbeariaRepository;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/barbearia")
@Slf4j
public class BarbeariaController {

 
    Logger log = LoggerFactory.getLogger(getClass());

    //repository
    @Autowired
    BarbeariaRepository barbeariaRepository;

    //getAll
    @GetMapping()
    public List<Barbearia> listaBarbearias() {

        return barbeariaRepository.findAll();
    }

    //getId
    @GetMapping("/{id}")
    public ResponseEntity<Barbearia> detalharBarbearia(@PathVariable  Long id) {
        log.info("buscando barbearia com o {}", id);
        
        return barbeariaRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
        
    }

    //post
    @PostMapping()
    @ResponseStatus(CREATED)
    public Barbearia cadastrarBabearia(@RequestBody Barbearia barbearia) {
        log.info("Cadastrando categoria com o {}", barbearia);
        
        return barbeariaRepository.save(barbearia);
    }

    

    //delete
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void excluirBarbearia(@PathVariable Long id) {
        log.info("Deletando barbearia com o id {}", id);

        verificarBarbearia(id);
        barbeariaRepository.deleteById(id);

    }


    //update
    @PutMapping("{id}")
    public Barbearia editarBarbearia(@PathVariable Long id, @RequestBody Barbearia barbearia) {
        log.info("Atuzalizando a barbearia a do id {} para a {}", id, barbearia);

        verificarBarbearia(id);
        barbearia.setId(id);

        return barbeariaRepository.save(barbearia);

    }

    private void verificarBarbearia(Long id) {

        barbeariaRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe uma barbearia com o ID informado!"));

    }


}
