# Sistema Web de Gerenciamento de Biblioteca — Grupo 1

Este repositório contém os arquivos do projeto acadêmico de gerenciamento de biblioteca e histórico do acervo.

## Versão corrigida para avaliação

**Para consultar a entrega atualizada, acesse a pasta [`entrega-atualizada/`](entrega-atualizada/).** Ela reúne a publicação complementar com o projeto atualizado, o pacote de código-fonte e o guia de instalação e execução.

Dentro de `entrega-atualizada/` estão disponíveis:

- [`README.md`](entrega-atualizada/README.md): apresentação da entrega e instruções resumidas.
- [`Projeto_Biblioteca_MySQL_Atualizado_COM_GUIA.zip`](entrega-atualizada/Projeto_Biblioteca_MySQL_Atualizado_COM_GUIA.zip): código-fonte completo atualizado e configuração do banco.
- [`Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.pdf`](entrega-atualizada/Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.pdf): guia passo a passo para preparar o ambiente e executar a aplicação.
- [`Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.docx`](entrega-atualizada/Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.docx): versão editável do guia.
- [`Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.md`](entrega-atualizada/Guia_Completo_Instalacao_Execucao_e_Publicacao_Biblioteca.md): versão em Markdown do guia.

## Principais atualizações da entrega individual

- Configuração do **MySQL 8.4** como banco de dados principal da aplicação, substituindo o H2 na execução normal.
- Inclusão de `compose.yaml` para inicialização do MySQL com Docker Compose.
- Manutenção do H2 apenas para os testes automatizados, em memória.
- Revisão da configuração de autenticação com Spring Security e codificação de senhas com BCrypt.
- Inclusão de um guia de instalação e execução local para Windows, com Java 21, WSL 2, Docker Desktop, Visual Studio Code, Maven Wrapper e inicialização do banco.

## Como executar a versão atualizada

1. Acesse a pasta `entrega-atualizada/` e baixe o arquivo `Projeto_Biblioteca_MySQL_Atualizado_COM_GUIA.zip`.
2. Extraia o ZIP.
3. No VS Code, abra a pasta do projeto que contém `pom.xml`, `mvnw.cmd` e `compose.yaml` (dentro da estrutura extraída).
4. Com JDK 21, WSL 2 e Docker Desktop instalados e iniciados, abra um terminal nessa pasta e execute:

```powershell
docker compose up -d mysql
docker compose ps
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

5. Aguarde o serviço MySQL apresentar estado `healthy` e acesse `http://localhost:8080`.

O guia em PDF, disponível em `entrega-atualizada/`, contém o procedimento detalhado e orientações para resolver problemas comuns de instalação e conexão.

## Observação sobre as versões e o histórico

Os arquivos que já estavam na raiz do repositório foram preservados. Para a avaliação da versão corrigida e da documentação complementar, consulte a pasta `entrega-atualizada/` indicada acima. O histórico de commits registra as publicações realizadas no GitHub, porém a análise da participação individual no desenvolvimento deve considerar também as atividades efetivamente realizadas e os demais registros disponíveis.
