package br.com.fiap.barbertime.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.barbertime.model.Barbearia;
import br.com.fiap.barbertime.repository.BarbeariaRepository;

@Configuration
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    BarbeariaRepository barbeariaRepository;

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

    }

    
    
}
