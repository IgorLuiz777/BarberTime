package br.com.fiap.barbertime.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;

import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.fiap.barbertime.controller.ServicosController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class Servicos extends EntityModel<Barbearia> {
     
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String nome;

    @Positive
    BigDecimal valor;

    @ManyToOne
    @JsonBackReference
    Barbearia barbearia;

    public EntityModel<Servicos> toEntityModel() {
        return EntityModel.of(
            this, 
                linkTo(methodOn(ServicosController.class).buscarPorId(id)).withSelfRel(),
                linkTo(ServicosController.class).slash(id).withRel("DELETE"),
                linkTo(methodOn(ServicosController.class).listarServicos(null)).withRel("GET"),
                linkTo(methodOn(ServicosController.class).cadastrarServico(null)).withRel("POST"),
                linkTo(methodOn(ServicosController.class).atualizarServico(id, null)).withRel("PUT")
            );
    }
}
