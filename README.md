# Spring Boot JWT Template

Template de projeto Spring Boot com autenticação JWT, tratamento global de exceções e estrutura pronta para iniciar novos projetos de backend em Java.

## ✨ Objetivo
Este projeto serve de base para acelerar o desenvolvimento de novos sistemas utilizando:
- Spring Boot
- Autenticação JWT (JSON Web Token)
- Cadastro e login de usuários
- Criptografia de senha
- Tratamento global de exceções
- Pronta integração com bancos PostgreSQL e H2

Você pode clonar e customizar este projeto conforme a demanda dos seus próximos trabalhos!

## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot 4
  - Spring Web MVC
  - Spring Security
  - Spring Data JPA
  - Thymeleaf
- JWT (io.jsonwebtoken)
- Banco PostgreSQL (por padrão, mas fácil trocar por outro)
- Banco H2 para testes
- Lombok

## 📦 Estrutura do Projeto
- `controller` - Endpoints REST (exemplo: autenticação)
- `model` - Entidades do banco de dados
- `repository` - Interfaces de acesso aos dados (Spring Data JPA)
- `service` - Regras de negócios (lógica de cadastro e autenticação)
- `security` - Utilitários JWT
- `exception` - Exceções customizadas e tratamento global de erros
- `DTO` - Objetos de transferência de dados

## ⚙️ Como rodar o projeto
### Pré-requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL (opcional, para rodar com banco real)

### Rodando localmente
1. **Clone este repositório**
   ```bash
   git clone <url-do-repo>
   cd spring-boot-jwt-template
   ```
2. **Configure o banco de dados**
   - O projeto já vem configurado para PostgreSQL por padrão
   - Edite o arquivo `src/main/resources/application.properties` se quiser mudar usuário, senha ou banco
   - Para testar sem banco externo, configure o H2 no properties

3. **Rode o projeto**
   ```bash
   mvn spring-boot:run
   ```

4. Acesse o projeto: `http://localhost:8080`

## 🔐 Rotas de exemplo
- `POST /auth/register` — Cadastra usuário (`username`, `password`)
- `POST /auth/login` — Autentica usuário e retorna JWT

## 🛡️ Segurança
- As senhas são guardadas de forma criptografada (BCrypt)
- O token JWT expira após 24h
- Inclui endpoints protegidos e abertos para facilitar customização

## ⚠️ Observações
- Este projeto **não possui frontend** pronto
- Ideal para novos projetos backend RESTful

---

Se for útil para você, faça um fork ou clone para servir de base para seus próprios sistemas!

---
Desenvolvido por Roberto Lara

