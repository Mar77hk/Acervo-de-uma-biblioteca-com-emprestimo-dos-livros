# Validação de cadastro e login

## Escopo

Esta validação cobre os requisitos RF09 (usuário comum), RF10 (funcionário), autenticação e restrições de acesso. O projeto analisado é `entrega-atualizada/projeto-mysql`.

Os testes de `UsuarioFluxoTests` usam Spring Boot, MockMvc, o filtro real do Spring Security, os serviços e os repositórios JPA. Não há simulação do banco por mocks: os dados são gravados no H2 de teste e cada cenário é revertido ao final. Nos testes de permissões, a identidade é fornecida pelo suporte de testes do Spring Security; os testes de login enviam identificador e senha pelo fluxo real de autenticação.

## Como repetir os testes

Na pasta `entrega-atualizada/projeto-mysql`, com JDK 21 disponível:

```powershell
.\mvnw.cmd clean test
```

O Maven Wrapper baixa Maven e dependências na primeira execução. Não é preciso iniciar Docker ou MySQL para estes testes. O resultado detalhado fica em `target/surefire-reports/`.

## Cenários automatizados

| Cenário | Resultado esperado |
|---|---|
| Cadastro válido | Cria um usuário comum, redireciona ao login e registra o histórico. |
| Proteção da senha | O valor salvo é um hash BCrypt que confere com a senha original. |
| CPF e CEP com pontuação | Aceita `529.982.247-25` e `13560-000`, salvando somente os dígitos. |
| Espaços nas bordas | Remove espaços de nome, e-mail, CPF e CEP antes da validação; preserva a senha. |
| E-mail duplicado | Rejeita também a variação em maiúsculas, sem novo usuário ou histórico. |
| CPF duplicado | Rejeita o segundo cadastro, sem novo usuário ou histórico. |
| Campos obrigatórios vazios | Mostra erros e não salva usuário. |
| E-mail, CPF, CEP ou senha inválidos | Recusa e-mail sem formato válido, letras nos documentos e senha curta. |
| Tentativa de definir perfil no cadastro público | O perfil continua sendo `COMUM`. |
| Login por e-mail | Aceita maiúsculas e espaços nas bordas. |
| Login por CPF | Aceita CPF sem máscara e no formato `000.000.000-00`. |
| Senha incorreta | Recusa o login; espaços extras na senha não são descartados. |
| Letras antes do CPF no login | Recusa o identificador em vez de convertê-lo silenciosamente em CPF. |
| Visitante na administração | Redireciona para o login. |
| Usuário comum na administração | Retorna HTTP 403 para usuários, novo livro, empréstimos e histórico. |
| Usuário comum criando funcionário | Retorna HTTP 403 e não salva usuário. |
| Funcionário criando funcionário | Permite acessar a tela e cadastrar o perfil `FUNCIONARIO`. |
| Cadastro sem token CSRF | Retorna HTTP 403 e não salva usuário. |
| Formulário renderizado | Os campos comportam CPF com 14 caracteres e CEP com 9 caracteres. |

Os dois testes preexistentes de `BibliotecaApplicationTests` também são mantidos: inicialização do contexto e hash da senha do funcionário inicial.

## Resultado observado em 16/11/2026

| Execução | Testes | Falhas | Erros | Ignorados |
|---|---:|---:|---:|---:|
| Código recebido, com os novos testes adicionados | 30 | 4 | 0 | 0 |
| Após as correções | 30 | 0 | 0 | 0 |

São 28 casos novos em `UsuarioFluxoTests` e 2 testes preexistentes. As quatro falhas iniciais foram: cadastro com CPF/CEP formatados e espaços, limite dos campos HTML, login por CPF formatado e login por CPF formatado com espaços. O login por e-mail com espaços já passou na execução inicial.

Ambiente: Windows, Eclipse Temurin JDK 21.0.13+9, Apache Maven 3.9.11, Spring Boot 3.5.16 e H2 2.3.232. As ferramentas foram utilizadas de forma portátil. A execução final usou o objetivo `test`, com cache Maven local e modo offline (`-o`), e terminou com `BUILD SUCCESS` às 15:39 de 16/11/2026, horário de São Paulo. A duração informada pelo Maven foi de 9,262 segundos; isso não mede o requisito de tempo de resposta da aplicação.

Também foram conferidos 17 links locais dos READMEs e do guia desta contribuição. O teste de links verifica a existência dos arquivos/pastas no projeto, não a disponibilidade futura de URLs no GitHub.

## Verificações manuais com MySQL

Estas verificações precisam de MySQL em execução. Não devem ser marcadas como aprovadas apenas porque os testes H2 passaram.

1. Inicie `docker compose up -d mysql` e espere `healthy` em `docker compose ps`.
2. Execute `.\mvnw.cmd spring-boot:run` e abra `http://localhost:8080`.
3. Cadastre um usuário fictício usando CPF e CEP formatados. Não use dados pessoais reais para a demonstração.
4. Entre com esse usuário por e-mail e, após sair, por CPF formatado.
5. Confirme o bloqueio ao abrir `/admin/usuarios` com esse usuário.
6. Pare a aplicação com `Ctrl+C` e execute `docker compose down` (sem `-v`).
7. Inicie novamente o MySQL, aguarde `healthy` e reinicie a aplicação.
8. Entre com o mesmo usuário e confira seu cadastro e histórico usando uma conta de funcionário.

| Verificação manual | Estado nesta entrega |
|---|---|
| Inicialização da aplicação com MySQL 8.4 | Pendente: Docker/MySQL indisponíveis no ambiente de validação. |
| Persistência após reiniciar aplicação e banco | Pendente: depende do cenário MySQL acima. |
| Inspeção visual no navegador | Pendente; os testes automatizados verificam as respostas e a renderização HTML, não a aparência. |

## Limites

A validação de CPF confere formato e quantidade de dígitos; não calcula os dígitos verificadores. Não houve auditoria completa de segurança, teste de carga, teste de cadastros simultâneos ou validação integral dos módulos de livros e empréstimos. A configuração MySQL e as credenciais de demonstração preexistentes foram preservadas.
