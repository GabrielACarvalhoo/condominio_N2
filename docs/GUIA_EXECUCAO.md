# Guia de configuração e execução — Windows

Este guia descreve como preparar um computador novo para executar o **Sistema de Gestão de Condomínio — N2**.

## 1. Pré-requisitos

Instale:

- **JDK 21 (64 bits):** necessário para compilar e executar o projeto. O código é compilado para Java 17.
- **IntelliJ IDEA:** ambiente de desenvolvimento.
- **Git for Windows:** para clonar o repositório.
- **Docker Desktop:** para executar o MySQL em container.

O Maven pode ser utilizado pela integração do IntelliJ. Não é obrigatório instalá-lo separadamente.

Após instalar o JDK, abra um novo PowerShell e confira:

```powershell
java -version
javac -version
git --version
docker --version
docker compose version
```

Os comandos `java` e `javac` devem apontar para um JDK compatível (JDK 21 recomendado).

## 2. Clonar e abrir o projeto

No PowerShell, escolha uma pasta para os projetos e execute:

```powershell
git clone https://github.com/GabrielACarvalhoo/condominio_N2.git
cd condominio_N2
```

No IntelliJ IDEA:

1. Selecione **Open** e escolha a pasta `condominio_N2` (ou **Get from VCS** para clonar diretamente).
2. Aguarde o IntelliJ carregar o projeto.
3. Verifique se o arquivo `pom.xml` está na raiz.
4. Abra a janela **Maven** em **View → Tool Windows → Maven**.
5. Confira se o projeto aparece com o grupo **Lifecycle**. O IntelliJ deve baixar as dependências Maven.

Se o projeto não for reconhecido, clique com o botão direito em `pom.xml` e selecione a opção para adicionar/carregar como projeto Maven.

## 3. Iniciar o MySQL pelo Docker

1. Abra o Docker Desktop e aguarde o estado **Engine running**.
2. No terminal do IntelliJ ou PowerShell, confirme que o diretório atual é a raiz do projeto (a pasta que contém `docker-compose.yml`).
3. Execute:

```powershell
docker compose up -d
```

O Compose irá baixar a imagem `mysql:8.4`, criar a rede e o volume persistente, iniciar o container `condominio-mysql` e importar `database/schema.sql` na primeira inicialização do volume.

Confira o estado:

```powershell
docker ps
docker compose ps
```

O container deve aparecer como `Up` e, após a inicialização do MySQL, como `healthy`.

Se precisar consultar os logs:

```powershell
docker compose logs mysql
```

## 4. Conferir o banco e as tabelas

Acesse o MySQL:

```powershell
docker exec -it condominio-mysql mysql -uroot -p
```

Quando solicitado, informe a senha local configurada no `docker-compose.yml` (atualmente `root123`). A senha não aparece enquanto é digitada; isso é normal.

No prompt `mysql>`, execute um comando por vez:

```sql
SHOW DATABASES;
USE gestao_condominio;
SHOW TABLES;
```

O banco esperado é `gestao_condominio`; o script atual cria 22 tabelas. Para sair:

```sql
exit;
```

**Importante:** o script de inicialização do MySQL é executado apenas quando o volume de dados é criado pela primeira vez. Se um volume antigo já existir, o Compose preserva os dados e não reaplica automaticamente o `schema.sql`.

## 5. Compilar o projeto

### Opção A — Maven integrado ao IntelliJ (recomendada)

1. Abra a janela **Maven** na lateral direita.
2. Expanda o projeto.
3. Expanda **Lifecycle**.
4. Execute `clean` com dois cliques.
5. Quando terminar, execute `compile` com dois cliques.

O resultado esperado é `BUILD SUCCESS`.

### Opção B — Maven no PowerShell

Se o Maven estiver instalado e configurado no PATH:

```powershell
mvn clean compile
```

Se o PowerShell informar que `mvn` não é reconhecido, utilize a opção A. Isso não significa que o projeto esteja com erro: o Maven integrado ao IntelliJ pode funcionar normalmente sem o comando global.

## 6. Executar a aplicação

1. No painel **Project**, navegue até `src/main/java/gestao/App.java`.
2. Abra o arquivo.
3. Clique no ícone de execução ao lado do método `main`.
4. Escolha **Run 'App.main()'**.

A aplicação deverá iniciar o menu principal no console do IntelliJ.

## 7. Encerrar e preservar o banco

Para parar o container sem excluir os dados:

```powershell
docker compose down
```

Na próxima utilização, basta abrir o Docker Desktop e executar novamente:

```powershell
docker compose up -d
```

**Não utilize `docker compose down -v` se quiser preservar os dados.** A opção `-v` remove o volume persistente do banco.

## 8. Problemas comuns

### O comando `mvn` não é reconhecido

Use o Maven integrado ao IntelliJ pela janela **Maven → Lifecycle**. A instalação global do Maven é opcional.

### Erro ao conectar ao MySQL

- Confira se o Docker Desktop está em execução.
- Execute `docker ps` e verifique se `condominio-mysql` está saudável.
- Confira se a porta `3306` não está sendo utilizada por outro serviço.
- Verifique se usuário, senha, porta e nome do banco no `hibernate.cfg.xml` correspondem à configuração do Compose.

### O banco existe, mas não há tabelas

Verifique se o volume já existia antes de o script `database/schema.sql` ser adicionado ou alterado. O MySQL não executa novamente os scripts de `docker-entrypoint-initdb.d` em um volume já inicializado.

Para recriar o banco do zero, é possível remover o volume com `docker compose down -v` e iniciar novamente com `docker compose up -d`. **Isso apaga todos os dados armazenados no volume.** Faça isso somente em ambiente local descartável e se tiver certeza de que não precisa dos dados.

### O MySQL pede a senha e não mostra os caracteres

Esse comportamento é normal no terminal. Digite a senha e pressione Enter.

## 9. Configuração de desenvolvimento

A configuração atual utiliza host `localhost`, porta `3306`, banco `gestao_condominio`, usuário `root` e senha local `root123`. Esses valores são para desenvolvimento local e não devem ser usados em produção ou com dados reais.
