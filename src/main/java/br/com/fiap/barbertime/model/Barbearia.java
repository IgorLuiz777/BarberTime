package br.com.fiap.barbertime.model;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Barbearia {
     
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String nome;
    //Endereco endereco;
    @Email
    String email;
    @Size(min= 11, max = 11)
    String telefone;
    @NotBlank
    String cnpj;
    //Servicos servicos;
    //Funcionario funcionario;

}
