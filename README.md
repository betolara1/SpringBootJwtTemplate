# Spring Boot JWT Template

![Java](https://img.shields.io/badge/java-21-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen?logo=spring)
![License](https://img.shields.io/badge/license-MIT-blue)
![Build](https://img.shields.io/badge/build-passing-brightgreen)

**Template pronto para produção com autenticação JWT, tratamento de erros global e arquitetura escalável.**

---

## 📋 Sumário

- [Objetivo & Problema](#-objetivo--problema)
- [Arquitetura](#-arquitetura)
- [Tech Stack](#-tech-stack)
- [Como Rodar](#-como-rodar)
- [Exemplos de API](#-exemplos-de-api)
- [Documentação Swagger](#-documentação-swagger)
- [Docker](#-docker)
- [Testes](#-testes)
- [CI/CD](#-cicd)

---

## 🎯 Objetivo & Problema

### Problema
Criar um novo projeto backend RESTful com autenticação segura envolve repetir tarefas comuns:
- Configurar Spring Security + JWT
- Implementar criptografia de senha
- Tratar exceções globalmente
- Estruturar projeto seguindo boas práticas
- Configurar banco de dados

### Solução
Este template **elimina boilerplate** fornecendo:
- ✅ Autenticação JWT com tokens de 24h
- ✅ Criptografia BCrypt de senhas
- ✅ Handler global de exceções
- ✅ Estrutura pronta para escalabilidade
- ✅ Suporte a PostgreSQL + H2
- ✅ Documentação OpenAPI/Swagger
- ✅ Docker Compose para desenvolvimento
- ✅ Testes unitários e integração
- ✅ CI/CD com GitHub Actions

---

## 🚀 Tech Stack

| Componente | Versão | Propósito |
|-----------|--------|----------|
| **Java** | 17 (LTS) | Runtime |
| **Spring Boot** | 3.4.2 | Framework |
| **Spring Security** | 7.0.1 | Autenticação & Autorização |
| **Spring Data JPA** | 7.0.1 | ORM |
| **JWT (JJWT)** | 0.11.5 | Tokens |
| **PostgreSQL** | 15 | Banco (produção) |
| **H2** | - | Banco (testes) |
| **Springdoc OpenAPI** | 2.5.0 | Swagger/Documentação |
| **Lombok** | - | Redução de boilerplate |
| **Maven** | 3.8+ | Build |
---

## 🏗️ Arquitetura

### Fluxo de autenticação

```mermaid
sequenceDiagram
    participant User as Usuário
    participant API as Controller<br/>/auth
    participant Service as UsuariosService
    participant Repo as UsuariosRepository
    participant JWT as JwtUtil
    participant Filter as JwtAuthFilter

    User->>API: POST /auth/register<br/>{username, password}
    API->>Service: registra usuário
    Service->>Service: criptografa senha (BCrypt)
    Service->>Repo: salva no BD
    Repo-->>Service: usuário criado
    Service-->>API: sucesso
    API-->>User: 201 Created

    User->>API: POST /auth/login<br/>{username, password}
    API->>Service: valida credenciais
    Service->>Repo: busca por username
    Repo-->>Service: usuário encontrado
    Service->>Service: verifica BCrypt
    Service->>JWT: gera token
    JWT-->>Service: token JWT válido
    Service-->>API: token
    API-->>User: 200 {token}

    User->>API: GET /usuarios<br/>Header: Authorization: Bearer TOKEN
    API->>Filter: intercepta request
    Filter->>JWT: valida token
    JWT-->>Filter: válido
    Filter->>Service: executa endpoint
    Service-->>Filter: dados
    Filter-->>User: 200 dados
```

### Estrutura de Pacotes

```
src/main/java/com/project/SpringBootJwtTemplate/
├── config/                    # Configurações (OpenAPI, Security)
├── controller/                # Endpoints REST (/auth, etc)
├── dto/                       # Objetos de transferência
├── exception/                 # Exceções customizadas + Handler global
├── model/                     # Entidades JPA (@Entity)
├── repository/                # Spring Data JPA Repositories
├── security/                  # JWT, Filtros, SecurityConfig
└── service/                   # Regras de negócio
```

---

## 📦 Como Rodar

### Pré-requisitos
- **Java 21** ou superior
- **Maven 3.8+**
- **Docker & Docker Compose** (opcional, para BD)
- **PostgreSQL 15+** (se não usar Docker)

### 1️⃣ Desenvolvimento (com Docker)

**Mais fácil e consistente.** Toda a stack roda em containers.

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/spring-boot-jwt-template.git
cd spring-boot-jwt-template

# Suba o banco em container
docker compose up -d

# Compile e rode a aplicação
mvn clean spring-boot:run
```

A aplicação estará disponível em **http://localhost:8080**

**Variáveis de ambiente** (já configuradas em `docker-compose.yml`):
```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/springbootjwt
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=admin
```

### 2️⃣ Desenvolvimento (local com PostgreSQL)

Se preferir PostgreSQL instalado localmente:

```bash
# Crie a database
createdb springbootjwt

# Configure em application.properties (já está pronto)
# spring.datasource.url=jdbc:postgresql://localhost:5432/springbootjwt
# spring.datasource.username=postgres
# spring.datasource.password=admin

# Rode
mvn clean spring-boot:run
```

### 3️⃣ Desenvolvimento (com H2 em memória)

Para testes rápidos **sem dependências externas**:

Edite `src/main/resources/application.properties`:

```properties
# Comente as linhas PostgreSQL
# spring.datasource.url=jdbc:postgresql://localhost:5432/springbootjwt
# spring.datasource.username=postgres
# spring.datasource.password=admin

# Descomente H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

Então:
```bash
mvn clean spring-boot:run
# H2 Console: http://localhost:8080/h2-console
```

### 4️⃣ Produção (Docker)

```bash
# Build da imagem (já tem Dockerfile)
docker build -t spring-boot-jwt-api:latest .

# Rode com variáveis de produção
docker run -d \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://seu-db-host:5432/springbootjwt \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=sua_senha_segura \
  spring-boot-jwt-api:latest
```

---

## 📡 Exemplos de API

Base URL: `http://localhost:8080`

### 🔐 Autenticação

#### Registrar usuário

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joao.silva",
    "password": "Senha@123"
  }'
```

**Response (201)**:
```json
{
  "id": 1,
  "username": "joao.silva",
  "message": "Usuário registrado com sucesso"
}
```

#### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joao.silva",
    "password": "Senha@123"
  }'
```

**Response (200)**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvLnNpbHZhIiwiaWF0IjoxNjQ2NTM2MDAwfQ.xyz...",
  "expiresIn": 86400,
  "type": "Bearer"
}
```

#### Usar token protegido

```bash
curl -X GET http://localhost:8080/api/protected \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## 📚 Documentação Swagger

Acesse em tempo real:

```
http://localhost:8080/swagger-ui/index.html
```

Ou JSON raw:
```
http://localhost:8080/v3/api-docs
```

**Swagger já está configurado e integrado.** Todos os endpoints são documentados automaticamente.

### 📸 Swagger UI

![Swagger UI](./images/swagger-jwt.png)

A interface interativa permite testar todos os endpoints diretamente do navegador, com exemplos de request/response em tempo real.

---

### docker-compose.yml

Desenvolva com tudo containerizado:

```bash
docker compose up -d          # Sobe Postgres + app
docker compose logs -f        # Veja logs em tempo real
docker compose down -v        # Derruba e limpa volumes
```

**Arquivo `docker-compose.yml`** está pronto em produção com:
- ✅ PostgreSQL 15 (Alpine, leve)
- ✅ Healthcheck automático
- ✅ Volumes persistentes
- ✅ Variáveis de ambiente configuradas

**Para produção**, adicione um reverse proxy (nginx) e aproveite a imagem Docker.

---

## ✅ Testes

Testes garantem confiabilidade. O template inclui:

### Executar testes
```bash
mvn test
```

### Estrutura de testes
```
src/test/java/com/project/SpringBootJwtTemplate/
├── service/
│   └── UsuariosServiceTest.java        # Testes de lógica
├── controller/
│   └── AuthControllerTest.java         # Testes de endpoint
└── security/
    └── JwtUtilTest.java                # Testes de JWT
```

### Exemplo de teste (AuthController)

```java
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content("{\"username\":\"user\",\"password\":\"pass\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }
    
    @Test
    void testLoginInvalidCredentials() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content("{\"username\":\"user\",\"password\":\"wrong\"}"))
            .andExpect(status().isUnauthorized());
    }
}
```

### Cobertura de testes
```bash
mvn clean test jacoco:report
# Veja em: target/site/jacoco/index.html
```

---

## 🔄 CI/CD

### GitHub Actions

O workflow automático roda a cada `push` ou `pull request`:

```bash
.github/workflows/
└── maven-build.yml
```

**O que faz:**
- ✅ Testa com Java 21
- ✅ Executa `mvn clean verify`
- ✅ Roda testes automaticamente
- ✅ Valida qualidade de código (linters)
- ✅ Gera relatório de cobertura

**Status do Build:**

![Build](https://github.com/seu-usuario/spring-boot-jwt-template/actions/workflows/maven-build.yml/badge.svg)

---

## 🔒 Segurança

| Recurso | Status |
|---------|--------|
| Criptografia de senha | ✅ BCrypt (bcrypt rounds: 12) |
| Token JWT | ✅ HS256, 24h expiração |
| CORS | ⚙️ Configurável |
| Rate Limiting | ⚠️ Por implementar |
| HTTPS | ⚙️ Nginx/Produção |

### Boas práticas

1. **Senhas**: Nunca retorne em responses, sempre criptografadas.
2. **JWT**: Token armazenado no header `Authorization: Bearer <token>`.
3. **CORS**: Configure em `SecurityConfig` conforme seu frontend.
4. **Secrets**: Use variáveis de ambiente, nunca commita credenciais.

```java
// SecurityConfig já está pronto para customizar
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // ... configuração de segurança
}
```

---

## 📖 Documentação Adicional

### Endpoints principais

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| POST | `/auth/register` | Cria novo usuário | ❌ |
| POST | `/auth/login` | Retorna JWT | ❌ |
| GET | `/usuarios` | Lista usuários | ✅ |
| GET | `/usuarios/{id}` | Detalhe usuário | ✅ |

### Estrutura de erro global

Todos os erros retornam no formato:

```json
{
  "timestamp": "2026-02-25T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Usuário não encontrado",
  "path": "/usuarios/999"
}
```

Implementado em `GlobalExceptionHandler.java`.

---

## 🤝 Contribuindo

1. Faça um **fork**
2. Crie uma branch para sua feature (`git checkout -b feature/minha-feature`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona minha feature'`)
4. **Push** para a branch (`git push origin feature/minha-feature`)
5. Abra um **Pull Request**

### Guidelines

- Mantenha a estrutura de pacotes
- Adicione testes para novas funcionalidades
- Siga o padrão de código existente (Spring conventions)
- Documente mudanças relevantes

---

## 📝 Licença

MIT License – veja `LICENSE` para detalhes.

---

## 📧 Suporte

Dúvidas ou sugestões? Abra uma **Issue** no repositório.

---

**Desenvolvido com ❤️ por Roberto Lara**

Java 21 • Spring Boot 4 • PostgreSQL • Docker • JWT

