# Sistema de Gestão de Condomínio — N2

Aplicação acadêmica para gerenciamento de rotinas de um condomínio, desenvolvida em **Java**, com persistência de dados por **Hibernate/JPA** e banco de dados **MySQL 8.4**. O banco é executado localmente por meio do **Docker Compose**.

**Instituição:** Universidade Católica de Brasília (UCB)  
**Curso:** Engenharia de Software  
**Classe principal:** `gestao.App`

## Integrantes

- Gabriel Andrade
- Gabriel Ribeiro
- Guilherme Bernardes
- Erick Gabriel

## Sobre o projeto

O sistema reúne funcionalidades para apoiar a administração de um condomínio. A aplicação é executada pelo terminal e organiza suas operações em oito módulos acessíveis por um menu principal.

### Módulos e funcionalidades

| Opção | Módulo | Funcionalidades |
| --- | --- | --- |
| 1 | Cadastros | Gerenciamento de usuários, unidades, vínculos entre usuários e unidades, cargos e funcionários |
| 2 | Portaria | Cadastro de visitantes, autorizações e registros de entrada e saída |
| 3 | Ocorrências | Registro e acompanhamento de ocorrências |
| 4 | Lazer e reservas | Gerenciamento de espaços comuns e reservas |
| 5 | Encomendas | Registro, consulta e controle de retirada de encomendas |
| 6 | Garagem | Gerenciamento de vagas e veículos |
| 7 | Manutenção | Registro de solicitações de manutenção |
| 8 | Financeiro | Taxas condominiais, pagamentos e acompanhamento de status |

A opção `0` encerra a aplicação. Nos submenus, a opção `0` retorna ao menu anterior.

> **Observação sobre o modelo de dados:** o banco possui 22 tabelas. Algumas representam estruturas de apoio ou funcionalidades previstas no modelo de dados que não possuem um módulo completo no menu atual, como comunicados, documentos, despesas e serviços de manutenção.

## Tecnologias utilizadas

| Tecnologia | Versão/configuração |
| --- | --- |
| Java | JDK 21 recomendado |
| Compilação Java | Java 17 (`maven.compiler.release=17`) |
| Maven | Maven integrado ao IntelliJ ou Maven 3.9+ |
| Hibernate ORM | 6.6.15.Final |
| Jakarta Persistence API | 3.1.0 |
| MySQL Connector/J | 9.3.0 |
| MySQL | 8.4 |
| Docker Compose | Ambiente local do banco de dados |

É necessário instalar um **JDK**, e não apenas um JRE, pois o JDK inclui as ferramentas de compilação Java. Embora o projeto recomende o JDK 21 para desenvolvimento e execução, o Maven compila o código com compatibilidade Java 17.

## Organização do repositório

