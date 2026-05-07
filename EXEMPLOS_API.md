## Dicionario de requisicoes para a API AutoBots

<details>
<summary><strong>O que mudou no comportamento da API</strong></summary>

- As respostas de `GET`, `POST` e `PUT` agora retornam recursos HATEOAS com `_links`.
- Os endpoints continuam os mesmos, mas os exemplos de resposta precisavam refletir os links gerados pelos `ModelAssembler`.
- `PUT /clientes/{id}` funciona como atualizacao parcial para os campos informados.
- No `PUT /clientes/{id}`, documentos e telefones podem ser atualizados por `id` e novos itens podem ser adicionados quando enviados sem `id`.
- A API possui tratamento padrao de erros com corpo JSON para `400`, `404` e `409`.

### Formato padrao de erro

Exemplo de resposta:

```json
{
  "timestamp": "2026-04-17T22:30:00",
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "nome: nome deve ser informado",
  "caminho": "/clientes"
}
```

</details>

---

<details>
<summary><strong>Cliente</strong></summary>

### 1. Cadastrar cliente (POST)

**Metodo:** `POST`  
**URL:** `http://localhost:8080/clientes`

**Body:**

```json
{
  "nome": "Sarah Montuani Batagioti",
  "nomeSocial": "Sarah Batagioti",
  "dataNascimento": "1990-05-15T00:00:00.000+00:00",
  "endereco": {
    "estado": "SP",
    "cidade": "Sao Jose dos Campos",
    "bairro": "Jardim Paulista",
    "rua": "Avenida Principal",
    "numero": "150",
    "codigoPostal": "12230000",
    "informacoesAdicionais": "Proximo a praca central"
  },
  "documentos": [
    {
      "tipo": "RG",
      "numero": "123456789"
    }
  ],
  "telefones": [
    {
      "ddd": "12",
      "numero": "987654321"
    }
  ]
}
```

**Resposta esperada:** `201 Created`

**Exemplo de resposta:**

```json
{
  "id": 1,
  "nome": "Sarah Montuani Batagioti",
  "nomeSocial": "Sarah Batagioti",
  "dataNascimento": "1990-05-15T00:00:00.000+00:00",
  "dataCadastro": "2026-04-17T22:30:00.000+00:00",
  "documentos": [
    {
      "id": 1,
      "tipo": "RG",
      "numero": "123456789"
    }
  ],
  "endereco": {
    "id": 1,
    "estado": "SP",
    "cidade": "Sao Jose dos Campos",
    "bairro": "Jardim Paulista",
    "rua": "Avenida Principal",
    "numero": "150",
    "codigoPostal": "12230000",
    "informacoesAdicionais": "Proximo a praca central"
  },
  "telefones": [
    {
      "id": 1,
      "ddd": "12",
      "numero": "987654321"
    }
  ],
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes/1"
    },
    "clientes": {
      "href": "http://localhost:8080/clientes"
    },
    "documentos": {
      "href": "http://localhost:8080/clientes/1/documentos"
    },
    "telefones": {
      "href": "http://localhost:8080/clientes/1/telefones"
    },
    "endereco": {
      "href": "http://localhost:8080/clientes/1/endereco"
    },
    "atualizar": {
      "href": "http://localhost:8080/clientes/1"
    },
    "remover": {
      "href": "http://localhost:8080/clientes/1"
    }
  }
}
```

### 2. Listar todos os clientes (GET)

**Metodo:** `GET`  
**URL:** `http://localhost:8080/clientes`

**Resposta esperada:** `200 OK`

**Exemplo de resposta:**

```json
{
  "_embedded": {
    "clienteList": [
      {
        "id": 1,
        "nome": "Sarah Montuani Batagioti",
        "nomeSocial": "Sarah Batagioti",
        "dataNascimento": "1990-05-15T00:00:00.000+00:00",
        "dataCadastro": "2026-04-17T22:30:00.000+00:00",
        "documentos": [],
        "endereco": null,
        "telefones": [],
        "_links": {
          "self": {
            "href": "http://localhost:8080/clientes/1"
          },
          "clientes": {
            "href": "http://localhost:8080/clientes"
          },
          "documentos": {
            "href": "http://localhost:8080/clientes/1/documentos"
          },
          "telefones": {
            "href": "http://localhost:8080/clientes/1/telefones"
          },
          "endereco": {
            "href": "http://localhost:8080/clientes/1/endereco"
          },
          "atualizar": {
            "href": "http://localhost:8080/clientes/1"
          },
          "remover": {
            "href": "http://localhost:8080/clientes/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes"
    },
    "cadastrar": {
      "href": "http://localhost:8080/clientes"
    }
  }
}
```

