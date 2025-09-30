# 🛒 Sistema de Gestão de Pedidos e Produtos

Este projeto é um sistema de Back-end completo que gerencia **Produtos**, **Clientes** e **Pedidos**, desenvolvido com **Spring Boot 3** e **Java 17+**. Ele demonstra a aplicação da **Arquitetura Hexagonal (Ports and Adapters)** para garantir a separação da lógica de negócio (Domínio) das preocupações de infraestrutura.

---

## 🌟 Destaques e Boas Práticas

* **Arquitetura Hexagonal (Ports and Adapters):** O código é estritamente separado. A **Camada de Aplicação/Domínio** (Services) define as interfaces (Ports) que são implementadas pelos **Adaptadores de Infraestrutura** (Controllers e Gateways/Repositories).
* **Controle de Estoque Transacional:** O módulo de Pedidos garante que o estoque do produto seja **decrementado** na criação e **revertido** automaticamente no cancelamento (utilizando `@Transactional`).
* **Tratamento de Erros Profissional:** Um **`GlobalExceptionHandler`** (`@ControllerAdvice`) é responsável por mapear exceções de domínio (`ResourceNotFoundException`, `BusinessValidationException`) para respostas HTTP coerentes (`404` e `400`).
* **Padrão REST Coerente:** Todos os recursos utilizam o **Soft Delete** (`DELETE /recurso/{id}`) e retornam o status `204 No Content` para operações de inativação.

---

## ⚙️ Tecnologias e Dependências

| Categoria | Tecnologia/Padrão |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot 3 |
| **Arquitetura** | **Arquitetura Hexagonal** (Ports/Adapters) |
| **Persistência**| Spring Data JPA, Hibernate |
| **Banco de Dados**| H2 (Em Memória para Dev) |
| **Documentação**| SpringDoc (Swagger/OpenAPI 3) |
| **Ferramentas** | Maven, Lombok |

---

## 📦 Estrutura Completa da API (Endpoints)

| Módulo | Método | Endpoint | Descrição | Status HTTP |
| :--- | :--- | :--- | :--- | :--- |
| **Produto** | `POST` | `/produtos` | Cria um novo produto. | `201 Created` |
| **Produto** | `GET` | `/produtos/{id}` | Busca produto ativo por ID. | `200 OK` / `404` |
| **Produto** | `PATCH`| `/produtos/{id}` | Atualiza dados parciais do produto. | `200 OK` / `404` |
| **Produto** | `DELETE`| `/produtos/{id}` | Inativa o produto (Soft Delete). | `204 No Content` |
| **Cliente** | `POST` | `/clientes` | Cadastra novo cliente (valida unicidade de CPF/E-mail). | `201 Created` / `400` |
| **Cliente** | `GET` | `/clientes/{id}` | Busca cliente ativo por ID. | `200 OK` / `404` |
| **Cliente** | `PATCH`| `/clientes/{id}` | Atualiza dados do cliente. | `200 OK` / `400` |
| **Cliente** | `DELETE`| `/clientes/{id}` | Desativa o cliente (Soft Delete). | `204 No Content` |
| **Pedido** | `POST` | `/pedidos` | Cria pedido (diminui estoque e valida cliente). | `201 Created` / `400` |
| **Pedido** | `GET` | `/pedidos/{id}` | Busca pedido por ID. | `200 OK` / `404` |
| **Pedido** | `PATCH`| `/pedidos/{id}/status`| Atualiza o status (pode devolver estoque no CANCELADO).| `200 OK` / `400` |
| **Pedido** | `DELETE`| `/pedidos/{id}` | Inativa o pedido (Soft Delete). | `204 No Content

---

## 🏃 Como Clonar e Rodar o Projeto

### Pré-requisitos
* **JDK 17** ou superior instalado.
* **Maven** instalado.

### Passos de Execução

1.  **Clone o repositório** para a sua máquina local:
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    cd nome-do-seu-repositorio
    ```

2.  **Execute o Spring Boot** usando o wrapper do Maven:
    ```bash
    ./mvnw spring-boot:run
    ```

3.  **Acesse a Documentação (Swagger/OpenAPI):**
    A aplicação estará rodando em `http://localhost:8080`. Para testar os endpoints, acesse:
    [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📄 Exemplos de Payload (JSON)

Para facilitar os testes via Postman ou Swagger, seguem exemplos de *payloads* de sucesso e de erro para os endpoints cruciais.

### 1. Cadastro de Produto (`POST /produtos`)

Este *payload* deve ser enviado antes de testar o Pedido, para garantir que o `produtoId: 1` exista.

```json
{
  "nome": "Smartphone Top de Linha",
  "descricao": "Telefone com câmera de 200MP e 12GB RAM.",
  "preco": 4500.50,
  "estoqueAtual": 10
}
```
### 2. Cadastro de Cliente (`POST /clientes`)

Este *payload* deve ser enviado antes de testar o Pedido, para garantir que o `clienteId: 1` exista.

```json
{
  "nome": "Bruce Wayne",
  "email": "bruce.wayne@empresa.com",
  "cpf": "12345678901"
}
```

### 3. Cadastro de Pedido (Sucesso) (`POST /pedidos`)

Este *payload* usa os IDs cadastrados acima

```json
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    }
  ]
}
```

### 4. Atualização de Status para CANCELADO (`PATCH /pedidos/{id}/status`)

Este *payload* deve ser usado para cancelar um pedido (id = 1, por exemplo). Lembre-se que o cancelamento devolve o estoque ao produto.

```json
{
  "novoStatus": "CANCELADO"
}
```

### 5. Exemplo de Erro (Business Validation-Estoque)

Se você tentar enviar este JSON, e o estoque do produtoId: 1 for 0, o sistema retornará 400 Bad Request com BusinessValidationException.

```json
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 1500
    }
  ]
}
```