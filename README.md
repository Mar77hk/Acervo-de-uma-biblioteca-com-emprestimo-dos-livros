Sistema Web de Gerenciamento de Biblioteca e Histórico do Acervo
Sistema web acadêmico para organização do acervo de uma biblioteca, com pesquisa de livros, cadastro de autores, editoras e exemplares, controle de empréstimos e devoluções, registro de movimentações e gerenciamento de usuários.

A aplicação é desenvolvida em Java 21 e Spring Boot 3.5.16, com interface web em Thymeleaf, persistência em MySQL 8.4 e execução local facilitada por Docker Compose e Maven Wrapper.
Finalidade: protótipo acadêmico para execução e demonstração local. As credenciais e configurações de desenvolvimento não devem ser utilizadas em ambiente público.

1. Funcionalidades
Módulo	Recursos disponíveis
Acervo	Cadastro e edição de livros, autores, editoras e exemplares.
Consulta	Pesquisa de livros por título, autor, editora e ISBN; visualização de detalhes e disponibilidade.
Movimentações	Histórico de eventos de cadastro/entrada, empréstimo, devolução, manutenção e descarte.
Empréstimos	Registro de empréstimos de exemplares disponíveis, com prazo definido pelo funcionário (de 1 a 90 dias).
Devoluções	Registro de devolução, identificação de atraso e indicação de avaria; exemplares avariados passam para manutenção.
Usuários	Cadastro público de usuários comuns e cadastro administrativo de funcionários.
Acesso	Login por e-mail ou CPF, controle de acesso por perfil e armazenamento de senhas com BCrypt.
Acessibilidade	Controles de tamanho da fonte, contraste e tema escuro; leitura por voz e seleção de idioma com cobertura parcial.


Perfis: visitantes podem consultar o catálogo e acessar o cadastro público. As operações administrativas, incluindo acervo, empréstimos e histórico, exigem autenticação como funcionário. Usuários comuns autenticados não recebem acesso administrativo.
2. Tecnologias
- Java 21
- Spring Boot 3.5.16, Spring MVC e Spring Security
- Thymeleaf (páginas HTML renderizadas no servidor)
- Spring Data JPA / Hibernate
- MySQL 8.4 (banco da aplicação)
- Docker Desktop e Docker Compose (banco local)
- Maven e Maven Wrapper (mvnw / mvnw.cmd)
- HTML, CSS e JavaScript
- JUnit / Spring Boot Test e H2 somente nos testes
3. Pré-requisitos
Para executar no Windows 10/11 com VS Code, instale:
Programa	Download	Uso
JDK 21	Eclipse Temurin / Adoptium	Compilação e execução do Java
Visual Studio Code	code.visualstudio.com	Editor/IDE
Git	git-scm.com	Clonar o repositório
Docker Desktop	docker.com	Executar o MySQL


No VS Code, instale as extensões Extension Pack for Java e Spring Boot Extension Pack.

O Maven não precisa ser instalado separadamente, pois o projeto contém o Maven Wrapper. É necessário acesso à internet na primeira execução para baixar as dependências.

Confira as instalações no terminal (menu Terminal > New Terminal):
java -version
git --version
docker --version
docker compose version
O comando java -version deve indicar a versão 21. Inicie o Docker Desktop antes de executar o banco.
4. Clonar o projeto do GitHub e abrir no VS Code
No GitHub, abra o repositório e copie a URL HTTPS em Code > HTTPS. No terminal, execute:
git clone https://github.com/USUARIO/NOME-DO-REPOSITORIO.git
cd NOME-DO-REPOSITORIO
code .
Substitua USUARIO/NOME-DO-REPOSITORIO pelo endereço real do seu repositório. Se code . não estiver disponível, abra o VS Code e use File > Open Folder.
Abra a pasta raiz da aplicação, aquela que contém pom.xml, compose.yaml, mvnw.cmd e src/. Se o repositório tiver uma pasta interna biblioteca-projeto-completo, abra essa pasta.

5. Iniciar o banco MySQL
Com o Docker Desktop em execução, abra um terminal integrado no VS Code, na pasta que contém compose.yaml, e execute:
docker compose up -d mysql
Verifique o serviço:
docker compose ps
Aguarde até que o contêiner biblioteca-mysql indique estado healthy. O Compose configura:
Parâmetro	Valor para desenvolvimento local
Serviço	mysql
Versão	MySQL 8.4
Banco	biblioteca
Host/porta	localhost:3306
Usuário	biblioteca
Persistência	Volume Docker biblioteca_mysql_data


