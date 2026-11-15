# Sistema Web de Gerenciamento de Biblioteca

Protótipo acadêmico para gerenciamento de acervo, usuários e empréstimos. O projeto usa Java 21, Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, Spring Security e MySQL 8.4. O banco H2 é usado somente nos testes automatizados.

## Como executar no Windows e no VS Code

### 1. Instale os pré-requisitos

- JDK 21. Confira no PowerShell com `java -version`.
- Docker Desktop, aberto e em execução.
- Visual Studio Code com a extensão **Extension Pack for Java**.
- Acesso à internet na primeira execução, para baixar Maven e as dependências.

### 2. Abra a pasta correta

No VS Code, abra a pasta raiz do projeto, ou seja, a pasta que contém `pom.xml`, `mvnw.cmd`, `compose.yaml` e `src`.

Se você abriu o ZIP fornecido, essa pasta é `biblioteca-projeto-completo`. Se clonou este repositório, abra a pasta clonada.

### 3. Inicie o banco MySQL

Abra o terminal integrado do VS Code nessa pasta e execute:

```powershell
docker compose up -d mysql
docker compose ps
```

Espere o serviço `mysql` aparecer como `healthy` antes de iniciar a aplicação. O Compose cria o banco `biblioteca` e o usuário local de demonstração.

### 4. Inicie a aplicação

No VS Code, abra `src/main/java/br/edu/ufscar/biblioteca/BibliotecaApplication.java` e clique em **Run** acima do método `main`.

Ou execute pelo terminal integrado:

```powershell
.\mvnw.cmd spring-boot:run
```

Na primeira execução, o Maven baixa as dependências; esse processo pode levar alguns minutos. Quando o servidor iniciar, abra [http://localhost:8080](http://localhost:8080).

Para encerrar a aplicação, volte ao terminal e pressione `Ctrl + C`.

### 5. Entre no sistema

Na primeira execução, o sistema cria um funcionário de demonstração caso ainda não exista nenhum funcionário no banco:

| Campo | Valor |
|---|---|
| E-mail | `admin@biblioteca.local` |
| CPF | `00000000000` |
| Senha | `Biblioteca123!` |

Use o e-mail ou CPF para entrar. A senha é armazenada com hash BCrypt. Essas credenciais são apenas para demonstração local; altere-as antes de qualquer implantação pública.

## Banco de dados e preservação dos dados

A aplicação usa MySQL 8.4. O Docker Compose mantém os registros no volume `biblioteca_mysql_data`; parar ou recriar o contêiner não apaga esse volume.

Para parar o banco sem remover o volume:

```powershell
docker compose down
```

**Não use `docker compose down -v`** se quiser manter os dados: a opção `-v` apaga os volumes do projeto.

O projeto foi atualizado de uma configuração antiga com H2 para MySQL. Se você já cadastrou dados no arquivo H2 `data/biblioteca.mv.db`, esse arquivo continua separado e não é importado automaticamente para o MySQL. Faça uma migração antes de descartar o arquivo, caso precise desses registros.

As credenciais presentes em `application.properties` e `compose.yaml` servem apenas para desenvolvimento local. Para conectar a outro servidor MySQL, defina as variáveis antes de iniciar a aplicação:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/biblioteca?serverTimezone=America/Sao_Paulo"
$env:DB_USERNAME = "seu_usuario"
$env:DB_PASSWORD = "sua_senha"
.\mvnw.cmd spring-boot:run
```

## Testes

Os testes usam H2 em memória e não precisam de Docker nem de um servidor MySQL. Execute:

```powershell
.\mvnw.cmd clean test
```

## Solução de problemas

- **O MySQL ainda não iniciou:** abra o Docker Desktop e confira `docker compose ps`. Só inicie a aplicação quando o serviço `mysql` estiver `healthy`.
- **A porta 3306 já está em uso:** encerre o outro serviço MySQL ou altere a porta em `compose.yaml` e a URL do banco em `application.properties`.
- **A porta 8080 já está em uso:** encerre a outra aplicação ou altere `server.port` em `application.properties`.
- **O terminal diz que `mvnw.cmd` não existe:** confira se o terminal está na pasta que contém `pom.xml` e `mvnw.cmd`.

## Funcionalidades

- Cadastro e pesquisa de livros, autores, editoras e exemplares.
- Histórico de cadastros, empréstimos, devoluções, manutenção e descarte.
- Empréstimos e devoluções com registro de atrasos e avarias.
- Login por e-mail ou CPF, autorização de áreas administrativas por perfil e senhas protegidas com BCrypt.
- Acessibilidade com seletor de idioma, leitura por voz, modo escuro, alto contraste e ajuste de fonte.

## Organização do código

- `domain/`: entidades JPA.
- `repository/`: interfaces Spring Data JPA.
- `service/`: regras de negócio.
- `controller/`: controladores MVC.
- `web/`: formulários e DTOs.
- `src/main/resources/templates/`: páginas Thymeleaf.
- `src/main/resources/static/`: CSS e JavaScript.
- `docs/EXECUCAO_LOCAL.md`: roteiro detalhado de execução.
- `docs/MAPA_DE_REQUISITOS.md` e `docs/INTEGRACAO_DOS_MODULOS.md`: documentação do projeto.