```text
condominio_N2/
├── database/
│   ├── schema.sql
│   ├── dados_iniciais.sql
│   └── diagrama (1).png
├── docs/
│   ├── DescriçãoDoCondominio.md
│   └── GUIA_EXECUCAO.md
├── src/
│   └── main/
│       ├── java/gestao/
│       │   ├── App.java
│       │   ├── exemplos/
│       │   │   └── poo/
│       │   │       ├── Pessoa.java
│       │   │       ├── Morador.java
│       │   │       ├── ConsultaPessoa.java
│       │   │       ├── Notificavel.java
│       │   │       ├── NotificacaoEmail.java
│       │   │       └── DemonstracaoPOO.java
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

### Principais diretórios e arquivos

- **`App.java`:** inicialização da aplicação e menus de navegação.
- **`entity/`:** entidades JPA que representam os dados do sistema.
- **`dao/`:** classes responsáveis pelas operações de acesso e persistência de dados.
- **`util/`:** utilitários de configuração e gerenciamento do Hibernate.
- **`teste/`:** classes de teste e validação executadas individualmente.
- **`exemplos/poo/`:** exemplos didáticos e independentes que demonstram conceitos de Programação Orientada a Objetos em Java.
- **`database/schema.sql`:** script de criação do banco, das tabelas e dos dados básicos necessários, como perfis e cargos.
- **`database/dados_iniciais.sql`:** script separado com registros fictícios para demonstração do sistema.
- **`database/diagrama (1).png`:** diagrama do banco de dados.
- **`docs/`:** documentação do projeto e instruções de execução.
- **`docker-compose.yml`:** configuração do serviço MySQL em container.
- **`pom.xml`:** dependências e configuração de compilação Maven.

### Exemplos didáticos de POO

A pasta `src/main/java/gestao/exemplos/poo` contém classes criadas para demonstrar conceitos estudados na disciplina:

| Classe | Conceito demonstrado |
| --- | --- |
| `Pessoa.java` e `Morador.java` | Classes, herança e sobrescrita de método (`@Override`) |
| `ConsultaPessoa.java` | Sobrecarga de métodos |
| `Notificavel.java` e `NotificacaoEmail.java` | Interface e implementação |
| `DemonstracaoPOO.java` | Execução dos exemplos, incluindo polimorfismo |

**Importante:** esses arquivos são exemplos educacionais independentes. Eles não fazem parte do fluxo operacional dos menus, nem substituem as entidades, DAOs ou funcionalidades reais do sistema de condomínio. Foram organizados separadamente para permitir a demonstração dos conceitos de POO sem alterar a lógica principal da aplicação.

Para executar a demonstração, abra `DemonstracaoPOO.java` no IntelliJ IDEA e execute seu método `main`. A saída será apresentada no console da IDE. Não é necessário iniciar o banco de dados para executar essa demonstração.

## Configuração e execução

O procedimento detalhado para preparar o ambiente no Windows está no **[Guia de configuração e execução](docs/GUIA_EXECUCAO.md)**.

### Pré-requisitos

- JDK 21 (64 bits).
- Git.
- IntelliJ IDEA.
- Docker Desktop.

O Maven pode ser utilizado pela integração do IntelliJ, sem necessidade de instalação global.

### 1. Clonar o repositório

```powershell
git clone https://github.com/GabrielACarvalhoo/condominio_N2.git
cd condominio_N2
```

Abra a pasta clonada no IntelliJ IDEA e aguarde o reconhecimento do projeto Maven e o download das dependências.

### 2. Iniciar o banco de dados

Abra o Docker Desktop. No terminal, dentro da pasta raiz do projeto, execute:

```powershell
docker compose up -d
```

Verifique se o container está em execução:

```powershell
docker ps
```

O container `condominio-mysql` deve aparecer como saudável (`healthy`) após a inicialização do MySQL.

### 3. Compilar o projeto

No IntelliJ, abra **Maven → Lifecycle** e execute `clean` e, em seguida, `compile`.

Se o Maven estiver instalado e configurado no PATH, também é possível compilar pelo terminal:

```powershell
mvn clean compile
```

### 4. Executar a aplicação

No IntelliJ, abra:

```text
src/main/java/gestao/App.java
```

Execute o método `main`. O menu principal será apresentado no console da IDE.

## Banco de dados

O banco utilizado pelo projeto é o **`gestao_condominio`**, executado localmente em um container MySQL 8.4.

### Configuração local atual

| Parâmetro | Valor |
| --- | --- |
| Host | `localhost` |
| Porta | `3306` |
| Banco de dados | `gestao_condominio` |
| Usuário | `root` |
| Senha local | `root123` |
| Fuso horário JDBC | `America/Sao_Paulo` |

Esses parâmetros correspondem à configuração de desenvolvimento presente em `docker-compose.yml` e `hibernate.cfg.xml`. **As credenciais são destinadas exclusivamente ao ambiente local de desenvolvimento. Não devem ser utilizadas em produção ou com dados reais.**

### Criação do esquema

Na primeira inicialização com um volume MySQL vazio, o Docker executa automaticamente o arquivo `database/schema.sql`, criando o banco e suas 22 tabelas, além dos dados básicos definidos nesse script.

O Hibernate está configurado com `hbm2ddl.auto=validate`: ele valida se o esquema existente é compatível com as entidades mapeadas, mas não cria nem atualiza as tabelas.

**Importante:** os scripts de inicialização do MySQL são executados somente quando o volume de dados é inicializado pela primeira vez. Se o volume já existir, alterações posteriores em `schema.sql` não serão aplicadas automaticamente.

### Carga de dados fictícios para demonstração (opcional)

O arquivo `database/dados_iniciais.sql` contém dados fictícios do cenário **Residencial Jardim das Palmeiras**, para facilitar a demonstração dos módulos. Esse arquivo **não é executado automaticamente** pelo Docker Compose.

Para executá-lo em um banco já inicializado, abra o PowerShell na pasta raiz do projeto e rode:

```powershell
Get-Content .\\database\\dados_iniciais.sql | docker exec -i condominio-mysql mysql -uroot -proot123 gestao_condominio
```

> **Atenção:** esse script apaga os registros existentes das tabelas operacionais antes de inserir os dados de demonstração. Ele preserva a estrutura do banco e as tabelas de perfis e cargos, mas substitui os dados operacionais. Faça um backup antes de executá-lo se houver dados que deseja manter. Os dados pessoais do cenário são fictícios. O campo `senha_hash` utiliza um valor demonstrativo que **não é uma senha válida e não permite autenticação**.

### Consultar o banco

Para acessar o MySQL pelo terminal:

```powershell
docker exec -it condominio-mysql mysql -uroot -p
```

Informe a senha local e execute os comandos:

```sql
SHOW DATABASES;
USE gestao_condominio;
SHOW TABLES;
```

### Parar e reiniciar o ambiente

Para parar os serviços sem apagar os dados:

```powershell
docker compose down
```

Para iniciar novamente:

```powershell
docker compose up -d
```

> **Atenção:** o comando `docker compose down -v` também remove o volume persistente do MySQL e apaga os dados armazenados nele. Utilize-o somente se desejar recriar o banco do zero e não precisar preservar os dados locais.

## Compilação e testes

A compilação pode ser realizada pelo Maven integrado ao IntelliJ (**Maven → Lifecycle → clean** e depois **compile**) ou pelo terminal com:

```powershell
mvn clean compile
```

As classes localizadas em `src/main/java/gestao/persistence/teste` são programas de teste independentes, executados individualmente conforme sua finalidade e seus pré-requisitos de dados. Elas não constituem, por si só, uma suíte automatizada executada pelo comando `mvn test`.

## Documentação

- [Guia de configuração e execução no Windows](docs/GUIA_EXECUCAO.md)
- [Descrição do condomínio](docs/DescriçãoDoCondominio.md)
- [Script de criação do banco de dados](database/schema.sql)
- [Script de dados fictícios para demonstração](database/dados_iniciais.sql)
- [Diagrama do banco de dados](database/diagrama%20%281%29.png)

---

Projeto acadêmico desenvolvido para a disciplina correspondente ao trabalho N2 do curso de Engenharia de Software da Universidade Católica de Brasília.
