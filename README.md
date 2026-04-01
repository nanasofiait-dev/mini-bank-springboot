# Mini Bank API

API REST desenvolvida com Spring Boot para simular um sistema bancário.

## Funcionalidades

- Gestão de clientes
- Criação de contas
- Depósitos
- Levantamentos
- Transferências
- Histórico de transações

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- H2

## Endpoints

### Clients
- POST /clients
- GET /clients
- GET /clients/{id}

### Accounts
- POST /accounts
- GET /accounts/{id}
- GET /accounts/client/{clientId}
- PATCH /accounts/{id}/deactivate

### Transactions
- POST /transactions/deposit
- POST /transactions/withdraw
- POST /transactions/transfer
- GET /transactions/account/{accountId}
