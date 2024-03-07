package br.com.fiap.barbertime.model;

public record Barbearia(Long id, String nome, Endereco endereco, String email, String telefone, String cnpj, Servicos servicos, Funcionario funcionario) {
     
    private static long contador = 1;

    public Barbearia(Long id, String nome, Endereco endereco, String email, String telefone, String cnpj, Servicos servicos, Funcionario funcionario) {
        this.id = contador++;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.servicos = servicos;
        this.funcionario = funcionario;
    }

    //public void atualizarBarbearia() {

    //    if (this.nome() != null) {
    //        this.nome =
    //    }

    //}

}
