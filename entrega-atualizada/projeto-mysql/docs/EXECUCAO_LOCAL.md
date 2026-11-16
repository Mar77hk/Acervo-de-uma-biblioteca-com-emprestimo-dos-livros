# Roteiro de execucao local — Biblioteca

Este roteiro permite que uma pessoa que recebeu o repositorio execute a aplicacao em outro computador.
A aplicacao usa Java 21, Spring Boot, Thymeleaf, Spring Security, Spring Data JPA e MySQL 8.4.

## 1. Pre-requisitos

- Windows 10/11 (os comandos abaixo usam PowerShell).
- JDK 21 instalado. Confira com `java -version`.
- Visual Studio Code (ou outra IDE Java).
- Docker Desktop com Docker Compose em execucao.
- Acesso a internet na primeira compilacao, para baixar Maven e dependencias.

## 2. Abrir o projeto

1. Extraia ou clone o repositorio.
2. No VS Code, use **File → Open Folder** e abra `entrega-atualizada/projeto-mysql`, dentro do repositorio extraido ou clonado. Essa pasta contem `pom.xml`, `mvnw.cmd`, `compose.yaml` e `src`. O projeto que esta na raiz do repositorio e a versao anterior.
3. Instale as extensoes **Extension Pack for Java** e **Spring Boot Extension Pack**, caso ainda nao estejam instaladas.

## 3. Iniciar o MySQL

No terminal integrado do VS Code, dentro da pasta que contem `compose.yaml`, execute:

```powershell
docker compose up -d mysql
```

Confira se o banco ficou saudavel:

```powershell
docker compose ps
```

Aguarde o estado `healthy` do servico `mysql`. O Compose cria o banco `biblioteca`, o usuario `biblioteca` e o volume persistente `biblioteca_mysql_data`.

As credenciais definidas no `compose.yaml` sao destinadas somente ao ambiente local de demonstracao. Nao reutilize essas senhas em um servidor publico.

## 4. Testar e executar a aplicacao

Ainda na pasta que contem `pom.xml`, rode os testes:

```powershell
.\mvnw.cmd clean test
```

Os testes usam um H2 em memoria isolado, apenas durante os testes. E possivel executar `clean test` antes da etapa 3, sem Docker ou MySQL, desde que o JDK 21 esteja disponivel. A aplicacao executada normalmente utiliza MySQL. Os testes com H2 nao comprovam a persistencia apos reiniciar o MySQL.

Depois, inicie a aplicacao:

```powershell
.\mvnw.cmd spring-boot:run
```

Quando o terminal indicar que o servidor Tomcat iniciou na porta 8080 e que `BibliotecaApplication` foi iniciada, abra:

```text
http://localhost:8080
```

Para encerrar a aplicacao, volte ao terminal e pressione `Ctrl + C`.

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
