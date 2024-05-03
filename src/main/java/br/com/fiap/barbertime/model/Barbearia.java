package br.com.fiap.barbertime.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Barbearia {

    // TODO: CONSEGUIR DA UM PUT SEM PASSAR OS ATRIBULOS NOTNULL
     
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String nome;

    //Endereco endereco;

    @Email
    String email;

    @Size(min= 11, max = 11)
    String telefone;

    @NotBlank @Size(min=14, max=14)
    String cnpj;
    
    @OneToMany(mappedBy = "barbearia")
    List<Servicos> servicos;

    //Funcionario funcionario;

}
