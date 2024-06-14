package br.com.fiap.barbertime.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;

import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.barbertime.controller.BarbeariaController;
import br.com.fiap.barbertime.model.dto.LoginRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Table(name = "barber_time_barbearias")
public class Barbearia extends EntityModel<Barbearia>{
     
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barbearia_id")
    private Long id;

    @NotBlank
    private String nome;

    @Email(message = "Email inválido!")
    @Column(unique = true)
    private String email;

    @NotBlank
    private String senha;

    @Size(min= 11, max = 11)
    private String telefone;

    @NotBlank @Size(min=14, max=14)
    private String cnpj;
    
    @OneToMany(mappedBy = "barbearia")
    private List<Servicos> servicos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "barbearia_roles",
        joinColumns = @JoinColumn(name = "barbearia_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    //Endereco endereco

    //Funcionarios funcionarios

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

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.senha(), this.senha);
    }

}
