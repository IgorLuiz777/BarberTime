package br.com.fiap.barbertime.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.fiap.barbertime.model.RoleName;
import br.com.fiap.barbertime.model.User;
import br.com.fiap.barbertime.repository.RoleRepository;
import br.com.fiap.barbertime.repository.UserRepository;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMINISTRATOR);

        var userAdmin = userRepository.findByEmail("admin");

        userAdmin.ifPresentOrElse(
            user -> System.out.println("ADMIN já existe"),
            () -> {
                var user = new User();
                user.setNome("admin");
                user.setEmail("admin@gmail.com");
                user.setSenha(bCryptPasswordEncoder.encode("senhaAdmin123"));
                roleAdmin.ifPresent(role -> user.setRoles(Set.of(role)));
                userRepository.save(user);
            });
    }

}
