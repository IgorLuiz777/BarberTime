package br.com.fiap.barbertime.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.barbertime.controller.BarbeariaController;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Barbearia extends EntityModel<Barbearia>{

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

     public EntityModel<Barbearia> toEntityModel() {
        return EntityModel.of(
            this, 
            linkTo(methodOn(BarbeariaController.class).buscarPorId(id)).withSelfRel(),
            linkTo(BarbeariaController.class).slash(id).withRel("DELETE"),
            linkTo(methodOn(BarbeariaController.class).listarBarbearias(null)).withRel("GET"),
            linkTo(methodOn(BarbeariaController.class).cadastrarBarbearia(null)).withRel("POST"),
            linkTo(methodOn(BarbeariaController.class).atualizarBarbearia(id, null)).withRel("PUT")
            );
    }

}
