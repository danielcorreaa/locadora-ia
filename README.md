# 🎬 Locadora de Vídeos - API

API REST desenvolvida em **Java + Spring Boot** para gerenciamento de uma locadora de filmes.  
O sistema permite o cadastro de clientes, controle de locações, devoluções de filmes e aplicação de multas por atrasos.  

---

## 📌 Funcionalidades
- **Clientes**: cadastro, atualização, bloqueio/desbloqueio e consulta.  
- **Filmes**: cadastro, listagem, busca e exclusão.  
- **Locações**: registrar locação de filmes, listar por cliente, devolver filmes.  
- **Multas**: geração de multas automáticas em devoluções atrasadas, listagem e pagamento.  

---

## 🚀 Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database** (para ambiente de testes)
- **Swagger / OpenAPI** (documentação interativa)
- **JUnit 5 & Mockito** (testes)

---

## ⚙️ Endpoints

### 👤 Clientes
`/clientes`
- `POST /clientes` → cadastrar cliente  
- `GET /clientes/{cpf}` → buscar cliente por CPF  
- `GET /clientes` → listar todos os clientes (com paginação)  
- `PUT /clientes/{cpf}` → atualizar cliente  
- `DELETE /clientes/{cpf}` → remover cliente  

---

### 🎥 Filmes
`/filmes`
- `POST /filmes` → cadastrar filme  
- `GET /filmes/{id}` → buscar filme por ID  
- `GET /filmes` → listar todos os filmes (com paginação e filtros)  
- `PUT /filmes/{id}` → atualizar filme  
- `DELETE /filmes/{id}` → remover filme  

---

### 📦 Locações
`/locacoes`
- `POST /locacoes` → cadastrar locação  
- `POST /locacoes/bulk` → cadastrar múltiplas locações  
- `POST /locacoes/devolver` → devolver filme e calcular multa (se necessário)  
- `GET /locacoes/{id}` → buscar locação por ID  
- `GET /locacoes` → listar locações (com paginação e filtros)  
- `PUT /locacoes/{id}` → atualizar locação  
- `DELETE /locacoes/{id}` → remover locação  

---

### 💰 Multas
`/multas`
- `POST /multas` → criar multa  
- `GET /multas` → listar todas as multas  
- `GET /multas/{id}` → buscar multa por ID  
- `GET /multas/cliente/{clienteId}` → listar multas de um cliente  
- `PUT /multas/{multaId}/pagar` → pagar multa  

---

## 📖 Exemplos de Requests

### ➕ Cadastrar Cliente
```json
POST /clientes
{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "email": "joao@email.com",
  "bloqueado": false
}
