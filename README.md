# Sistema de Gestão de Condomínio — N2

Projeto acadêmico de gestão condominial com interface no terminal, desenvolvido em Java com Hibernate/JPA e MySQL. A aplicação principal é `gestao.App`.

**Universidade Católica de Brasília (UCB) — Engenharia de Software**

## Integrantes

- Gabriel Andrade
- Gabriel Ribeiro
- Guilherme Bernardes
- Erick Gabriel

## Funcionalidades disponíveis

O menu principal reúne oito áreas:

| Opção | Área | Recursos |
| --- | --- | --- |
| 1 | Cadastros | Usuários, unidades, vínculos entre usuários e unidades, cargos e funcionários |
| 2 | Portaria | Visitantes, autorizações e registros de entrada e saída |
| 3 | Ocorrências | Registro, consulta e acompanhamento de ocorrências |
| 4 | Lazer e reservas | Espaços comuns, solicitação de reservas e alteração de status |
| 5 | Encomendas | Recebimento, consulta e controle de retirada |
| 6 | Garagem | Cadastro e consulta de vagas e veículos |
| 7 | Manutenção | Abertura, consulta e atualização de solicitações |
| 8 | Financeiro | Taxas condominiais, pagamentos e acompanhamento de status |

A opção **0** encerra o sistema. Nos submenus, **0** retorna ao menu anterior.

O banco também possui tabelas para serviços de manutenção, comunicados, documentos e despesas. Essas quatro funcionalidades ainda não têm entidades/DAOs e menus próprios na aplicação atual.

## Tecnologias e requisitos

| Componente | Versão/configuração |
| --- | --- |
| Java | JDK 17 ou superior; compilação configurada para Java 17 |
| Maven | 3.9 recomendado; validação realizada com 3.9.9 |
| Hibernate ORM | 6.6.15.Final |
| Jakarta Persistence | 3.1.0 |
| MySQL Connector/J | 9.2.0 |
| Banco de dados | MySQL 8.4 |
| Docker Compose | Opcional, para executar o banco |

É necessário instalar um **JDK**, que inclui `javac`. Apenas o JRE não é suficiente para compilar.

## Estrutura do projeto

| Caminho | Conteúdo |
| --- | --- |
| [pom.xml](pom.xml) | Dependências e configuração de compilação |
| [src/main/java/gestao/App.java](src/main/java/gestao/App.java) | Aplicação e menus no terminal |
| [src/main/java/gestao](src/main/java/gestao) | Classes de POO e demonstração de Collections |
| [src/main/java/gestao/persistence/entity](src/main/java/gestao/persistence/entity) | Entidades JPA e enums |
| [src/main/java/gestao/persistence/dao](src/main/java/gestao/persistence/dao) | Operações de persistência |
| [src/main/java/gestao/persistence/teste](src/main/java/gestao/persistence/teste) | Programas de teste com método `main` |
| [src/main/java/gestao/persistence/util/HibernateUtil.java](src/main/java/gestao/persistence/util/HibernateUtil.java) | Inicialização e encerramento do Hibernate |
| [src/main/resources/hibernate.cfg.xml](src/main/resources/hibernate.cfg.xml) | Conexão e mapeamentos |
| [database/schema.sql](database/schema.sql) | Criação das 22 tabelas e dados iniciais |
| [docker-compose.yml](docker-compose.yml) | Serviço MySQL 8.4 |
| [docs](docs) | Documentação acadêmica e referências do projeto |

## Como executar

### 1. Obter o código

```bash
git clone https://github.com/GabrielACarvalhoo/condominio_N2.git
cd condominio_N2
java -version
javac -version
mvn -version
```

O repositório exige uma conta com acesso autorizado.

### 2. Preparar o banco

Escolha uma das alternativas.

**Com Docker Compose:**

```bash
docker compose up -d mysql
docker compose ps
docker compose logs mysql
```

Aguarde o serviço ficar saudável (`healthy`). O Compose publica a porta `3306`, cria `gestao_condominio` e importa `database/schema.sql` na primeira inicialização de um volume vazio.

Se a porta `3306` já estiver ocupada por outro MySQL, ajuste o mapeamento no Compose e a URL JDBC. Em volumes já inicializados, alterar o script não executa uma nova importação automaticamente.

**Com MySQL instalado localmente:**

No terminal, a partir da raiz do projeto:

```bash
mysql -u root -p < database/schema.sql
```

Também é possível abrir e executar esse arquivo no MySQL Workbench. Use uma instalação/base nova para a importação: o script não é inteiramente idempotente e não deve ser reaplicado sobre tabelas já existentes.

### 3. Conferir a conexão

O arquivo `src/main/resources/hibernate.cfg.xml` usa estes valores de desenvolvimento:

| Parâmetro | Valor |
| --- | --- |
| Servidor | `localhost` |
| Porta | `3306` |
| Banco | `gestao_condominio` |
| Usuário | `root` |
| Senha local | `root123` |
| Fuso horário JDBC | `America/Sao_Paulo` |
| `hibernate.hbm2ddl.auto` | `validate` |

Se usar outro usuário, senha ou porta, ajuste esse arquivo. Os valores padrão coincidem com o Compose e servem para desenvolvimento local.

O Hibernate **valida** a estrutura; ele não cria as tabelas. Por isso, importe o SQL antes de executar os programas que usam persistência.

### 4. Compilar e iniciar

```bash
mvn clean package
mvn dependency:build-classpath -Dmdep.outputFile=target/classpath.txt
```

No macOS ou Linux:

```bash
java -cp "target/classes:$(cat target/classpath.txt)" gestao.App
```

