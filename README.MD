# User Service API 🚀

Um CRUD simples de **usuários** desenvolvido em **Java + Spring Boot +
JPA**.\
Este projeto foi criado para estudo e demonstra como estruturar uma
**API RESTful** com boas práticas.

------------------------------------------------------------------------

## ⚙️ Tecnologias utilizadas

-   Java 24
-   Spring Boot
-   Spring Data JPA
-   Spring Security (BCrypt para senhas)
-   Lombok
-   H2

------------------------------------------------------------------------

## 📂 Estrutura do projeto

    src/main/java/com/engagetech/user_service
     ├── business         # Service
     ├── controller       # API REST Controllers
     ├── infrastructure
     │    ├── entities     # Entidades JPA
     │    └── repository  # Repositórios JPA

------------------------------------------------------------------------

## 📌 Endpoints disponíveis

### Criar usuário

``` http
POST /api/usuarios
```

**Request body**

``` json
{
  "nome": "Joaozinho",
  "email": "joaozinho@email.com",
  "telefone": "11999999999",
  "senha": "minhasenha"
}
```

**Response**

``` json
{
  "id": 1,
  "nome": "Joaozinho",
  "email": "joaozinho@email.com",
  "telefone": "11999999999",
  "senha": "$2a$10$..."
}
```

------------------------------------------------------------------------

### Buscar usuário

``` http
GET /api/usuarios?valor={valor}&tipo={EMAIL|TELEFONE|ID}
```

**Exemplo**

    GET /api/usuarios?valor=1&tipo=ID

------------------------------------------------------------------------

### Atualizar usuário

``` http
PUT /api/usuarios/{id}
```

**Request body**

``` json
{
  "nome": "Novo nome"
}
```

------------------------------------------------------------------------

### Deletar usuário

``` http
DELETE /api/usuarios?valor={valor}&tipo={EMAIL|TELEFONE|ID}
```

------------------------------------------------------------------------

## 🔒 Segurança

-   As senhas são **criptografadas com BCrypt** antes de serem salvas no
    banco.
-   O retorno da API expõe a senha apenas para debug (em produção
recomenda-se ocultar).

------------------------------------------------------------------------

## ▶️ Como executar

1.  Clone o repositório:

    ``` bash
    git clone https://github.com/gdcaue/crud-gerenciamento-usuarios-java.git
    cd user-service
    ```

2.  Compile e execute:

    ``` bash
    ./mvnw spring-boot:run
    ```

3.  API disponível em:

        http://localhost:8080/api/usuarios

------------------------------------------------------------------------

## 📖 Em breve

-   [ ] Adicionar autenticação JWT.
-   [ ] Adicionar campo **CPF** no cadastro com validador.

------------------------------------------------------------------------

