# Finance App

## 📌 Descrição
O **Finance App** é uma aplicação backend para gerenciamento financeiro pessoal,
com suporte a múltiplos usuários compartilhando a mesma conta financeira.

O projeto está sendo desenvolvido com foco em:
- Arquitetura limpa
- Segurança
- Escalabilidade
- Integração futura com Web e Mobile

---

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Docker / Docker Compose
- Lombok
- Maven

---

## 🧱 Arquitetura do Projeto

O projeto segue uma arquitetura em camadas:


Principais pacotes:

- `controller` → Exposição de endpoints REST
- `service` → Regras de negócio
- `repository` → Acesso a dados (JPA)
- `model` → Entidades do domínio
- `dto` → Objetos de entrada e saída da API
- `security` → Configurações de segurança

---

## 🐳 Infraestrutura (Docker)

O projeto utiliza containers Docker para o banco de dados:

- PostgreSQL
- pgAdmin

Os serviços são inicializados via `docker-compose`.

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 21
- Maven
- Docker
- Docker Compose

---

## ▶️ Como executar o projeto

### 1️⃣ Subir os containers do banco de dados

```bash
docker compose up -d
```
### 2️⃣ Executar a aplicação spring
```bash
mvn spring-boot:run
```
### A aplicação roda em localhost
http://localhost:8080

## ⚙️ Endpoints disponíveis
Cadastro de usuário
- POST /auth/register
 
Login de usuário
- POST /auth/register


## Status do projeto
**Em desenvolvimento**

## Funcionalidades já implementadas:
- Cadastro de usuário
- Persistência no banco de dados
- Configuração inicial de segurança
- Ambiente Docker configurado

## Próximos passos
- Implementação de autenticação com JWT - **SENDO IMPLEMENTADO**
- Proteção de endpoints com token
- Criação de contas financeiras compartilhadas
- Controle de acesso por usuário
- Integração com frontend Web e App Mobile

## 🔐 Autenticação e Segurança

A autenticação da aplicação é baseada em usuários cadastrados no sistema,
com controle de acesso realizado pelo Spring Security.

### 👤 Usuário (User)

Cada usuário possui:
- Identificador único (UUID)
- Nome
- Email (único)
- Senha criptografada (BCrypt)

O identificador UUID foi escolhido para:
- Evitar enumeração de usuários
- Facilitar escalabilidade futura
- Aumentar a segurança da API

---

### 🔑 Cadastro de Usuário

Endpoint responsável por criar novos usuários no sistema.

