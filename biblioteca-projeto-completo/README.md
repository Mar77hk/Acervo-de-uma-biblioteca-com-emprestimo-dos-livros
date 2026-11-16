# Sistema Web de Gerenciamento de Biblioteca e Histórico do Acervo

Protótipo acadêmico em Java 21, Spring Boot 3.5.16, Spring MVC, Thymeleaf, Spring Data JPA, Spring Security e MySQL 8.4.

## Executar no Windows

Consulte o roteiro completo em [`docs/EXECUCAO_LOCAL.md`](docs/EXECUCAO_LOCAL.md). Em resumo:

1. Instale JDK 21, Docker Desktop e Visual Studio Code.
2. Abra no VS Code a pasta que contém `pom.xml` e `compose.yaml`.
3. Inicie o banco de dados:

```powershell
docker compose up -d mysql
```

4. Execute os testes:

```powershell
.\mvnw.cmd clean test
```

5. Inicie a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

6. Acesse http://localhost:8080.

## Conta inicial de funcionário

- E-mail: `admin@biblioteca.local`
- CPF: `00000000000`
- Senha: `Biblioteca123!`

A conta é criada automaticamente se ainda não houver funcionário cadastrado. Essas credenciais são apenas para demonstração local e devem ser alteradas antes de qualquer implantação pública.

## Banco de dados

A aplicação usa MySQL 8.4. O `compose.yaml` inicia o serviço e cria o banco `biblioteca`, com os dados persistidos no volume Docker `biblioteca_mysql_data`. Não use `docker compose down -v` caso queira manter os dados.

As credenciais padrão no arquivo de configuração são exclusivamente para desenvolvimento local. Em outro ambiente, configure `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` como variáveis de ambiente.

O H2 foi mantido somente no escopo de testes automatizados, em banco em memória. Ele não é usado para persistência da aplicação.

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

Os testes usam H2 em memória e não precisam que o MySQL esteja iniciado. A execução normal da aplicação exige MySQL ativo.

## Limitações conhecidas deste protótipo

- O seletor de idioma traduz navegação e controles de acessibilidade; nem todo o texto das páginas está internacionalizado.
- A leitura em voz depende do suporte e das vozes do navegador/sistema operacional.
- O limite de resposta inferior a 10 s (RNF04) não foi medido; deve ser testado no computador/rede da demonstração.
- O banco MySQL não é compartilhado automaticamente entre computadores diferentes; para uma base compartilhada, é necessário hospedar o servidor MySQL em local acessível ao grupo.
- Antes de uso real, seriam necessários testes, validação de CPF/CNPJ/ISBN mais rigorosa, política de senha, backup, privacidade e revisão de segurança.

A participação dos integrantes deve ser comprovada no histórico do repositório Git remoto. O ZIP de entrega não inclui a pasta `.git`. Consulte o roteiro para os comandos de conferência dos commits.

Veja também `docs/MAPA_DE_REQUISITOS.md` e `docs/INTEGRACAO_DOS_MODULOS.md`.
