package br.com.fiap.barbertime.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.fiap.barbertime.model.Barbearia;
import br.com.fiap.barbertime.model.Role;
import br.com.fiap.barbertime.model.RoleName;
import br.com.fiap.barbertime.model.Servicos;
import br.com.fiap.barbertime.model.User;
import br.com.fiap.barbertime.repository.BarbeariaRepository;
import br.com.fiap.barbertime.repository.RoleRepository;
import br.com.fiap.barbertime.repository.ServicosRepository;
import br.com.fiap.barbertime.repository.UserRepository;

@Configuration
@Profile("dev")
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BarbeariaRepository barbeariaRepository;

    @Autowired
    ServicosRepository servicosRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        roleRepository.saveAll(
            List.of(
                Role.builder().name(RoleName.ROLE_USER).build(),
                Role.builder().name(RoleName.ROLE_BARBEARIA).build(),
                Role.builder().name(RoleName.ROLE_ADMINISTRATOR).build()
                
            )
        );


        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_USER);
        Optional<Role> adminRole = roleRepository.findByName(RoleName.ROLE_ADMINISTRATOR);
        Optional<Role> barbeariaRole = roleRepository.findByName(RoleName.ROLE_BARBEARIA);


        if (userRole.isEmpty() || adminRole.isEmpty() || barbeariaRole.isEmpty()) {
            throw new RuntimeException("Roles not found in database.");
        }

        
        barbeariaRepository.saveAll(
            List.of(
                Barbearia.builder().id(1L).nome("Cariani Barber").email("crackiani@gmail.com").telefone("11978057895")
                .cnpj("30133297000124").senha(bCryptPasswordEncoder.encode("senhaforte12")).roles(Set.of(barbeariaRole.get())).build(),
                Barbearia.builder().id(2L).nome("Lele Flash Power").email("lele@gmail.com").telefone("11997374320")
                .cnpj("73985239000131").senha(bCryptPasswordEncoder.encode("senhaforte12")).roles(Set.of(barbeariaRole.get())).build(),
                Barbearia.builder().id(3L).nome("W Miranda").email("wmiranda@gmail.com").telefone("11981375216")
                .cnpj("61793404000174").senha(bCryptPasswordEncoder.encode("senhaforte12")).roles(Set.of(barbeariaRole.get())).build(),
                Barbearia.builder().id(4L).nome("Fluxo Barber").email("fluxo@gmail.com").telefone("11983353364")
                .cnpj("31981457000158").senha(bCryptPasswordEncoder.encode("senhaforte12")).roles(Set.of(barbeariaRole.get())).build()
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

    userRepository.saveAll(
            List.of(
                User.builder().id(2L).nome("Igor")
                .email("igor@gmail.com").senha(bCryptPasswordEncoder.encode("senhaforte12")).roles(Set.of(userRole.get())).build(),
                User.builder().id(3L).nome("Usuario teste02")
                .email("testuser02@gmail.com").senha(bCryptPasswordEncoder.encode("senhaforte123")).roles(Set.of(userRole.get())).build(),
                User.builder().id(4L).nome("Usuario teste03")
                .email("testuser03@gmail.com").senha(bCryptPasswordEncoder.encode("senhaforte1234")).roles(Set.of(userRole.get())).build(),
                User.builder().id(5L).nome("Usuario teste04")
                .email("testuser04@gmail.com").senha(bCryptPasswordEncoder.encode("senhaforte12345")).roles(Set.of(userRole.get())).build()
            )
        );

    }

    
    
}
