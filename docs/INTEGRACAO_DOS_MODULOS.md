# Integração dos quatro módulos

## Divisão combinada

- **Eduardo — Acervo (RF01–RF04):** `domain/Autor.java`, `Editora.java`, `Livro.java`, `Exemplar.java`; `web/LivroForm.java`; repositories correspondentes; `LivroService`, `ExemplarService`; controladores de Livro/Autor/Editora/Exemplar; templates `livros/`, `autores/`, `editoras/`, `exemplares/`.
- **Melkzedekue — Pesquisa e histórico (RF05–RF06):** consulta por filtros em `LivroRepository`/`LivroController` (alterar em conjunto com Eduardo); `Movimentacao.java`, `MovimentacaoRepository`, `MovimentacaoService`, `HistoricoController` e `templates/historico/`.
- **Marcela — Empréstimos (RF07–RF08):** `Emprestimo.java`, `EmprestimoRepository`, `EmprestimoService`, `EmprestimoController`, `templates/emprestimos/`; alinhar mudança do estado de Exemplar com Eduardo.
- **Gabriela — Usuários (RF09–RF10):** `Usuario.java`, `TipoUsuario.java`, `UsuarioRepository`, `UsuarioService`, `UsuarioController`, `UsuarioForm`, `SecurityConfig`, `DataInitializer`, `templates/usuarios/` e `templates/auth/`.

## Regras para evitar conflitos

1. Todos devem trabalhar no mesmo repositório e no mesmo `pom.xml`.
2. Não criem outra `BibliotecaApplication.java` dentro dos módulos.
3. Não substituam `application.properties`, `fragments.html` ou `SecurityConfig.java` sem combinar.
4. Preservem os nomes dos campos/relacionamentos das entidades compartilhadas (`Livro`, `Exemplar`, `Usuario`, `Emprestimo`).
5. Registrem eventos pelo contrato comum `MovimentacaoService.registrar(tipo, descricao, responsavel)`.
6. Façam commits pequenos e testem antes de enviar pull request.

## Branches sugeridas

- `feature/acervo-eduardo`
- `feature/historico-melk`
- `feature/emprestimos-marcela`
- `feature/usuarios-gabriela`

No GitHub, cada integrante cria sua branch, envia commits e abre Pull Request para `main`. Não copiem quatro projetos Spring Boot independentes por cima do projeto principal.
