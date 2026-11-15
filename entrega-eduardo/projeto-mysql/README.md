# Sistema Web de Gerenciamento de Biblioteca e Histórico do Acervo

**Versão atualizada:** Java 21 · Spring Boot 3.5.16 · Thymeleaf · Spring Data JPA · Spring Security · MySQL 8.4.

Esta é a versão atualizada do projeto, preparada para execução local no Windows com MySQL 8.4 iniciado pelo Docker Compose.

## Execução resumida

1. Instale JDK 21, WSL 2, Docker Desktop e Visual Studio Code.
2. Abra esta pasta no VS Code (a pasta que contém `pom.xml` e `compose.yaml`).
3. Abra o Docker Desktop e aguarde o mecanismo iniciar.
4. Inicie o banco:

```powershell
docker compose up -d mysql
docker compose ps
```

Aguarde o serviço `mysql` ficar `healthy`.

5. Execute os testes:

```powershell
.\mvnw.cmd clean test
```

6. Inicie a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

7. Acesse `http://localhost:8080`.

O guia completo está em [`docs/GUIA_COMPLETO_INSTALACAO_EXECUCAO_GITHUB.md`](docs/GUIA_COMPLETO_INSTALACAO_EXECUCAO_GITHUB.md) e o roteiro resumido está em [`docs/EXECUCAO_LOCAL.md`](docs/EXECUCAO_LOCAL.md).

## Banco de dados

A aplicação utiliza MySQL 8.4. O Compose mantém os dados no volume `biblioteca_mysql_data`. Para parar o serviço sem apagar os dados, use `docker compose down`. **Não use `docker compose down -v` se quiser preservar os cadastros.**

O H2 é usado somente nos testes automatizados, em memória; não é o banco de execução normal da aplicação.

## Acesso de demonstração

- E-mail: `admin@biblioteca.local`
- CPF: `00000000000`
- Senha: `Biblioteca123!`

Credenciais exclusivas para demonstração local. Altere-as antes de qualquer implantação pública.

## Estrutura

- `src/main/java/.../domain`: entidades JPA.
- `repository`: interfaces Spring Data JPA.
- `service`: regras de negócio.
- `controller`: controladores MVC.
- `src/main/resources/templates`: páginas Thymeleaf.
- `src/main/resources/static`: CSS e JavaScript.
- `compose.yaml`: configuração local do MySQL 8.4.
- `docs/`: roteiro de execução, integração, requisitos e guia completo.

## Comandos úteis

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
docker compose ps
docker compose down
```

## Limitações conhecidas

- A internacionalização cobre a navegação e os controles de acessibilidade, mas ainda não todos os textos.
- A leitura por voz depende dos recursos disponíveis no navegador e no sistema operacional.
- O requisito de resposta inferior a 10 s precisa ser medido com testes reais.
- O banco local não é automaticamente compartilhado entre os computadores do grupo.
- Antes de uso real, são necessários testes adicionais, políticas de senha, backup, privacidade e revisão de segurança.

A participação de cada integrante deve ser conferida pelo histórico de commits do repositório remoto. O ZIP do projeto não contém a pasta `.git`.
