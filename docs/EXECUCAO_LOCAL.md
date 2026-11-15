# Roteiro de execucao local — Biblioteca

Este roteiro permite que uma pessoa que recebeu o repositorio execute a aplicacao em outro computador.
A aplicacao usa Java 21, Spring Boot, Thymeleaf, Spring Security e Spring Data JPA. H2 em arquivo e usado por padrao; MySQL e opcional.

## 1. Pre-requisitos

- Windows 10/11 (os comandos abaixo usam PowerShell).
- JDK 21 instalado. Confira com `java -version`.
- Visual Studio Code (ou outra IDE Java).
- Acesso a internet na primeira compilacao, para baixar Maven e dependencias.

## 2. Abrir o projeto

1. Extraia ou clone o repositorio.
2. No VS Code, use **File → Open Folder** e abra a pasta `biblioteca-projeto-completo`, onde estao `pom.xml`, `mvnw.cmd`, `compose.yaml` e `src`.
3. Instale as extensoes **Extension Pack for Java** e **Spring Boot Extension Pack**, caso ainda nao estejam instaladas.

## 3. Executar no VS Code

O banco H2 local e criado automaticamente, sem Docker ou instalacao de MySQL. No VS Code:

1. Abra a pasta `biblioteca-projeto-completo`, onde estao `pom.xml`, `mvnw.cmd` e `src`.
2. Instale a extensao **Extension Pack for Java**.
3. Abra `src/main/java/br/edu/ufscar/biblioteca/BibliotecaApplication.java` e clique em **Run** acima do metodo `main`.

Tambem e possivel iniciar pelo terminal integrado, nessa mesma pasta:

```powershell
.\mvnw.cmd spring-boot:run
```

A primeira execucao precisa de internet para baixar Maven e as dependencias. Quando o Tomcat iniciar na porta 8080, acesse `http://localhost:8080`. O banco fica em `data/biblioteca.mv.db`.

Para encerrar, volte ao terminal e pressione `Ctrl + C`.

## 4. MySQL opcional

Se preferir MySQL, inicie-o com `docker compose up -d mysql` e configure `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e `DB_DRIVER=com.mysql.cj.jdbc.Driver` antes de executar. O Compose cria o usuario e o volume persistente descritos em `compose.yaml`.

## 5. Primeiro acesso

Na primeira execucao, o sistema cria um funcionario inicial caso ainda nao exista nenhum funcionario no banco:

| Campo | Credencial local |
|---|---|
| E-mail | `admin@biblioteca.local` |
| CPF | `00000000000` |
| Senha | `Biblioteca123!` |

Entre pela tela **Entrar** usando o e-mail ou o CPF. A senha e armazenada com BCrypt, nao em texto puro. Altere a credencial de demonstracao antes de qualquer implantacao publica.

## 6. Persistencia e encerramento do banco

Os dados do MySQL ficam no volume Docker `biblioteca_mysql_data` e continuam disponiveis depois que o container e parado.

Para parar o container sem apagar os dados:

```powershell
docker compose down
```

**Nao use `docker compose down -v`** quando quiser preservar os cadastros, pois a opcao `-v` remove o volume do banco.

## 7. Testes funcionais recomendados

1. Entre como funcionario e confira os menus administrativos.
2. Cadastre um autor e uma editora.
3. Cadastre um livro com mais de um exemplar.
4. Pesquise por titulo, autor, editora e ISBN.
5. Cadastre um usuario comum e encerre a sessao.
6. Entre com o usuario comum e confira que ele consegue pesquisar, mas nao acessar operacoes administrativas.
7. Entre novamente como funcionario, registre um emprestimo e depois uma devolucao, com e sem avaria.
8. Consulte o historico das movimentacoes.

## 8. Conferir participacao no Git

A participacao dos integrantes e comprovada pelo historico do repositorio Git remoto, nao por arquivos Java isolados nem pelo ZIP. Cada integrante deve realizar commits com sua propria conta e enviar suas alteracoes para o repositorio compartilhado.

Comandos uteis na pasta do repositorio:

```powershell
git log --all --oneline --graph --decorate
git shortlog -sne --all
git log --all --author="Nome do integrante" --oneline
```

O ZIP de entrega nao contem a pasta `.git`; portanto, ele nao carrega o historico de commits. Para a avaliacao, mantenham o repositorio remoto atualizado e fornecam o link ao professor.

## 9. Configurar outro servidor MySQL

Por padrao, a aplicacao usa `localhost:3306`, banco `biblioteca`, usuario `biblioteca` e a senha local definida em `application.properties`. Para usar outro servidor, configure as variaveis de ambiente `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` antes de iniciar a aplicacao. Exemplo no PowerShell:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/biblioteca?useSSL=true&serverTimezone=America/Sao_Paulo"
$env:DB_USERNAME = "seu_usuario"
$env:DB_PASSWORD = "sua_senha"
.\mvnw.cmd spring-boot:run
```

Em ambientes compartilhados ou de producao, use credenciais proprias, conexao protegida por TLS e um usuario de banco com permissao apenas sobre o schema da aplicacao.

## Observacao sobre dados anteriores

Esta versao usa MySQL. O arquivo local `data/biblioteca.mv.db` da versao antiga H2 nao e convertido automaticamente. A nova base MySQL comeca vazia, e o funcionario de demonstracao e criado automaticamente. Exporte e importe os dados antigos separadamente caso precise preserva-los.
