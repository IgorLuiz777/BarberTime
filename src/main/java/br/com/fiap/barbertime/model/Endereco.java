package br.com.fiap.barbertime.model;

public record Endereco(String cep, String logradouro, String bairro, String localidade, String uf, String complemento, int numero ) {
    
}
