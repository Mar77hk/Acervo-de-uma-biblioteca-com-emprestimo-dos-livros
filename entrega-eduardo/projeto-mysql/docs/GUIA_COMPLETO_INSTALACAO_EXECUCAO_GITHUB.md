# Guia completo de instalação, execução e publicação

## Sistema Web de Gerenciamento de Biblioteca e Histórico do Acervo

**Versão documentada:** MySQL 8.4 · Java 21 · Spring Boot 3.5.16 · Windows 11  
**Data do registro:** 15 de novembro de 2026

Este documento reúne os procedimentos para preparar um computador Windows, instalar as ferramentas necessárias, iniciar o banco MySQL e executar a aplicação localmente. Também descreve uma forma transparente de publicar esta versão no repositório do grupo sem apagar o histórico já existente.

> **Importante:** esta versão usa MySQL 8.4 para a execução normal da aplicação. O H2 permanece apenas nos testes automatizados, em memória. A versão anterior do projeto que está na raiz histórica do repositório pode ainda mencionar H2; confira a pasta `entrega-eduardo/projeto-mysql` para esta versão atualizada.

## 1. Visão geral da aplicação

A aplicação foi desenvolvida com Java 21, Spring Boot 3.5.16, Spring MVC, Thymeleaf, Spring Data JPA e Spring Security. O banco de dados usado pela aplicação é MySQL 8.4, iniciado localmente por Docker Compose.

O fluxo de execução possui dois serviços que precisam estar ativos:

1. **MySQL:** armazena livros, autores, editoras, exemplares, usuários, empréstimos e movimentações. O container publica a porta `3306`.
2. **Spring Boot:** executa o servidor web e disponibiliza o site na porta `8080`.

O Docker Desktop não substitui o Java ou o Maven. Ele disponibiliza o ambiente de contêiner para executar o MySQL. O Maven Wrapper (`mvnw.cmd`) baixa/usa a versão de Maven configurada pelo projeto e as dependências necessárias.

## 2. Requisitos do computador

- Windows 11 de 64 bits (Windows 10 compatível com os requisitos de WSL 2 e Docker Desktop também pode ser usado).
- JDK 21 (não apenas JRE).
- Visual Studio Code.
- Docker Desktop para Windows, com backend WSL 2.
- Windows Subsystem for Linux (WSL 2) habilitado.
- Conexão com a internet durante a primeira execução, para baixar o Maven, bibliotecas Java e a imagem MySQL.

## 3. Instalar e verificar o Java 21

1. Abra a página oficial do Eclipse Temurin e selecione **JDK 21**, **Windows**, arquitetura **x64** e instalador **MSI**. Também é possível usar outra distribuição de JDK 21 compatível.
2. Execute o instalador e conclua as etapas padrão. Quando disponível, habilite as opções para adicionar o Java ao `PATH` e configurar `JAVA_HOME`.
3. Feche os terminais antigos e abra um novo PowerShell.
4. Execute:

```powershell
java -version
javac -version
```

Os dois comandos devem indicar a versão 21. O comando `javac` ajuda a confirmar que foi instalado um JDK, e não somente um ambiente de execução Java.

Se `java` ou `javac` não forem reconhecidos, confira a instalação e as variáveis de ambiente `JAVA_HOME` e `Path`. Depois de alterá-las, abra um novo terminal.

## 4. Instalar e preparar o WSL 2

O Docker Desktop pode usar o WSL 2 como backend para executar contêineres Linux no Windows.

1. Abra o PowerShell **como administrador**.
2. Execute:

```powershell
wsl --install
```

3. Se o Windows solicitar, reinicie o computador para concluir a habilitação dos componentes.
4. Depois da reinicialização, abra o PowerShell e confira:

```powershell
wsl --version
wsl --status
```

5. Para atualizar o WSL, quando necessário, execute:

```powershell
wsl --update
```

Se o WSL já estiver instalado, o comando `wsl --install` pode informar que não há nada novo a instalar. Nesse caso, confira a versão e use `wsl --update`.

## 5. Instalar o Docker Desktop

