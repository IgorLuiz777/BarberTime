package br.com.fiap.barbertime.model.dto;

import java.util.List;

import br.com.fiap.barbertime.model.Servicos;

public record BarbeariaResponse(Long id, String email, String nome, String telefone, String cnpj, List<Servicos> servicos) {
}
