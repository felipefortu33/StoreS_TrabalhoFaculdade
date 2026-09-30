# StoreS - Sistema de Controle de Estoque e Compras

Sistema Java de console para controle de produtos, compras, vendas e estoque.

Este projeto foi desenvolvido como parte de um trabalho acadêmico. A aplicacao atual usa Java, JDBC e MySQL.

---

## 📖 Sobre o Projeto

O StoreS permite autenticar um usuario e executar operacoes basicas de estoque pelo terminal.

### 🔹 Funcionalidades Principais:
- 📦 **Cadastro e gerenciamento de produtos**
- 🛒 **Registro de vendas e baixa de estoque**
- 🔍 **Busca de produtos por nome ou ID**
- 📥 **Compra e reposicao de estoque**

---

## 🛠 Tecnologias Utilizadas

O projeto foi desenvolvido utilizando:

- ☕ **Java 21**
- 🗄️ **MySQL**
- 🔗 **JDBC**
- 🛠️ **Maven**

---

## 🚀 Como Executar o Projeto

### 🔧 **Pré-requisitos:**
Antes de começar, certifique-se de ter instalado:
- **Java 21+**
- **Maven 3.9+**
- **MySQL 8+**

### 📦 **Passos para rodar o projeto**

```bash
# Clone o repositório
git clone https://github.com/felipefortu33/StoreS_TrabalhoFaculdade.git

# Acesse a pasta do projeto
cd StoreS_TrabalhoFaculdade

# Compile o projeto e baixe as dependencias
mvn clean package

# Execute o sistema pelo Maven
mvn exec:java
```

Antes de executar, crie o banco `stores` e importe os scripts em `StoreS_SQL/`.
O acesso deve ser configurado pelas variaveis `STORES_DB_USER` e
`STORES_DB_PASSWORD`. Tambem e possivel substituir a URL padrao pelas variaveis
`STORES_DB_URL` ou pelas propriedades Java `stores.db.url`, `stores.db.user` e
`stores.db.password`.

---

## 📂 Estrutura do Repositório

```plaintext
StoreS_TrabalhoFaculdade/
|-- src/               # Codigo-fonte Java
|-- StoreS_SQL/        # Scripts de criacao do banco
|-- pom.xml            # Dependencias e build Maven
|-- TODO.md            # Plano de melhorias
|-- README.md          # Documentacao principal
```

---

## 🧑‍💻 Contribuindo

Quer ajudar a melhorar o StoreS? Siga estas etapas:

```bash
# Faça um fork do repositório

# Crie um branch para sua contribuição
git checkout -b minha-contribuicao

# Após concluir as alterações, envie um pull request
```

---

## 📧 Contato

Caso tenha dúvidas ou sugestões, sinta-se à vontade para entrar em contato:

📩 Email: [felipefortu33@gmail.com](mailto:felipefortu33@gmail.com)

💼 LinkedIn: [Felipe Fortunato](https://www.linkedin.com/in/felipe-fortunato-570749207)

Vamos construir juntos! 🚀



![Imagem Projeto](https://raw.githubusercontent.com/felipefortu33/StoreS_TrabalhoFaculdade/d35f495c68d63cfb066b425747b8f3eba699a543/Captura%20de%20tela%202025-01-06%20130925.png)
![Imagem Projeto1](https://raw.githubusercontent.com/felipefortu33/StoreS_TrabalhoFaculdade/d35f495c68d63cfb066b425747b8f3eba699a543/Captura%20de%20tela%202025-01-06%20131014.png)