As senhas de desenvolvimento já estão definidas em compose.yaml e são compatíveis com os valores padrão de application.properties. Não reutilize essas credenciais em produção.
6. Executar a aplicação
Na raiz da aplicação, execute:
.\mvnw.cmd spring-boot:run
Na primeira vez, o Maven Wrapper faz o download das dependências. Quando o terminal informar que o servidor foi iniciado na porta 8080, abra:

http://localhost:8080

Para encerrar a aplicação, use Ctrl + C no terminal.
Alternativa: executar pelo VS Code
Com as extensões Java instaladas, abra:

src/main/java/br/edu/ufscar/biblioteca/BibliotecaApplication.java

Clique em Run sobre o método main ou abra Run and Debug e selecione Executar Biblioteca, configuração incluída em .vscode/launch.json.
Nas duas alternativas, o MySQL precisa estar ativo. Não é necessário executar simultaneamente pelo terminal e pelo botão Run.

Linux e macOS
Após instalar Java 21, Git e Docker com Compose, os comandos principais são os mesmos, exceto pelo Maven Wrapper:
chmod +x ./mvnw
docker compose up -d mysql
docker compose ps
./mvnw spring-boot:run
7. Primeiro acesso e catálogo demonstrativo
Se ainda não existir funcionário no banco, a aplicação cria automaticamente a seguinte conta para demonstração:
Campo	Valor
E-mail	admin@biblioteca.local
CPF	00000000000
Senha	Biblioteca123!
Perfil	Funcionário


Na página inicial, selecione Entrar. Informe o e-mail ou CPF e a senha indicada.
Dados de demonstração
Se o banco ainda não contiver livros, a aplicação também cadastra automaticamente um catálogo inicial:
- Autor: Machado de Assis
- Editora: Editora Demonstração
- Livro: Dom Casmurro
- ISBN demonstrativo: 9780000000001
- Exemplar: patrimônio DEMO-0001, inicialmente disponível
Esses valores são dados fictícios/de teste, inclusive o ISBN. A inicialização não duplica o catálogo ao reiniciar: o catálogo é inserido apenas quando ainda não há livros cadastrados. A conta administrativa inicial também só é criada quando ainda não há funcionário no banco.

Acesse http://localhost:8080/livros para consultar o acervo sem autenticação.
8. Roteiro sugerido de demonstração
1. Acesse o catálogo público e pesquise por Dom Casmurro.
2. Abra os detalhes do livro e confira a disponibilidade do exemplar demonstrativo.
3. Faça login com a conta inicial de funcionário.
4. Cadastre outro autor, uma editora e um livro com exemplares.
5. Cadastre um usuário comum em Cadastro.
6. Entre em Empréstimos, escolha um exemplar disponível e um usuário, defina o prazo e registre o empréstimo.
7. Confira a alteração do estado do exemplar e o histórico de movimentações.
8. Registre a devolução. Teste, se desejar, uma devolução com avaria para observar o encaminhamento do exemplar à manutenção.
9. Saia da conta administrativa e confirme que a consulta pública continua disponível, mas as operações administrativas ficam protegidas.
9. Executar testes e gerar o JAR
Os testes automatizados utilizam H2 em memória e, portanto, não exigem MySQL:
.\mvnw.cmd clean test
Para compilar e empacotar:
.\mvnw.cmd clean package
Com o MySQL em execução, também é possível iniciar o arquivo gerado:
java -jar target\biblioteca-1.0.0.jar
No Linux/macOS, use ./mvnw e target/biblioteca-1.0.0.jar.
10. Encerrar o banco sem perder dados
Após encerrar a aplicação Java, é possível desligar o contêiner MySQL:
docker compose down
O banco fica armazenado no volume Docker biblioteca_mysql_data e permanece disponível nas próximas execuções.

Atenção: docker compose down -v remove também o volume e pode apagar os dados cadastrados. Não execute esse comando caso queira preservar o acervo.
11. Configuração de outro banco de dados
A aplicação lê as seguintes variáveis de ambiente, definidas em src/main/resources/application.properties:
- DB_URL: URL JDBC do MySQL;
- DB_USERNAME: usuário do banco;
- DB_PASSWORD: senha do banco.
Exemplo de configuração no PowerShell para um banco diferente:
$env:DB_URL = "jdbc:mysql://localhost:3306/biblioteca?useSSL=true&serverTimezone=America/Sao_Paulo"
$env:DB_USERNAME = "seu_usuario"
$env:DB_PASSWORD = "sua_senha"
.\mvnw.cmd spring-boot:run
A URL ilustrada precisa ser adaptada às configurações de TLS e autenticação do servidor utilizado. O banco remoto deve existir e estar acessível. Não armazene credenciais reais no repositório.

