# 📚 Sistema de Biblioteca - API Documentation

## 📋 Índice
- [Visão Geral](#visão-geral)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Instalação e Execução](#instalação-e-execução)
- [Endpoints da API](#endpoints-da-api)
- [Regras de Negócio](#regras-de-negócio)
- [Exemplos de Uso](#exemplos-de-uso)

## 🎯 Visão Geral

Sistema de gerenciamento de biblioteca com controle completo de livros, usuários e empréstimos. A API fornece endpoints RESTful para todas as operações necessárias em uma biblioteca moderna.

## 🛠 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.x** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **Maven** - Gerenciamento de dependências
- **Jakarta Bean Validation** - Validação de dados
- **JUnit 5** - Testes unitários

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── biblioteca/
│   │           ├── api/
│   │           │   ├── controller/
│   │           │   ├── dto/
│   │           │   └── exception/
│   │           ├── core/
│   │           │   ├── model/
│   │           │   ├── service/
│   │           │   ├── validation/
│   │           │   └── exception/
│   │           └── infrastructure/
│   │               ├── repository/
│   │               └── config/
│   └── resources/
│       ├── application.properties
│       └── data.sql
└── test/
    └── java/
        └── com/
            └── biblioteca/
```

## 🚀 Instalação e Execução

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6 ou superior

### Executando a aplicação

```bash
# Clone o repositório
git clone <repository-url>
cd biblioteca-api

# Compile o projeto
mvn clean compile

# Execute a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### Livros

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/livros` | Lista todos os livros |
| GET | `/livros?titulo={titulo}` | Busca livros por título |
| GET | `/livros/{id}` | Busca livro por ID |
| POST | `/livros` | Cria um novo livro |
| PUT | `/livros/{id}` | Atualiza um livro |
| DELETE | `/livros/{id}` | Desativa um livro (exclusão lógica) |
| GET | `/livros/emprestados` | Lista livros atualmente emprestados |

### Usuários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/usuarios` | Lista todos os usuários |
| GET | `/usuarios/{id}` | Busca usuário por ID |
| POST | `/usuarios` | Cria um novo usuário |
| GET | `/usuarios/com-emprestimos` | Lista usuários com estatísticas de empréstimos |

### Empréstimos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/emprestimos` | Lista todos os empréstimos |
| GET | `/emprestimos?usuarioId={id}` | Busca empréstimos por usuário |
| POST | `/emprestimos` | Cria um novo empréstimo |
| PUT | `/emprestimos/{id}/devolver` | Registra devolução de livro |
| GET | `/emprestimos/{id}/multa` | Calcula multa por atraso |

## ⚙️ Regras de Negócio

1. **Validação de Estoque**: Empréstimo só é permitido se estoque > 0
2. **Controle de Estoque**: Ao emprestar, estoque--; ao devolver, estoque++
3. **Prazo de Devolução**: `devolucao_prevista = retirado_em + 7 dias`
4. **Prevenção de Duplicidade**: Um usuário não pode pegar o mesmo livro em aberto duplicado
5. **Validação de ISBN**: Campo obrigatório e único
6. **Registro de Devolução**: Devolução exige preenchimento de `devolvido_em`
7. **Livros Inativos**: Livros com `ativo=false` não podem ser emprestados
8. **Sistema de Multas**: R$ 2,00 por dia de atraso na devolução
9. **Relatórios**: API fornece lista de livros emprestados e usuários que os pegaram

## 💡 Exemplos de Uso

### Criar um livro
```bash
curl -X POST http://localhost:8080/livros \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "978-8532530786",
    "titulo": "O Senhor dos Anéis",
    "autor": "J.R.R. Tolkien",
    "estoque": 5
  }'
```

### Criar um usuário
```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Silva",
    "email": "maria.silva@email.com"
  }'
```

### Realizar um empréstimo
```bash
curl -X POST http://localhost:8080/emprestimos \
  -H "Content-Type: application/json" \
  -d '{
    "livroId": 1,
    "usuarioId": 1
  }'
```

### Registrar devolução
```bash
curl -X PUT http://localhost:8080/emprestimos/1/devolver
```

## 🧪 Testes

Execute os testes com o comando:
```bash
mvn test
```

## 📊 Banco de Dados

O projeto utiliza H2 Database em modo de desenvolvimento. Acesse o console do H2 em:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: `sa`
- Senha: (vazio)

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Por favor, leia as diretrizes de contribuição antes de enviar um pull request.

## 📞 Suporte

Para dúvidas ou problemas, abra uma issue no repositório do projeto.

---

**Nota**: Esta documentação está atualizada para a versão 1.0.0 do sistema.