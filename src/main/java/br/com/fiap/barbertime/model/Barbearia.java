package br.com.fiap.barbertime.model;

public record Barbearia(Long id, String nome, Endereco endereco, String email, String telefone, String cnpj, Servicos servicos) {
     
}
