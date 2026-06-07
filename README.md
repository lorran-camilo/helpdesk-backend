# Helpdesk API

Backend de um sistema de helpdesk desenvolvido com Spring Boot. A API gerencia clientes, tecnicos e chamados, com autenticacao via JWT, persistencia com Spring Data JPA e profiles para execucao em H2 ou MySQL.

## Tecnologias

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT
- Bean Validation
- H2 Database
- MySQL
- Maven

## Requisitos

- JDK 25
- Maven, ou o wrapper incluido no projeto (`mvnw` / `mvnw.cmd`)
- MySQL, apenas para uso do profile `dev`

## Como executar

Por padrao, o projeto usa o profile `test`, configurado em `src/main/resources/application.yaml`. Esse profile executa a aplicacao com banco H2 em memoria e popula dados iniciais automaticamente.

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

## Profiles

### test

Profile padrao do projeto. Usa H2 em memoria:

```text
jdbc:h2:mem:testdb
```

Console H2:

```text
http://localhost:8080/h2-console
```

Credenciais:

```text
User: sa
Password:
```

### dev

Usa MySQL local:

```text
jdbc:mysql://localhost:3306/helpdesk
```

Configuracao atual:

```text
username: root
password:
```

Para executar com o profile `dev`:

```bash
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

No profile `dev`, os dados iniciais so sao inseridos quando `spring.jpa.hibernate.ddl-auto` estiver configurado como `create`.

## Variaveis de ambiente

O JWT pode ser configurado por variaveis de ambiente:

```text
JWT_SECRET
JWT_EXPIRATION
```

Caso nao sejam informadas, a aplicacao usa os valores padrao definidos em `application.yaml`.

## Autenticacao

A autenticacao e feita em:

```http
POST /login
```

Exemplo de body:

```json
{
  "email": "lorran@mail.com",
  "senha": "123456"
}
```

Quando o login e bem-sucedido, o token e retornado no header:

```text
Authorization: Bearer <token>
```

Use esse header nas proximas requisicoes protegidas.

## Usuarios iniciais

Com o profile `test`, a base e populada automaticamente. Alguns logins disponiveis:

| Perfil | Email | Senha |
| --- | --- | --- |
| Admin/Tecnico | `lorran@mail.com` | `123456` |
| Tecnico | `leonardo@mail.com` | `654321` |
| Cliente | `torvalds@mail.com` | `654321` |
| Cliente | `bill@mail.com` | `123456` |

## Endpoints

Todas as rotas abaixo exigem autenticacao, exceto o console H2.

### Tecnicos

```http
GET /tecnicos
GET /tecnicos/{id}
POST /tecnicos
PUT /tecnicos/{id}
DELETE /tecnicos/{id}
```

As operacoes `POST`, `PUT` e `DELETE` de tecnicos exigem perfil `ADMIN`.

Exemplo de criacao:

```json
{
  "nome": "Maria Tecnica",
  "cpf": "12345678901",
  "email": "maria@mail.com",
  "senha": "123456"
}
```

### Clientes

```http
GET /clientes
GET /clientes/{id}
POST /clientes
PUT /clientes/{id}
DELETE /clientes/{id}
```

Exemplo de criacao:

```json
{
  "nome": "Joao Cliente",
  "cpf": "98765432100",
  "email": "joao@mail.com",
  "senha": "123456"
}
```

### Chamados

```http
GET /chamados
GET /chamados/{id}
POST /chamados
PUT /chamados/{id}
```

Exemplo de criacao:

```json
{
  "prioridade": 1,
  "status": 0,
  "titulo": "Erro ao acessar o sistema",
  "observacoes": "Usuario nao consegue realizar login.",
  "idTecnico": 1,
  "idCliente": 5
}
```

Valores de prioridade:

| Codigo | Descricao |
| --- | --- |
| 0 | BAIXA |
| 1 | MEDIA |
| 2 | ALTA |

Valores de status:

| Codigo | Descricao |
| --- | --- |
| 0 | ABERTO |
| 1 | ANDAMENTO |
| 2 | ENCERRADO |

## Testes

Para executar os testes:

```bash
.\mvnw.cmd test
```

## Estrutura do projeto

```text
src/main/java/com/lorran_camilo/helpdesk
+-- config        # Configuracoes de seguranca e profiles
+-- domain        # Entidades, DTOs e enums
+-- repositories  # Interfaces Spring Data JPA
+-- resources     # Controllers REST e tratamento de excecoes
+-- security      # Filtros, utilitarios JWT e usuario autenticado
+-- services      # Regras de negocio e carga inicial do banco
```
