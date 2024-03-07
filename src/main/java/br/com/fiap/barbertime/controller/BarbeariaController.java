package br.com.fiap.barbertime.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.barbertime.model.Barbearia;


@RestController
@RequestMapping("/barbearia")
public class BarbeariaController {

 
    //repository
    //@AutoWired
    List<Barbearia> barbeariaRepository = new ArrayList<>();

    //post
    @PostMapping()
    //@Transactional
    public ResponseEntity<Barbearia> cadastrarBabearia(@RequestBody Barbearia barbearia) {
        
        barbeariaRepository.add(barbearia);
        return ResponseEntity.status(HttpStatus.CREATED).body(barbearia);
    }

    //getAll
    @GetMapping()
    public List<Barbearia> listaBarbearias() {

        return barbeariaRepository;
    }

    //getId
    @GetMapping("/{id}")
    public ResponseEntity<Barbearia> detalharBarbearia(@PathVariable  Long id) {
        
        for (Barbearia barbearia : barbeariaRepository) {
            if (barbearia.id().equals(id)) {
                return ResponseEntity.status( HttpStatus.OK).body(barbearia);
            } 
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Barbearia> excluirBarbearia(@PathVariable Long id) {
        for (Barbearia barbearia : barbeariaRepository) {
            if (barbearia.id().equals(id)) {
                barbeariaRepository.remove(barbearia);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



}