Observação: esta versão não importa automaticamente os dados de versões antigas que utilizavam H2 em arquivo. Caso seja necessária uma migração, faça a exportação e a importação separadamente.
12. Estrutura do projeto
biblioteca-projeto-completo/
├── .mvn/wrapper/                  # Configuração do Maven Wrapper
├── .vscode/                       # Extensões e configuração de execução
├── compose.yaml                   # Serviço MySQL no Docker
├── pom.xml                        # Dependências Maven / Spring Boot
├── mvnw / mvnw.cmd                # Scripts Maven Wrapper
├── README.md                      # Este guia
├── docs/
│   ├── EXECUCAO_LOCAL.md          # Instruções detalhadas para execução local
│   ├── MAPA_DE_REQUISITOS.md      # Relação dos requisitos funcionais/não funcionais
│   └── INTEGRACAO_DOS_MODULOS.md  # Organização e integração dos módulos
└── src/
    ├── main/
    │   ├── java/br/edu/ufscar/biblioteca/
    │   │   ├── config/            # Segurança e inicialização dos dados
    │   │   ├── controller/        # Controladores HTTP/MVC
    │   │   ├── domain/            # Entidades e enumerações JPA
    │   │   ├── repository/        # Persistência Spring Data JPA
    │   │   ├── service/           # Regras de negócio
    │   │   ├── web/               # Formulários e objetos de entrada
    │   │   └── BibliotecaApplication.java
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/         # Páginas Thymeleaf
    │       └── static/            # Estilos CSS e scripts JavaScript
    └── test/                      # Testes automatizados e perfil H2
13. Solução de problemas
Problema	O que verificar
java não é reconhecido ou está em versão incorreta	Instale o JDK 21, ajuste JAVA_HOME/`PATH` e reabra o terminal.
docker não é reconhecido	Instale o Docker Desktop e reabra o terminal.
Docker não consegue iniciar contêineres	Verifique se o Docker Desktop está aberto e se o mecanismo está em execução.
MySQL não está healthy	Aguarde a inicialização; consulte docker compose logs mysql.
Porta 3306 em uso	Identifique outro MySQL local e ajuste a configuração de porta e conexão, se necessário.
Erro de conexão JDBC	Confira docker compose ps e se DB_URL, DB_USERNAME e DB_PASSWORD apontam para o banco correto.
Porta 8080 em uso	Encerre a aplicação anterior ou ajuste server.port em application.properties.
Falha ao baixar dependências	Verifique a conexão com a internet e tente novamente.
A página não abre	Confirme que o Spring Boot inicializou sem erros e acesse http://localhost:8080.
O catálogo demo não aparece	Verifique se já havia livros no banco; os dados de demonstração só são inseridos se a tabela de livros estiver vazia.
Login inicial não funciona	A conta demo só é criada quando não existe funcionário cadastrado; verifique os dados persistidos no MySQL.


14. Limitações e cuidados
- O projeto é um protótipo acadêmico, não um sistema pronto para implantação pública.
- O seletor de idioma não traduz integralmente todas as páginas; a leitura por voz depende do suporte do navegador e do sistema operacional.
- O requisito de tempo de resposta inferior a 10 segundos ainda precisa ser aferido em ambiente de teste.
- A configuração padrão pressupõe um MySQL local; para uso por múltiplas máquinas com o mesmo acervo, é necessário um banco acessível em rede e uma estratégia de implantação apropriada.
- O projeto não implementa automaticamente multas ou reservas de livros.
- Para uso real, são necessários maior rigor de validação, revisão de segurança e privacidade, política de senhas, gestão de segredos, backup, testes adicionais e configuração segura do banco.
- Inconsistência textual conhecida: o aviso da página inicial (templates/index.html) ainda menciona H2, mas a aplicação em execução utiliza MySQL; H2 é utilizado apenas nos testes. O texto da interface deve ser corrigido em uma revisão futura.
15. Documentação complementar e colaboração
- [`docs/EXECUCAO_LOCAL.md`](docs/EXECUCAO_LOCAL.md) — execução detalhada em outro computador.
- [`docs/MAPA_DE_REQUISITOS.md`](docs/MAPA_DE_REQUISITOS.md) — mapeamento dos requisitos implementados.
- [`docs/INTEGRACAO_DOS_MODULOS.md`](docs/INTEGRACAO_DOS_MODULOS.md) — divisão dos módulos e orientações de integração.
O histórico de contribuições deve permanecer disponível no repositório GitHub. Para conferir autores e commits:
git log --all --oneline --graph --decorate
git shortlog -sne --all
Um arquivo ZIP não contém necessariamente o histórico .git; para avaliar as contribuições, consulte o repositório remoto e seus commits.

Projeto acadêmico — Engenharia de Software.
