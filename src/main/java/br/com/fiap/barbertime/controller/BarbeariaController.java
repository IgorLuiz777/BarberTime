package br.com.fiap.barbertime.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.barbertime.model.Barbearia;


@RestController
@RequestMapping("/barbearia")
public class BarbeariaController {

 
    //repository
    List<Barbearia> barbeariaRepository = new ArrayList<>();

    //post
    @PostMapping()
    public ResponseEntity<Barbearia> cadastrarBabearia(@RequestBody Barbearia barbearia) {
        
        barbeariaRepository.add(barbearia);
        return ResponseEntity.status(HttpStatus.CREATED).body(barbearia);
    }

    //getAll
    @GetMapping  
    public List<Barbearia> listaBarbearias() {

        return barbeariaRepository;
    }


}
