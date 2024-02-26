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
        "nome": "Cariani Barber",
        "endereco": {
            "logradouro": "Rua 1",
            "cep": "08384733",
            "numero": "33,
            "bairro": "Paulista",
            "cidade": "São Paulo",
            "uf": "SP"
        },
        "email": "crackiani@gmail.com",
        "telefone": "11970863841",
        "cnpj": "17349836000100",
        "servicos": {
            [
                "CORTE",
                "BARBA",
                "CORTE + BARTA",
                "RASPAR O CABELO",
                "LUZES",
                "COLORACAO"
            ]
        },
        "funcionarios" {
            [
                "id": 1,
                "nome": "Adriano",
                "telefone": "11987637495",
                "email": "adriano.imperador@gmail.com",
                "dataNascimento": 17/10/1982         
            ]
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
    "nome": "Cariani Barber",
    "endereco": {
        "logradouro": "Rua 1",
        "cep": "08384733",
        "numero": "33,
        "bairro": "Paulista",
        "cidade": "São Paulo",
        "uf": "SP"
    },
    "email": "crackiani@gmail.com",
    "telefone": "11970863841",
    "cnpj": "17349836000100",
    "servicos": {
        [
            "CORTE",
            "BARBA",
            "CORTE + BARTA",
            "RASPAR O CABELO",
            "LUZES",
            "COLORACAO"
        ]
    },
    "funcionarios" {
        [
            "id": 1,
            "nome": "Adriano",
            "telefone": "11987637495",
            "email": "adriano.imperador@gmail.com",
            "dataNascimento": 17/10/1982         
        ]
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

#### Corpo da Requisição

|código | tipo | obrigatório | descrição |
|-------|-----------|-------------|-----------|
| nome | string | ✅ | nome da barbearia |
| endereco | endereco | ✅ | endereço da berbearia|
| email | string | ✅ | email da barbearia |
| telefone | string | ✅ | telefone da barbearia |
| cnpj | string | ✅ | cnpj da barbearia |
| servicos | servicos | ❌ | serviços da barbearia |
| funcionarios | funcionarios | ❌ | funcionários da barbearia |
---


#### Exemplo de Resposta
```js
{
    "id": 1,
    "nome": "Cariani Barber",
    "endereco": {
        "logradouro": "Rua 1",
        "cep": "08384733",
        "numero": "33,
        "bairro": "Paulista",
        "cidade": "São Paulo",
        "uf": "SP"
    },
    "email": "crackiani@gmail.com",
    "telefone": "11970863841",
    "cnpj": "17349836000100",
    "servicos": {
        [
            "CORTE",
            "BARBA",
            "CORTE + BARTA",
            "RASPAR O CABELO",
            "LUZES",
            "COLORACAO"
        ]
    },
    "funcionarios" {
        [
            "id": 1,
            "nome": "Adriano",
            "telefone": "11987637495",
            "email": "adriano.imperador@gmail.com",
            "dataNascimento": 17/10/1982         
        ]
    }     
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

#### Corpo da Requisição

|código | tipo | obrigatório | descrição |
|-------|-----------|-------------|-----------|
| nome | string | ❌ | altera o nome da barbearia |
| endereco | endereco | ❌ | altera o endereço da berbearia|
| email | string | ❌ | altera o email da barbearia |
| telefone | string | ❌ | altera o telefone da barbearia |
| servicos | servicos | ❌ | altera os serviços da barbearia |
| funcionarios | funcionarios | ❌ | altera os funcionários da barbearia |
---

#### Exemplo de Resposta
```js
{
    "telefone": "11973628453",
    "cnpj": "58907312000199"
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