1. Baixe o Docker Desktop exclusivamente pelo site oficial indicado em **Referências oficiais**.
2. Execute `Docker Desktop Installer.exe`.
3. Na seleção do backend, escolha **Use WSL 2 instead of Hyper-V** (ou a opção equivalente de WSL 2).
4. O instalador oferece instalação por usuário ou para todos os usuários. No computador usado para esta configuração, foi concluída a instalação **All users**. Essa modalidade solicita privilégios de administrador; ela não é um requisito do projeto, pois o Docker Desktop também oferece instalação por usuário.
5. Conclua a instalação e abra o **Docker Desktop** pelo menu Iniciar. Aguarde o mecanismo ficar em execução.
6. Abra um novo terminal do PowerShell ou do VS Code e verifique:

```powershell
docker --version
docker compose version
```

Os dois comandos devem retornar versões. Se `docker` não for reconhecido, abra o Docker Desktop, feche e reabra o terminal e tente novamente. Se o Windows/instalador tiver acabado de habilitar o WSL, reinicie o computador antes de repetir a verificação.

## 6. Preparar o Visual Studio Code

1. Instale o Visual Studio Code, caso ainda não esteja instalado.
2. Abra o VS Code e pressione `Ctrl + Shift + X` para abrir **Extensions**.
3. Instale:
   - **Extension Pack for Java** (Microsoft).
   - **Spring Boot Extension Pack**.
4. Aguarde a instalação e reinicie/recarregue o VS Code se solicitado.

As extensões auxiliam na edição, navegação, execução e depuração do projeto. Elas não substituem o JDK 21 nem o Docker Desktop.

## 7. Extrair e abrir a pasta correta do projeto

1. Extraia `Projeto_Biblioteca_MySQL_Atualizado.zip` para uma pasta de fácil acesso.
2. No VS Code, escolha **File → Open Folder**.
3. Abra a pasta `biblioteca-projeto-completo`, que contém diretamente os arquivos `pom.xml`, `mvnw.cmd` e `compose.yaml`.
4. Não abra apenas `src` e não execute os comandos de outra pasta.
5. No terminal integrado (**Terminal → New Terminal**), confirme que o caminho atual é a pasta que contém `pom.xml` e `compose.yaml`.

## 8. Iniciar o banco de dados MySQL

Com o Docker Desktop aberto e em execução, execute no terminal integrado, dentro da pasta do projeto:

```powershell
docker compose up -d mysql
```

Na primeira execução, o Docker baixa a imagem `mysql:8.4`. É necessário ter conexão com a internet. Em seguida, o Compose cria o banco `biblioteca`, o usuário local da aplicação e o volume persistente `biblioteca_mysql_data`.

Confira o estado do serviço:

```powershell
docker compose ps
```

Aguarde o serviço `mysql` apresentar o estado **healthy**. Se aparecer `starting`, aguarde alguns segundos e repita o comando. Para acompanhar a inicialização:

```powershell
docker compose logs -f mysql
```

Pressione `Ctrl + C` para sair da visualização dos logs; isso não encerra o container.

## 9. Executar os testes automatizados

Ainda na pasta que contém `pom.xml`, execute:

```powershell
.\mvnw.cmd clean test
```

O Maven Wrapper baixa a distribuição Maven configurada pelo projeto e as dependências Java na primeira execução. Os testes usam H2 em memória e não precisam do MySQL ativo. A aplicação em execução normal, por outro lado, utiliza MySQL.

## 10. Iniciar a aplicação web

Com o MySQL em estado `healthy`, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Aguarde no terminal mensagens indicando que o Tomcat iniciou na porta `8080` e que `BibliotecaApplication` foi iniciada. Depois, abra o navegador em:

```text
http://localhost:8080
```

Mantenha o terminal aberto enquanto estiver usando o sistema. Para encerrar o Spring Boot, volte ao terminal e pressione `Ctrl + C`.

## 11. Primeiro acesso

Se ainda não houver funcionário cadastrado no banco, o sistema cria uma conta local de demonstração:

