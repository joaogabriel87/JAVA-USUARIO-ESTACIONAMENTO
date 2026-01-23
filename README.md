# API Usuários Estacionamento

## Visão Geral

Serviço backend responsável pelo gerenciamento de usuários, autenticação, autorização e vínculo de veículos a usuários no contexto do sistema de estacionamento. Implementa segurança com JWT e integração via RabbitMQ.

## Principais Tecnologias

* Java
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* RabbitMQ
* OpenAPI (Swagger)

## Arquitetura

```
Controller -> Service -> Repository -> Database
                 |
              DTO / Security
```

## Segurança

* Autenticação via JWT
* Filtros de segurança personalizados
* Controle de acesso por token

Principais classes:

* `SecurityConfig`
* `SecurityFilter`
* `TokenConfig`
* `JWTUserData`

## Módulos Principais

### Autenticação

* `AuthController`

Funcionalidades:

* Login
* Geração de token JWT

DTOs:

* `LoginRequest`
* `LoginResponse`

### Usuários

* `UserController`
* `UserServices`
* `UserRepository`
* `Users`

Funcionalidades:

* Cadastro de usuários
* Consulta por email

### Veículos

* `VehicleRepository`
* `Vehicle`
* `VehicleConsumer`

Funcionalidades:

* Associação de veículos a usuários
* Consumo de mensagens via RabbitMQ

## Integrações

* Comunicação assíncrona com a API de Estacionamento usando RabbitMQ

## Documentação da API

Configurada via OpenAPI (`OpenApiConfig`), permitindo visualização dos endpoints pelo Swagger.

## Execução do Projeto

1. Configurar banco de dados
2. Configurar RabbitMQ
3. Ajustar `application.properties`
4. Executar:

```bash
mvn spring-boot:run
```

## Objetivo do Projeto

Demonstrar implementação de autenticação segura, arquitetura distribuída e comunicação entre microsserviços usando Java e Spring Boot.
