# Sistema Web de Gerenciamento de Biblioteca — Grupo 1

Este repositório contém os arquivos do projeto acadêmico de gerenciamento de biblioteca e histórico do acervo.

## Versão corrigida para avaliação

**Para consultar a entrega atualizada, acesse a pasta [`entrega-atualizada/`](entrega-atualizada/).** Ela reúne a publicação complementar com o projeto atualizado, o pacote de código-fonte e o guia de instalação e execução.

Dentro de `entrega-atualizada/` estão disponíveis:

- [`README.md`](entrega-atualizada/README.md): apresentação da entrega e instruções resumidas.
- [`Projeto_Biblioteca_MySQL_Atualizado_COM_GUIA.zip`](entrega-atualizada/Projeto_Biblioteca_MySQL_Atualizado_COM_GUIA.zip): código-fonte completo atualizado e configuração do banco.
- [`projeto-mysql/`](entrega-atualizada/projeto-mysql/): código-fonte da versão atual. Use esta pasta para desenvolver e executar após baixar ou clonar a branch.
- [`Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.pdf`](entrega-atualizada/documentacao/Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.pdf): guia em PDF.
- [`Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.docx`](entrega-atualizada/documentacao/Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.docx): versão editável do guia.
- [`Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.md`](entrega-atualizada/documentacao/Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.md): versão em Markdown do guia.
- [`Roteiro de execução local`](entrega-atualizada/projeto-mysql/docs/EXECUCAO_LOCAL.md): instruções para a estrutura atual do repositório.

## Principais atualizações da entrega individual

- Configuração do **MySQL 8.4** como banco de dados principal da aplicação, substituindo o H2 na execução normal.
- Inclusão de `compose.yaml` para inicialização do MySQL com Docker Compose.
- Manutenção do H2 apenas para os testes automatizados, em memória.
- Revisão da configuração de autenticação com Spring Security e codificação de senhas com BCrypt.
- Inclusão de um guia de instalação e execução local para Windows, com Java 21, WSL 2, Docker Desktop, Visual Studio Code, Maven Wrapper e inicialização do banco.

## Como executar a versão atualizada

1. Selecione a branch `Entrega-Atualizada` no GitHub e use **Code → Download ZIP**, ou clone essa branch.
2. Extraia o ZIP da branch e entre na pasta extraída.
3. No VS Code, abra `entrega-atualizada/projeto-mysql`, que contém `pom.xml`, `mvnw.cmd` e `compose.yaml`. O projeto da raiz é a versão anterior. O ZIP interno é um pacote anterior e pode não conter as alterações mais recentes; prefira os arquivos desta pasta.
4. Com JDK 21, WSL 2 e Docker Desktop instalados e iniciados, abra um terminal nessa pasta e execute:

```powershell
docker compose up -d mysql
docker compose ps
```

5. Aguarde o serviço MySQL apresentar estado `healthy`.
6. Execute os testes e, se passarem, inicie a aplicação:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

7. Quando a aplicação iniciar, acesse `http://localhost:8080`.

O guia em PDF está em `entrega-atualizada/documentacao/`. Para os caminhos atuais, consulte o [roteiro de execução local](entrega-atualizada/projeto-mysql/docs/EXECUCAO_LOCAL.md). Os testes automatizados usam H2 em memória e podem ser executados somente com JDK 21 e Maven Wrapper, sem iniciar Docker ou MySQL.

## Observação sobre as versões e o histórico

Os arquivos que já estavam na raiz do repositório foram preservados. Para a avaliação da versão corrigida e da documentação complementar, consulte a pasta `entrega-atualizada/` indicada acima. O histórico de commits registra as publicações realizadas no GitHub, porém a análise da participação individual no desenvolvimento deve considerar também as atividades efetivamente realizadas e os demais registros disponíveis.

## Melhorias no cadastro e validação de usuários

Contribuição publicada por [Melk-Moreira](https://github.com/Melk-Moreira).

- Cadastro aceita CPF e CEP com a pontuação usual.
- Login aceita CPF formatado.
- Formulários ajustados para comportar os documentos formatados.
- Inclusão de 28 testes de cadastro, login, duplicidade e permissões.
- Correção dos links e das instruções de execução.

Validação local: 30 testes passaram, incluindo os 2 testes preexistentes.
Os testes utilizam H2; a validação com MySQL permanece pendente.

Consulte a [explicação das alterações](entrega-atualizada/projeto-mysql/docs/CONTRIBUICAO_CADASTRO_LOGIN.md)
e os [resultados da validação](entrega-atualizada/projeto-mysql/docs/VALIDACAO_CADASTRO_LOGIN.md).
