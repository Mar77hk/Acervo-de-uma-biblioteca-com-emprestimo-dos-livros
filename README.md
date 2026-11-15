# Sistema Web de Gerenciamento de Biblioteca e Histórico do Acervo

Protótipo acadêmico em Java 21, Spring Boot 3.5.16, Spring MVC, Thymeleaf, Spring Data JPA e Spring Security. Por padrão, usa H2 local; MySQL é opcional.

## Executar no Windows

Consulte o roteiro completo em [`docs/EXECUCAO_LOCAL.md`](docs/EXECUCAO_LOCAL.md). Em resumo:

1. Instale JDK 21 e Visual Studio Code com Extension Pack for Java.
2. Abra no VS Code a pasta que contém `pom.xml` e `compose.yaml`.
3. Inicie a aplicação pelo botão **Run** da classe `BibliotecaApplication` no VS Code, ou pelo terminal:

```powershell
.\mvnw.cmd spring-boot:run
```

4. Acesse http://localhost:8080. O banco H2 é criado automaticamente em `data/biblioteca.mv.db`.

Docker/MySQL é opcional. Para usar MySQL, configure `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e `DB_DRIVER=com.mysql.cj.jdbc.Driver`.

## Conta inicial de funcionário

- E-mail: `admin@biblioteca.local`
- CPF: `00000000000`
- Senha: `Biblioteca123!`

A conta é criada automaticamente se ainda não houver funcionário cadastrado. Essas credenciais são apenas para demonstração local e devem ser alteradas antes de qualquer implantação pública.

## Banco de dados

A aplicação usa H2 em arquivo por padrão e mantém os dados em `data/biblioteca.mv.db`. O `compose.yaml` permite usar MySQL 8.4 opcionalmente.

O banco padrão é local e não pede credenciais. Para usar MySQL, configure `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e `DB_DRIVER=com.mysql.cj.jdbc.Driver`.

Os testes usam H2 em memória.

## Organização

- `domain/`: entidades JPA.
- `repository/`: interfaces de persistência Spring Data JPA.
- `service/`: regras de negócio.
- `controller/`: controladores MVC.
- `web/`: formulários/DTOs de entrada.
- `src/main/resources/templates/`: páginas Thymeleaf.
- `src/main/resources/static/`: CSS e JavaScript.
- `docs/EXECUCAO_LOCAL.md`: roteiro para executar o projeto em outro computador.

## Funcionalidades implementadas

- Livros, autores, editoras e exemplares (RF01–RF04).
- Pesquisa por título, autor, editora e ISBN (RF05).
- Histórico de cadastro/entrada, empréstimo, devolução, manutenção e descarte (RF06).
- Empréstimos com prazo escolhido pelo funcionário e mudança do estado do exemplar (RF07).
- Devolução, registro de avaria e indicação de atraso (RF08).
- Cadastro de usuário comum e de funcionário (RF09–RF10).
- Login usando e-mail ou CPF, Spring Security e áreas administrativas restritas a funcionário.
- Senhas armazenadas com hash BCrypt.
- Recursos de acessibilidade: seletor de idioma, leitura por voz do navegador, modo escuro, alto contraste e ajuste de fonte (RNF06).

## Comandos úteis

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package
java -jar target\biblioteca-1.0.0.jar
```

A execução normal usa H2 persistente em arquivo e não exige MySQL nem Docker. Os testes usam H2 em memória.

## Limitações conhecidas deste protótipo

- O seletor de idioma traduz navegação e controles de acessibilidade; nem todo o texto das páginas está internacionalizado.
- A leitura em voz depende do suporte e das vozes do navegador/sistema operacional.
- O limite de resposta inferior a 10 s (RNF04) não foi medido; deve ser testado no computador/rede da demonstração.
- O banco MySQL não é compartilhado automaticamente entre computadores diferentes; para uma base compartilhada, é necessário hospedar o servidor MySQL em local acessível ao grupo.
- Antes de uso real, seriam necessários testes, validação de CPF/CNPJ/ISBN mais rigorosa, política de senha, backup, privacidade e revisão de segurança.

A participação dos integrantes deve ser comprovada no histórico do repositório Git remoto. O ZIP de entrega não inclui a pasta `.git`. Consulte o roteiro para os comandos de conferência dos commits.

Veja também `docs/MAPA_DE_REQUISITOS.md` e `docs/INTEGRACAO_DOS_MODULOS.md`.