No IntelliJ IDEA, abra o `pom.xml` como projeto Maven, selecione um JDK 17 ou superior, carregue as dependências e execute o método `main` de `gestao.App`. Essa alternativa também pode ser usada no Windows.

O JAR gerado em `target/` não inclui as dependências nem declara uma classe principal para execução direta com `java -jar`.

### 5. Primeiros cadastros

O SQL inicial insere **8 perfis e 4 cargos**, mas não cria usuários, unidades ou funcionários. No menu **1 — Cadastros**, cadastre usuários e unidades antes de criar vínculos ou registros que dependam deles. Para operações que exigem funcionário, cadastre-o vinculando um usuário e um cargo existentes.

## Testes

### Compilação e testes Maven

```bash
mvn clean test package
```

Atualmente não há suíte em `src/test/java`. Portanto, o sucesso de `mvn test` **não significa que os testes de persistência foram executados**: os programas em `src/main/java/gestao/persistence/teste` precisam ser iniciados individualmente.

### Executar os testes existentes

Depois da compilação e da geração do classpath, por exemplo:

```bash
java -cp "target/classes:$(cat target/classpath.txt)" gestao.persistence.teste.TesteConexao
```

Substitua a classe pelo teste desejado ou execute seu `main` pela IDE.

| Classe | Finalidade/pré-requisito |
| --- | --- |
| `gestao.persistence.teste.TesteConexao` | Conectar e validar os mapeamentos contra o schema |
| `gestao.persistence.teste.TestePagamento` | Seis cenários de pagamento; exige unidade com ID 2 |
| `gestao.persistence.teste.TesteSolicitacaoManutencao` | Cadastro, consultas e atualização; exige usuário com ID 2 |
| `gestao.teste.TesteFuncionarioCRUD` | CRUD de funcionário; exige usuário ID 2 sem vínculo de funcionário e cargo ID 1 |
| `gestao.persistence.teste.TesteEspacoComum` | Cadastro e listagem de espaço |
| `gestao.persistence.teste.TesteVaga` | Cadastro e listagem de vaga |
| `gestao.persistence.teste.TesteVeiculo` | Cadastro e listagem; exige unidade ID 2 e vaga ID 1 |
| `gestao.persistence.teste.TesteTaxaCondominial` | Teste interativo: solicita unidade, dados da taxa e ID para consulta |
| `gestao.SistemaCondominioCollections` | Demonstração de ArrayList, HashSet e HashMap; não utiliza banco |

A classe `TesteFuncionarioCRUD` está no diretório `persistence/teste/cargo`, mas declara o pacote `gestao.teste`; use o nome completo indicado na tabela.

Execute os testes em uma **base exclusiva de testes**. Vários deles usam IDs e valores fixos, inserem ou excluem registros e podem falhar quando repetidos sem preparar os dados novamente. Alguns capturam exceções e ainda encerram com código zero: confira também as mensagens e o resultado no banco.

Para reproduzir os pré-requisitos usados na validação, execute o SQL abaixo **somente em uma base de testes recém-criada pelo schema**, antes dos testes da tabela:

```sql
USE gestao_condominio;

INSERT INTO unidade (id_unidade, bloco, numero, andar, metragem)
VALUES (1, 'T', '101', 1, 65.50),
       (2, 'T', '102', 1, 70.00);

INSERT INTO usuario (id_usuario, id_perfil, nome, cpf, email, senha_hash)
VALUES (1, 1, 'Teste Um', '11111111111', 'teste1@example.invalid', 'apenas_teste'),
       (2, 5, 'Teste Dois', '22222222222', 'teste2@example.invalid', 'apenas_teste');
```

Esses registros são fictícios e não representam credenciais de autenticação. Execute `TesteVaga` antes de `TesteVeiculo` para criar a vaga ID 1 na base nova.

### Validação realizada em 16/11/2026

Código-base: commit `f526416d7e2e068de1c0bed41441c2c36118ca0a`. Ambiente: JDK 17, Maven 3.9.9 e MySQL 8.4 em base isolada.

| Verificação | Resultado |
| --- | --- |
| Compilação das 84 classes e geração do pacote Maven | Aprovada |
| Importação do schema e conexão com validação Hibernate | Aprovada |
| Pagamento parcial, consulta, quitação, bloqueio de pagamento em taxa quitada e duas exclusões com recálculo de status | 6 aprovados, 0 falhas |
| Limpeza dos dados criados por `TestePagamento` | Confirmada por consulta SQL |
| Cadastro, consulta e atualização de manutenção | Aprovados |
| Cadastro, consulta, atualização e exclusão de funcionário | Aprovados |
| Cadastro e listagem de espaço, vaga e veículo | Aprovados |
| Teste interativo de taxa com valor decimal informado com vírgula | Aprovado |
| Navegação nos oito menus, retorno e encerramento | Aprovados |
| Opções inválidas no menu principal, incluindo entrada não numérica | Tratadas |
| Demonstração de Collections | Executada sem erros |
| Verificações adicionais de POO: taxas, multa, cobertura, CPF duplicado, cópias de coleções e saldo | 19 verificações aprovadas em programa temporário de validação |

Esta validação cobre os cenários listados. As 19 verificações adicionais não foram adicionadas como suíte ao repositório. Não foram validados todos os fluxos de portaria, reservas e encomendas, nem concorrência ou a inicialização pelo Docker Compose.

## Documentação

- [Schema SQL](database/schema.sql)
- [Diagrama do banco](database/diagrama%20%281%29.png)
- [Descrição do condomínio](docs/DescriçãoDoCondominio.md)
- [Documento ITEM3](docs/ITEM3.md)
- [Referências do Figma](docs/figma.md)
