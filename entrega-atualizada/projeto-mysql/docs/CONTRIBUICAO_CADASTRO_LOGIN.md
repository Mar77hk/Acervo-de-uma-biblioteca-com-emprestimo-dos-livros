# Contribuição: cadastro, login e documentação

## Problema e mudança

O cadastro exigia CPF e CEP sem pontuação, e o login não normalizava CPF com máscara. Isso impedia a entrada por CPF formatado. No cadastro, a validação ocorria antes da remoção dos espaços externos do e-mail. Os links do README principal também apontavam para guias fora da pasta em que eles realmente estavam, e o roteiro indicava uma pasta diferente da estrutura atual.

A mudança aceita os formatos usuais de CPF e CEP, normaliza os campos antes da validação e permite login por CPF formatado. O login por e-mail com espaços externos já funcionava pelo tratamento do Spring Security e ganhou um teste de regressão. A senha não é alterada. Os campos HTML foram ampliados para comportar a pontuação. Foram acrescentados testes de cadastro, duplicidade, autenticação, histórico e permissões, além da correção dos caminhos da documentação.

BCrypt, os perfis de acesso e a configuração MySQL já existiam. A contribuição acrescenta cobertura de testes para esses comportamentos; ela não implementou originalmente essas funcionalidades.

## Como o fluxo funciona

1. O navegador envia os campos do formulário ao `UsuarioController`.
2. `UsuarioForm` normaliza os dados e o Bean Validation verifica os campos obrigatórios e seus formatos.
3. `UsuarioService` verifica duplicidade, codifica a senha com BCrypt, grava o usuário e registra a movimentação na mesma transação.
4. No login, `SecurityConfig` prepara o identificador e busca a conta por e-mail ou CPF. O Spring Security compara a senha com o hash armazenado.
5. As regras de acesso permitem operações administrativas apenas ao perfil `FUNCIONARIO`.

## Arquivos centrais para estudar

- `src/main/java/br/edu/ufscar/biblioteca/web/UsuarioForm.java`: tratamento dos campos antes da validação.
- `src/main/java/br/edu/ufscar/biblioteca/config/SecurityConfig.java`: identificação do usuário no login e regras de acesso.
- `src/main/resources/templates/usuarios/cadastro.html` e `usuarios/admin.html`: limites dos campos CPF e CEP.
- `src/test/java/br/edu/ufscar/biblioteca/UsuarioFluxoTests.java`: cenários automatizados.
- [Validação e limites](VALIDACAO_CADASTRO_LOGIN.md): testes, forma de execução e verificações ainda pendentes.

## Preparar a apresentação

Antes de apresentar, execute os testes, leia os arquivos alterados e faça a demonstração manual. Explique um exemplo concreto: o CPF `529.982.247-25` é recebido com pontuação, validado e armazenado como `52998224725`; o login aceita as duas representações. Explique também que testar H2 não comprova a persistência do MySQL após reiniciar o serviço.

As alterações e a documentação foram preparadas com auxílio do Codex. Registre apenas as atividades que você efetivamente revisar, executar e compreender, seguindo as regras da disciplina sobre uso de ferramentas. Este documento não afirma que a apresentação ou a verificação manual já ocorreram.

## Integração ao repositório do grupo

O ZIP baixado do GitHub não contém o histórico Git. Copie as alterações para um clone da branch `Entrega-Atualizada`, revise o diff e publique com sua própria conta. Não substitua o histórico existente do grupo nem edite o projeto antigo da raiz para aplicar estas mudanças.

Sugestões de mensagens para commits com conteúdo correspondente:

- `Corrige normalização de CPF, CEP e identificador de login`
- `Adiciona testes de cadastro, autenticação e permissões`
- `Corrige links e documenta execução e validação de usuários`

Depois da publicação, execute novamente os testes a partir da branch publicada. O pacote ZIP interno do repositório é anterior; para obter a versão atual, use o código em `entrega-atualizada/projeto-mysql`.