### 3. Buscar cliente por ID (GET)

**Metodo:** `GET`  
**URL:** `http://localhost:8080/clientes/1`

**Resposta esperada:** `200 OK`

**Observacao:** o corpo segue o mesmo formato do cadastro, incluindo `_links`.

### 4. Atualizar cliente (PUT)

**Metodo:** `PUT`  
**URL:** `http://localhost:8080/clientes/1`

**Observacao:** a atualizacao e parcial. Campos omitidos permanecem como estao.

**Body:**

```json
{
  "nomeSocial": "Sarah M. Batagioti",
  "documentos": [
    {
      "id": 1,
      "numero": "987654321",
      "tipo": "RG"
    },
    {
      "tipo": "CPF",
      "numero": "11122233344"
    }
  ],
  "telefones": [
    {
      "id": 1,
      "ddd": "12",
      "numero": "999999999"
    },
    {
      "ddd": "11",
      "numero": "988887777"
    }
  ]
}
```

**Resposta esperada:** `200 OK`

**Observacao:** itens com `id` sao atualizados; itens sem `id` sao adicionados ao cliente.

### 5. Deletar cliente (DELETE)

**Metodo:** `DELETE`  
**URL:** `http://localhost:8080/clientes/1`

**Resposta esperada:** `204 No Content`

</details>

<details>
<summary><strong>Endereco</strong></summary>

### 1. Cadastrar endereco (POST)

**Pre-requisito:** cliente com ID 1 deve existir

**Metodo:** `POST`  
**URL:** `http://localhost:8080/clientes/1/endereco`

**Body:**

```json
{
  "estado": "SP",
  "cidade": "Sao Jose dos Campos",
  "bairro": "Paraiso do Sol",
  "rua": "Rua Esperanca",
  "numero": "250",
  "codigoPostal": "12240000",
  "informacoesAdicionais": "Apto 501"
}
```

**Resposta esperada:** `201 Created`

**Exemplo de resposta:**

```json
{
  "id": 1,
  "estado": "SP",
  "cidade": "Sao Jose dos Campos",
  "bairro": "Paraiso do Sol",
  "rua": "Rua Esperanca",
  "numero": "250",
  "codigoPostal": "12240000",
  "informacoesAdicionais": "Apto 501",
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes/1/endereco"
    },
    "cliente": {
      "href": "http://localhost:8080/clientes/1"
    },
    "atualizar": {
      "href": "http://localhost:8080/clientes/1/endereco"
    },
    "remover": {
      "href": "http://localhost:8080/clientes/1/endereco"
    }
  }
}
```

### 2. Buscar endereco do cliente (GET)

**Metodo:** `GET`  
**URL:** `http://localhost:8080/clientes/1/endereco`

**Resposta esperada:** `200 OK`

**Observacao:** retorna o endereco com o mesmo formato HATEOAS do cadastro.

### 3. Atualizar endereco (PUT)

**Metodo:** `PUT`  
**URL:** `http://localhost:8080/clientes/1/endereco`

**Body:**

```json
{
  "estado": "RJ",
  "cidade": "Rio de Janeiro",
  "bairro": "Copacabana",
  "rua": "Avenida Atlantica",
  "numero": "444",
  "codigoPostal": "22021001",
  "informacoesAdicionais": "Proximo a praia"
}
```

**Resposta esperada:** `200 OK`

### 4. Deletar endereco (DELETE)

**Metodo:** `DELETE`  
**URL:** `http://localhost:8080/clientes/1/endereco`

**Resposta esperada:** `204 No Content`

</details>

<details>
<summary><strong>Documento</strong></summary>

### 1. Cadastrar documento (POST)

**Pre-requisito:** cliente com ID 1 deve existir

**Metodo:** `POST`  
**URL:** `http://localhost:8080/clientes/1/documentos`

**Body:**

```json
{
  "tipo": "RG",
  "numero": "123456789"
}
```

**Resposta esperada:** `201 Created`

**Exemplo de resposta:**

```json
{
  "id": 1,
  "tipo": "RG",
  "numero": "123456789",
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes/1/documentos/1"
    },
    "documentos": {
      "href": "http://localhost:8080/clientes/1/documentos"
    },
    "cliente": {
      "href": "http://localhost:8080/clientes/1"
    },
    "atualizar": {
      "href": "http://localhost:8080/clientes/1/documentos/1"
    },
    "remover": {
      "href": "http://localhost:8080/clientes/1/documentos/1"
    }
  }
}
```

