# Mapa dos requisitos do documento do grupo

| Requisito | Implementação no protótipo |
|---|---|
| RF01 — Cadastro de livros | `Livro`, `LivroForm`, `LivroController`, `LivroService` e `livros/form.html`; título, volume, editora, ISBN e autores. |
| RF02 — Cadastro de autores | `Autor`, `AutorController` e `autores/`; nome e data de nascimento. |
| RF03 — Cadastro de editoras | `Editora`, `EditoraController` e `editoras/`; razão social, CNPJ, endereço e cidade. |
| RF04 — Quantidade de exemplares físicos | `LivroService` cria registros individuais `Exemplar`, com código patrimonial. |
| RF05 — Pesquisa por título, autor, editora e ISBN | `/livros`; quatro filtros opcionais e consulta JPA em `LivroRepository`. |
| RF06 — Histórico | `Movimentacao`, `MovimentacaoService` e `HistoricoController`; cadastros/entradas, empréstimos, devoluções, manutenções e descartes. |
| RF07 — Empréstimo e prazo | `/emprestimos`; funcionário escolhe exemplar, usuário e número de dias (1 a 90). |
| RF08 — Devolução, avaria e atraso | Devolução registra avaria; atraso é verificado comparando data de devolução e prazo. Avaria coloca exemplar em manutenção. |
| RF09 — Cadastro de usuário comum | `/cadastro`, acessível publicamente; funcionários também podem usar a tela. |
| RF10 — Cadastro de funcionário | `/admin/usuarios`, restrito a funcionário autenticado. |
| RNF01 — Navegador | Spring MVC/Thymeleaf via HTTP. |
| RNF02 — Java | Java 21. |
| RNF03 — Banco relacional | H2 SQL em arquivo, com JPA/Hibernate. |
| RNF04 — Interface responsiva e até 10 s | CSS responsivo; tempo de resposta não foi medido e precisa de teste real. |
| RNF05 — Persistência | Arquivo de banco H2 salvo em `data/`. |
| RNF06 — Acessibilidade | Ajuste de fonte, modo escuro, alto contraste, leitura pelo navegador e seletor de idioma. A tradução ainda não cobre todos os textos. |

## Decisões assumidas

O documento não define prazo padrão, multas, reservas nem bloqueio de usuário com empréstimo vencido. Para este protótipo, o funcionário escolhe prazo entre 1 e 90 dias; não há cálculo de multa. Validem essas escolhas com o professor/grupo.