| Campo | Valor |
|---|---|
| E-mail | `admin@biblioteca.local` |
| CPF | `00000000000` |
| Senha | `Biblioteca123!` |

Na tela **Entrar**, use o e-mail ou o CPF no campo de identificação. A senha é armazenada com hash BCrypt, não em texto puro. Essas credenciais são exclusivamente para demonstração local; altere-as antes de qualquer implantação pública.

## 12. Persistência e encerramento

Os cadastros ficam no volume Docker `biblioteca_mysql_data`. Para parar os contêineres sem apagar os dados:

```powershell
docker compose down
```

Para iniciar novamente em outra ocasião:

```powershell
docker compose up -d mysql
```

**Não execute `docker compose down -v` se quiser preservar os cadastros.** A opção `-v` remove o volume e apaga os dados persistidos pelo contêiner.

## 13. Teste funcional recomendado

1. Entre como funcionário.
2. Cadastre um autor e uma editora.
3. Cadastre um livro e gere mais de um exemplar.
4. Pesquise o livro por título, autor, editora e ISBN.
5. Cadastre um usuário comum.
6. Encerre a sessão e entre como usuário comum; confira que ele pode pesquisar, mas não acessar as operações administrativas.
7. Volte ao perfil de funcionário e registre um empréstimo.
8. Registre a devolução, testando os casos com e sem avaria.
9. Consulte o histórico das movimentações.
10. Teste os recursos de acessibilidade disponíveis no navegador.

## 14. Solução de problemas comuns

| Mensagem/situação | Causa provável e solução |
|---|---|
| `docker` não é reconhecido | Docker Desktop não foi instalado corretamente, não foi iniciado ou o terminal é antigo. Abra o Docker Desktop, reabra o PowerShell e execute `docker --version`. Reinicie o Windows se o WSL acabou de ser habilitado. |
| `Cannot connect to the Docker daemon` | O Docker Desktop ainda não terminou de iniciar. Abra-o e aguarde o mecanismo ficar pronto. |
| `Communications link failure` / `Connection refused` | O MySQL não está ativo ou ainda está inicializando. Execute `docker compose up -d mysql`, depois `docker compose ps` e aguarde `healthy`. |
| MySQL aparece como `starting` | Aguarde a inicialização e acompanhe `docker compose logs -f mysql`. |
| Porta `3306` ocupada | Outro serviço MySQL já está usando a porta. Pare o serviço conflitante ou ajuste a porta publicada e a URL JDBC de forma consistente. |
| Porta `8080` ocupada | Outra aplicação está usando a porta do Spring Boot. Encerre a outra execução com `Ctrl + C` ou libere a porta. |
| `java`/`javac` não reconhecido | Verifique o JDK 21 e as variáveis `JAVA_HOME` e `Path`; abra um novo terminal. |
| Maven não baixa dependências | Verifique a conexão com a internet, proxy ou firewall e repita o comando Maven. |

## 15. Publicar a versão atualizada no GitHub sem apagar o histórico

### 15.1. Situação do repositório

Na consulta realizada em 15/11/2026, o repositório público do Grupo 1 apresentava uma branch (`main`) e cinco commits. A página principal ainda descrevia a versão com H2. Para evitar apagar o histórico e para distinguir a versão atualizada, recomenda-se adicionar uma pasta própria, por exemplo `entrega-eduardo/`, contendo o projeto MySQL e o guia.

**Não apague os commits existentes nem tente atribuir retroativamente commits de outra pessoa à sua conta.** O histórico deve permanecer íntegro. Os novos commits registrarão as alterações efetivamente enviadas pela conta autenticada.

### 15.2. Configurar a identificação de autoria

Antes de realizar commits pela linha de comando, configure o nome e um e-mail associado à sua conta GitHub. Use um e-mail verificado ou o endereço `noreply` exibido nas configurações da conta:

```powershell
git config --global user.name "Seu nome de exibição"
git config --global user.email "SEU_EMAIL_VERIFICADO_OU_NOREPLY"
git config --global user.name
git config --global user.email
```

O GitHub relaciona commits à conta pelo e-mail gravado no commit. Alterar essa configuração vale para commits futuros; não muda a autoria dos commits antigos.

