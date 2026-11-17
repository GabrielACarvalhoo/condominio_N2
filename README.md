# Sistema de Gestão de Condomínio — N2

Aplicação acadêmica de gestão condominial desenvolvida em **Java**, com interface de terminal, persistência usando **Hibernate/JPA** e banco de dados **MySQL 8.4** executado localmente com Docker Compose.

**Ponto de entrada:** `gestao.App`  
**Universidade:** Universidade Católica de Brasília (UCB) — Engenharia de Software

## Integrantes

- Gabriel Andrade
- Gabriel Ribeiro
- Guilherme Bernardes
- Erick Gabriel

## Funcionalidades

A aplicação possui um menu principal com oito módulos:

| Opção | Módulo | Funcionalidades |
| --- | --- | --- |
| 1 | Cadastros | Usuários, unidades, vínculos entre usuários e unidades, cargos e funcionários |
| 2 | Portaria | Visitantes, autorizações e registros de entrada e saída |
| 3 | Ocorrências | Registro e acompanhamento de ocorrências |
| 4 | Lazer e reservas | Espaços comuns e reservas |
| 5 | Encomendas | Registro, consulta e controle de retirada de encomendas |
| 6 | Garagem | Vagas e veículos |
| 7 | Manutenção | Solicitações de manutenção |
| 8 | Financeiro | Taxas condominiais, pagamentos e acompanhamento de status |

A opção `0` encerra a aplicação; nos submenus, `0` retorna ao menu anterior.

O banco contém **22 tabelas**. Algumas tabelas dão suporte a funcionalidades previstas no modelo de dados, mas não possuem um módulo completo no menu atual, como comunicados, documentos, despesas e serviços de manutenção.

## Tecnologias

| Tecnologia | Versão/configuração |
| --- | --- |
| Java | JDK 21 recomendado |
| Versão de compilação | Java 17 (`maven.compiler.release=17`) |
| Maven | Maven integrado ao IntelliJ ou Maven 3.9+ |
| Hibernate ORM | 6.6.15.Final |
| Jakarta Persistence API | 3.1.0 |
| MySQL Connector/J | 9.2.0 |
| MySQL | 8.4 |
| Docker Compose | Para subir o banco local |

> É necessário instalar um **JDK**, não apenas um JRE. O JDK inclui o compilador `javac`.

## Estrutura do projeto

```text
condominio_N2/
├── database/
│   ├── schema.sql
│   └── diagrama (1).png
├── docs/
│   └── documentação acadêmica
├── src/
│   └── main/
│       ├── java/gestao/
│       │   ├── App.java
│       │   └── persistence/
│       │       ├── dao/
│       │       ├── entity/
│       │       ├── teste/
│       │       └── util/
│       └── resources/
│           └── hibernate.cfg.xml
├── docker-compose.yml
└── pom.xml
```

- **App.java:** inicialização e menus da aplicação.
- **entity/:** entidades JPA que representam os dados do sistema.
- **dao/:** operações de acesso e persistência no banco.
- **util/:** utilitários de configuração e gerenciamento do Hibernate.
- **teste/:** classes de teste/validação executadas individualmente.
- **database/schema.sql:** criação do banco, tabelas e dados iniciais de perfis e cargos.
- **docker-compose.yml:** configuração do serviço MySQL.
- **pom.xml:** dependências e configuração de compilação Maven.

## Como configurar e executar no Windows

O roteiro completo, incluindo comandos de verificação e solução de problemas comuns, está em **[docs/GUIA_EXECUCAO.md](docs/GUIA_EXECUCAO.md)**.

Resumo do processo:

1. Instale JDK 21, Git, IntelliJ IDEA e Docker Desktop.
2. Clone o repositório e abra a pasta no IntelliJ.
3. Aguarde o IntelliJ reconhecer o `pom.xml` como projeto Maven e baixar as dependências.
4. Inicie o Docker Desktop.
5. No terminal, na pasta raiz do projeto, execute:

   ```powershell
   docker compose up -d
   ```

6. Confirme que o container `condominio-mysql` está `healthy`:

   ```powershell
   docker ps
   ```

7. No IntelliJ, abra a janela **Maven → Lifecycle** e execute `clean` e depois `compile`. Se o Maven estiver instalado no PATH, também pode usar `mvn clean compile` no terminal.
8. Abra `src/main/java/gestao/App.java` e execute o método `main`.

Na primeira inicialização de um volume MySQL vazio, o Docker importa automaticamente `database/schema.sql` e cria as 22 tabelas. O Hibernate está configurado com `hbm2ddl.auto=validate`: ele **valida** o esquema existente, mas não cria as tabelas.

## Banco de dados e configuração local

A configuração atual de desenvolvimento utiliza:

| Parâmetro | Valor |
| --- | --- |
| Host | `localhost` |
| Porta | `3306` |
| Banco | `gestao_condominio` |
| Usuário | `root` |
| Senha de desenvolvimento | `root123` |
| Fuso horário JDBC | `America/Sao_Paulo` |

Esses valores correspondem ao `docker-compose.yml` e ao `hibernate.cfg.xml` versionados neste repositório. **São credenciais simples destinadas somente ao ambiente local de desenvolvimento; não as utilize em produção nem com dados reais.**

Para conferir o banco pelo terminal:

```powershell
docker exec -it condominio-mysql mysql -uroot -p
```

Informe a senha de desenvolvimento e, no prompt do MySQL, execute:

```sql
SHOW DATABASES;
USE gestao_condominio;
SHOW TABLES;
```

O script `database/schema.sql` é executado automaticamente somente quando o volume de dados do MySQL é inicializado pela primeira vez. Se o volume já existir, alterações posteriores no SQL não serão importadas automaticamente.

### Preservar ou reiniciar os dados

Para parar os serviços sem apagar o banco:

```powershell
docker compose down
```

Para iniciar novamente:

```powershell
docker compose up -d
```

> **Atenção:** `docker compose down -v` remove também o volume do MySQL e apaga os dados locais armazenados nele. Use somente se realmente quiser recriar o banco do zero.

## Compilação e testes

A compilação pode ser feita pelo terminal, caso o Maven esteja instalado:

```powershell
mvn clean compile
```

Ou pelo IntelliJ: **Maven → Lifecycle → clean** e depois **compile**.

As classes da pasta `src/main/java/gestao/persistence/teste` são programas de teste independentes. Execute cada classe pelo IntelliJ conforme sua finalidade e seus pré-requisitos de dados. Elas não constituem, por si só, uma suíte automatizada executada pelo comando `mvn test`.

## Documentação complementar

- [Guia de configuração e execução](docs/GUIA_EXECUCAO.md)
- [Script de criação do banco](database/schema.sql)
- [Diagrama do banco de dados](database/diagrama%20%281%29.png)
- [Descrição do condomínio](docs/DescriçãoDoCondominio.md)
- [Documento ITEM3](docs/ITEM3.md)
- [Referências do Figma](docs/figma.md)
