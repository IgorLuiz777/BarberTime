package br.com.fiap.barbertime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "BarberTime API",
        version = "2.0",
        description = "API do aplicativo BarberTime - Sistema de Agendamento para barbearias e clientes",
        contact = @Contact(
            name = "Igor Luiz",
            email = "igorluizpereiralima@gmail.com",
            url = "https://github.com/IgorLuiz777"
        ),
        license = @License(
            name = "Repositório - GitHub",
            url = "https://github.com/IgorLuiz777/BarberTime"
        )
    )
)
public class BarbertimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarbertimeApplication.class, args);
    }

}