### 15.3. Criar uma branch de entrega

O repositório pertence à conta que o criou. Para enviar uma branch, sua conta precisa ter permissão de escrita. Se não tiver, solicite acesso como colaborador ou faça um fork e envie a branch ao fork.

Com Git instalado, abra o PowerShell em uma pasta onde deseja manter a cópia local e execute:

```powershell
git clone https://github.com/Mar77hk/Acervo-de-uma-biblioteca-com-emprestimo-dos-livros.git
cd Acervo-de-uma-biblioteca-com-emprestimo-dos-livros
git switch -c entrega-eduardo
```

Copie a pasta do projeto MySQL atualizado para dentro do repositório clonado, com uma estrutura semelhante a:

```text
entrega-eduardo/
  README.md
  projeto-mysql/
    pom.xml
    compose.yaml
    mvnw.cmd
    src/
    docs/
  documentacao/
    Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.pdf
    Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.docx
```

Depois, revise os arquivos e envie:

```powershell
git status
git add entrega-eduardo
git commit -m "Adiciona versão MySQL e guia de execução - Eduardo"
git push -u origin entrega-eduardo
```

A branch poderá ser vista em `https://github.com/Mar77hk/Acervo-de-uma-biblioteca-com-emprestimo-dos-livros/tree/entrega-eduardo`.

### 15.4. Manter o link do AVA apontando para `main`

Se o link do AVA aponta para `/tree/main`, os arquivos enviados apenas à branch `entrega-eduardo` não aparecem na visualização `main`. Para manter o link do AVA inalterado, abra o repositório no GitHub, crie um Pull Request de `entrega-eduardo` para `main` e, depois da revisão/aprovação necessária, faça o merge. Após o merge, a pasta também aparecerá em `main` e o link original continuará funcionando.

Se não for possível fazer merge por falta de permissão, mantenha a branch e envie ao professor o link direto dela ou solicite que a pessoa proprietária do repositório aprove o Pull Request.

### 15.5. Conferir os commits

Depois do envio, confira se os novos commits aparecem vinculados à sua conta na página **Commits** do GitHub. Se estiverem associados a outra conta ou sem associação, confira se o e-mail usado no commit está adicionado e verificado nas configurações do GitHub.

Comandos úteis:

```powershell
git log --oneline --decorate --all
git shortlog -sne --all
```

O ZIP do projeto não carrega a pasta `.git`; por isso, ele não contém o histórico de commits. A comprovação de participação deve ser feita pelo repositório remoto.

## 16. Lista final de conferência

- [ ] JDK 21 instalado e `java -version` funcionando.
- [ ] WSL 2 instalado/atualizado.
- [ ] Docker Desktop instalado, aberto e em execução.
- [ ] `docker --version` e `docker compose version` funcionando.
- [ ] VS Code e extensões Java/Spring instalados.
- [ ] Pasta correta do projeto aberta (contém `pom.xml` e `compose.yaml`).
- [ ] MySQL iniciado e em estado `healthy`.
- [ ] `mvnw.cmd clean test` executado.
- [ ] Spring Boot iniciado e site acessível em `http://localhost:8080`.
- [ ] Login, cadastro, empréstimo, devolução e histórico testados.
- [ ] Versão e guia enviados ao GitHub com commits associados à conta correta.

## Referências oficiais

- Eclipse Temurin JDK 21: https://adoptium.net/temurin/releases/?version=21
- Instalar WSL: https://learn.microsoft.com/pt-br/windows/wsl/install
- Instalar Docker Desktop no Windows: https://docs.docker.com/desktop/setup/install/windows-install/
- Backend WSL 2 do Docker Desktop: https://docs.docker.com/desktop/features/wsl/
- Java no Visual Studio Code: https://code.visualstudio.com/docs/languages/java
- Adicionar arquivos ao GitHub: https://docs.github.com/en/repositories/working-with-files/managing-files/adding-a-file-to-a-repository
- Configurar e-mail de commits: https://docs.github.com/en/account-and-profile/how-tos/email-preferences/setting-your-commit-email-address
