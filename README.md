## BarberTime
API do projeto BarberTime - Sistema de Agendamento em barbearias

## Tarefas

- [] CRUD BARBEARIAS
- [] CRUD CLIENTES
- [] CRUD BARBEIROS
- [] AGENDAMENTO
- [] ENDEREÇO

## Documentação da API

### Endpoints
- [Listar Todas as Barbearias](#listar-todas-as-barbearias)
- [Listar Barbearia](#listar-barbearia)
- [Cadastrar Barbearia](#cadastrar-barbearia)
- [Deletar Barbearia](#deletar-barbearia)
- [Editar Barbearia](#editar-barbearia)

### Listar Todas as Barbearias

`GET` /barbearias

Retorna um array com todas as barbearias cadastradas.

#### Exemplo de Resposta

```js
[
    {
        "id": 1,
        "nome": "Renato Cariani",
        "endereco": {

        },
        "email": "cariani@gmail.com",
        "telefone": "11970863841",
        "cnpj": "",
        "servicos": {

        },
        "funcionarios" {

        }
        
    }
]
```

#### Códigos de Status
|código | descrição |
|-------|-----------|
| 200 | Os dados das barbearias foram retornados com sucesso
| 401 | Acesso negado! Você deve se autenticar|
---

### Listar Barbearia

`GET` /barbearias/`{id}`

Retorna uma única barbearia.

#### Exemplo de Resposta

```js

{
    "id": 1,
    "nome": "Renato Cariani",
    "endereco": {

    },
    "email": "cariani@gmail.com",
    "telefone": "11970863841",
    "cnpj": "",
    "servicos": {

    },
    "funcionarios" {

    }     
}

```

#### Códigos de Status
|código | descrição |
|-------|-----------|
| 200 | Os dados das barbearias foram retornados com sucesso
| 401 | Acesso negado! Você deve se autenticar|
| 404 | Não existe Barbearia com esse id
---

### Cadastrar Barbearia

`POST` /barbearia

Cadastra uma nova barbearia

|código | descrição | obrigatório | descrição |
|-------|-----------|-------------|-----------|
| nome |
| endereco |
| email |
| telefone |
| cnpj |
| servicos |
| funcionarios |
---


#### Exemplo de Resposta
```js
{

}
```

#### Códigos de Status
|código | descrição |
|-------|-----------|
|201 | Dados cadastrados com sucesso
| 400 | Dados enviados são inválidos! Verifique o corpo da requisição |
| 401 | Acesso negado! Você deve se autenticar |
---

### Deletar Barbearia

`DELETE` /barbearia/`{id}`

Deleta a barbearia bom base no `id` informado

#### Exemplo de Resposta
```js
{

}
```

#### Códigos de Status

|código | descrição |
|-------|-----------|
|204 | Barbearia deletada com êxito
| 404 | Não existe barbearia com esse parâmetro |
| 401 | Acesso negado! Você deve se autenticar |
---

### Editar Barbearia

`PUT` /barbearia/`{id}`

Edita a barbearia bom base no `id` informado

|código | descrição | obrigatório | descrição |
|-------|-----------|-------------|-----------|
| nome |
| endereco |
| email |
| telefone |
| cnpj |
| servicos |
| funcionarios |
---

#### Exemplo de Resposta
```js
{

}
```

#### Códigos de Status

|código | descrição |
|-------|-----------|
| 200 | Barbearia editada com êxito
| 400 | Dados envidos são inválidos. Verifique o corpo da requisição
| 404 | Não existe barbearia com esse parâmetro |
| 401 | Acesso negado! Você deve se autenticar |
---
