# Módulo 32 — Java + Hibernate

Projeto inicial de estudo (curso EBAC, módulo 32) com JPA/Hibernate.

## Tecnologias

- Java 25
- Maven (wrapper incluído: `mvnw` / `mvnw.cmd`)
- Hibernate ORM 6.6 (Jakarta Persistence 3.1)
- PostgreSQL (execução) e H2 em memória (testes)
- JUnit 5

## Estrutura

```
src/main/java/app/App.java              # exemplo de uso (cadastra, altera e lista)
src/main/java/app/domain/Produto.java   # entidade JPA
src/main/java/app/dao/GenericDAO.java   # CRUD genérico com controle de transação
src/main/java/app/dao/ProdutoDAO.java   # DAO específico (busca por código)
src/main/java/app/util/JPAUtil.java     # EntityManagerFactory único
src/main/resources/META-INF/persistence.xml   # conexão com PostgreSQL
src/test/resources/META-INF/persistence.xml   # unidade "modulo32-test" com H2
src/test/java/app/dao/ProdutoDAOTest.java
```

## Banco de dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE modulo32;
```

A conexão padrão é `localhost:5432/modulo32`, usuário `postgres`, senha `postgres`.
Para usar outros valores, edite o `persistence.xml` ou defina as variáveis de ambiente
`DB_URL`, `DB_USER` e `DB_PASSWORD`. A tabela `tb_produto` é criada automaticamente
(`hibernate.hbm2ddl.auto=update`).

## Como rodar

```powershell
.\mvnw.cmd test          # testes (H2, não precisa de PostgreSQL)
.\mvnw.cmd compile exec:java   # roda App.main contra o PostgreSQL
```
