package br.com.fiap.barbertime.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.fiap.barbertime.model.Barbearia;
import br.com.fiap.barbertime.model.Servicos;
import br.com.fiap.barbertime.repository.BarbeariaRepository;
import br.com.fiap.barbertime.repository.ServicosRepository;

@Configuration
@Profile("dev")
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    BarbeariaRepository barbeariaRepository;

    @Autowired
    ServicosRepository servicosRepository;

    @Override
    public void run(String... args) throws Exception {
        
        barbeariaRepository.saveAll(
            List.of(
                Barbearia.builder().id(1L).nome("Cariani Barber")
                .email("crackiani@gmail.com").telefone("11978057895").cnpj("30133297000124").build(),
                Barbearia.builder().id(2L).nome("Lele Flash Power")
                .email("lele@gmail.com").telefone("11997374320").cnpj("73985239000131").build(),
                Barbearia.builder().id(3L).nome("W Miranda")
                .email("wmiranda@gmail.com").telefone("11981375216").cnpj("61793404000174").build(),
                Barbearia.builder().id(4L).nome("Fluxo Barber")
                .email("fluxo@gmail.com").telefone("11983353364").cnpj("31981457000158").build()
            )
        );

        servicosRepository.saveAll(
        List.of(
            Servicos.builder().nome("Corte").valor(BigDecimal.valueOf(50))
            .barbearia(barbeariaRepository.findById(1L).orElseThrow()).build(),
            Servicos.builder().nome("Corte + Barba").valor(BigDecimal.valueOf(65))
            .barbearia(barbeariaRepository.findById(1L).orElseThrow()).build(),
            Servicos.builder().nome("Corte").valor(BigDecimal.valueOf(60))
            .barbearia(barbeariaRepository.findById(2L).orElseThrow()).build(),
            Servicos.builder().nome("Corte").valor(BigDecimal.valueOf(55))
            .barbearia(barbeariaRepository.findById(3L).orElseThrow()).build(),
            Servicos.builder().nome("Corte").valor(BigDecimal.valueOf(35))
            .barbearia(barbeariaRepository.findById(4L).orElseThrow()).build()
        )
    );

    }

    
    
}
