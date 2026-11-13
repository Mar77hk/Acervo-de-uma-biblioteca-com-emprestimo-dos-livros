# Sistema Web de Gerenciamento de Biblioteca e Histórico do Acervo

Protótipo acadêmico em Java 21, Spring Boot 3.5.16, Spring MVC, Thymeleaf, Spring Data JPA, Spring Security e banco relacional H2 em arquivo.

## Executar no Windows

1. Instale um JDK 21 (Eclipse Temurin ou Microsoft OpenJDK).
2. Abra esta pasta no Visual Studio Code.
3. Instale as extensões recomendadas: **Extension Pack for Java** e **Spring Boot Extension Pack**.
4. Abra **Terminal > New Terminal** e execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Na primeira execução, `mvnw.cmd` baixa Apache Maven 3.9.11 para o perfil do Windows. O Maven também baixa as dependências do projeto pela internet.

5. Acesse http://localhost:8080. Para parar, pressione `Ctrl + C` no terminal.

## Conta inicial de funcionário

- E-mail: `admin@biblioteca.local`
- CPF: `00000000000`
- Senha: `Biblioteca123!`

Altere as credenciais para uma demonstração compartilhada. A conta é criada automaticamente se ainda não houver funcionário cadastrado.

## Banco de dados

H2 é um banco SQL relacional. O arquivo fica na pasta `data/` e permanece salvo entre execuções. Não apague a pasta se quiser preservar cadastros.

Console local: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/biblioteca`
- User Name: `sa`
- Password: (em branco)

Console habilitado apenas para desenvolvimento local; não publique assim em produção.

## Organização

- `domain/`: entidades JPA (classes de domínio).
- `repository/`: interfaces de persistência Spring Data JPA.
- `service/`: regras de negócio.
- `controller/`: controladores MVC.
- `web/`: formulários/DTOs de entrada.
- `src/main/resources/templates/`: páginas Thymeleaf.
- `src/main/resources/static/`: CSS e JavaScript.

## Funcionalidades implementadas

- Livros, autores, editoras e exemplares (RF01–RF04).
- Pesquisa por título, autor, editora e ISBN (RF05).
- Histórico de cadastro/entrada, empréstimo, devolução, manutenção e descarte (RF06).
- Empréstimos com prazo escolhido pelo funcionário e mudança do estado do exemplar (RF07).
- Devolução, registro de avaria e indicação de atraso (RF08).
- Cadastro de usuário comum e de funcionário (RF09–RF10).
- Login usando e-mail ou CPF; áreas administrativas restritas a funcionário.
- Recursos de acessibilidade: seletor de idioma, leitura por voz do navegador, modo escuro, alto contraste e ajuste de fonte (RNF06).

## Comandos úteis

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package
java -jar target\biblioteca-1.0.0.jar
```

## Limitações conhecidas deste protótipo

- O seletor de idioma traduz navegação e controles de acessibilidade; nem todo o texto das páginas está internacionalizado.
- A leitura em voz depende do suporte e das vozes do navegador/sistema operacional.
- O limite de resposta inferior a 10 s (RNF04) não foi medido; deve ser testado no computador/rede da demonstração.
- O H2 é local e adequado ao protótipo. O banco não é compartilhado automaticamente entre os computadores do grupo.
- Antes de uso real, seriam necessários testes, validação de CPF/CNPJ/ISBN mais rigorosa, política de senha, backup, privacidade e revisão de segurança.

Veja `docs/MAPA_DE_REQUISITOS.md` e `docs/INTEGRACAO_DOS_MODULOS.md`.