### 2. Listar documentos do cliente (GET)

**Metodo:** `GET`  
**URL:** `http://localhost:8080/clientes/1/documentos`

**Resposta esperada:** `200 OK`

**Exemplo de resposta:**

```json
{
  "_embedded": {
    "documentoList": [
      {
        "id": 1,
        "tipo": "RG",
        "numero": "123456789",
        "_links": {
          "self": {
            "href": "http://localhost:8080/clientes/1/documentos/1"
          },
          "documentos": {
            "href": "http://localhost:8080/clientes/1/documentos"
          },
          "cliente": {
            "href": "http://localhost:8080/clientes/1"
          },
          "atualizar": {
            "href": "http://localhost:8080/clientes/1/documentos/1"
          },
          "remover": {
            "href": "http://localhost:8080/clientes/1/documentos/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes/1/documentos"
    },
    "cliente": {
      "href": "http://localhost:8080/clientes/1"
    },
    "cadastrar": {
      "href": "http://localhost:8080/clientes/1/documentos"
    }
  }
}
```

### 3. Buscar documento por ID (GET)

**Metodo:** `GET`  
**URL:** `http://localhost:8080/clientes/1/documentos/1`

**Resposta esperada:** `200 OK`

**Observacao:** retorna o mesmo formato HATEOAS do cadastro de documento.

### 4. Atualizar documento (PUT)

**Metodo:** `PUT`  
**URL:** `http://localhost:8080/clientes/1/documentos/1`

**Body:**

```json
{
  "tipo": "RG",
  "numero": "987654321"
}
```

**Resposta esperada:** `200 OK`

### 5. Deletar documento (DELETE)

**Metodo:** `DELETE`  
**URL:** `http://localhost:8080/clientes/1/documentos/1`

**Resposta esperada:** `204 No Content`

</details>

<details>
<summary><strong>Telefone</strong></summary>

### 1. Cadastrar telefone (POST)

**Pre-requisito:** cliente com ID 1 deve existir

**Metodo:** `POST`  
**URL:** `http://localhost:8080/clientes/1/telefones`

**Body:**

```json
{
  "ddd": "12",
  "numero": "987654321"
}
```

**Resposta esperada:** `201 Created`

**Exemplo de resposta:**

```json
{
  "id": 1,
  "ddd": "12",
  "numero": "987654321",
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes/1/telefones/1"
    },
    "telefones": {
      "href": "http://localhost:8080/clientes/1/telefones"
    },
    "cliente": {
      "href": "http://localhost:8080/clientes/1"
    },
    "atualizar": {
      "href": "http://localhost:8080/clientes/1/telefones/1"
    },
    "remover": {
      "href": "http://localhost:8080/clientes/1/telefones/1"
    }
  }
}
```

### 2. Listar telefones do cliente (GET)

**Metodo:** `GET`  
**URL:** `http://localhost:8080/clientes/1/telefones`

**Resposta esperada:** `200 OK`

**Exemplo de resposta:**

```json
{
  "_embedded": {
    "telefoneList": [
      {
        "id": 1,
        "ddd": "12",
        "numero": "987654321",
        "_links": {
          "self": {
            "href": "http://localhost:8080/clientes/1/telefones/1"
          },
          "telefones": {
            "href": "http://localhost:8080/clientes/1/telefones"
          },
          "cliente": {
            "href": "http://localhost:8080/clientes/1"
          },
          "atualizar": {
            "href": "http://localhost:8080/clientes/1/telefones/1"
          },
          "remover": {
            "href": "http://localhost:8080/clientes/1/telefones/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes/1/telefones"
    },
    "cliente": {
      "href": "http://localhost:8080/clientes/1"
    },
    "cadastrar": {
      "href": "http://localhost:8080/clientes/1/telefones"
    }
  }
}
```

### 3. Buscar telefone por ID (GET)

**Metodo:** `GET`  
**URL:** `http://localhost:8080/clientes/1/telefones/1`

**Resposta esperada:** `200 OK`

**Observacao:** retorna o mesmo formato HATEOAS do cadastro de telefone.

### 4. Atualizar telefone (PUT)

**Metodo:** `PUT`  
**URL:** `http://localhost:8080/clientes/1/telefones/1`

**Body:**

```json
{
  "ddd": "11",
  "numero": "999999999"
}
```

**Resposta esperada:** `200 OK`

### 5. Deletar telefone (DELETE)

**Metodo:** `DELETE`  
**URL:** `http://localhost:8080/clientes/1/telefones/1`

**Resposta esperada:** `204 No Content`

</details>
