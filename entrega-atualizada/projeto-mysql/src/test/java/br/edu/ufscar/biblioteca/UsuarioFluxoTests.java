package br.edu.ufscar.biblioteca;

import br.edu.ufscar.biblioteca.domain.TipoUsuario;
import br.edu.ufscar.biblioteca.repository.MovimentacaoRepository;
import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import br.edu.ufscar.biblioteca.service.UsuarioService;
import br.edu.ufscar.biblioteca.web.UsuarioForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Integra MVC, validacao, seguranca, servicos e persistencia real no H2 de testes. */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioFluxoTests {
    private static final String EMAIL = "leitor@example.com";
    private static final String CPF = "52998224725";
    private static final String SENHA = "SenhaDeTeste123!";

    @Autowired private MockMvc mvc;
    @Autowired private UsuarioRepository usuarios;
    @Autowired private MovimentacaoRepository movimentacoes;
    @Autowired private UsuarioService service;
    @Autowired private PasswordEncoder encoder;

    @Test
    void cadastroSalvaUsuarioComumComHashEHistorico() throws Exception {
        long total = usuarios.count();
        long historico = movimentacoes.count();

        mvc.perform(cadastro("/cadastro"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"))
            .andExpect(flash().attributeExists("sucesso"));

        var salvo = usuarios.findByEmailIgnoreCaseOrCpf(EMAIL, CPF).orElseThrow();
        assertThat(usuarios.count()).isEqualTo(total + 1);
        assertThat(salvo.getTipoUsuario()).isEqualTo(TipoUsuario.COMUM);
        assertThat(salvo.getSenha()).isNotEqualTo(SENHA).startsWith("$2");
        assertThat(encoder.matches(SENHA, salvo.getSenha())).isTrue();
        assertThat(movimentacoes.count()).isEqualTo(historico + 1);
        assertThat(movimentacoes.findByTipoOrderByDataHoraDesc("USUARIO_CADASTRADO"))
            .anySatisfy(m -> {
                assertThat(m.getDescricao()).contains("Leitor de Teste", "COMUM");
                assertThat(m.getResponsavel()).isEqualTo("Auto cadastro");
            });
    }

    @Test
    void cadastroAceitaCpfCepFormatadosEEspacosNasBordas() throws Exception {
        var request = cadastro("/cadastro", Map.of(
            "nome", "  Leitor de Teste  ", "email", "  LEITOR@EXAMPLE.COM  ",
            "cpf", " 529.982.247-25 ", "cep", " 13560-000 "));

        mvc.perform(request).andExpect(redirectedUrl("/login"));

        var salvo = usuarios.findByEmailIgnoreCaseOrCpf(EMAIL, CPF).orElseThrow();
        assertThat(salvo.getNome()).isEqualTo("Leitor de Teste");
        assertThat(salvo.getEmail()).isEqualTo(EMAIL);
        assertThat(salvo.getCpf()).isEqualTo(CPF);
        assertThat(salvo.getCep()).isEqualTo("13560000");
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "cpf"})
    void cadastroRecusaDuplicidadeSemSalvarUsuarioOuHistorico(String campo) throws Exception {
        criarLeitor();
        long total = usuarios.count();
        long historico = movimentacoes.count();
        var request = cadastro("/cadastro", Map.of(
            "email", campo.equals("email") ? "LEITOR@EXAMPLE.COM" : "outro@example.com",
            "cpf", campo.equals("cpf") ? CPF : "11144477735"));

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(view().name("usuarios/cadastro"))
            .andExpect(model().attribute("erro", campo.equals("email")
                ? "Este e-mail já está cadastrado." : "Este CPF já está cadastrado."));

        assertThat(usuarios.count()).isEqualTo(total);
        assertThat(movimentacoes.count()).isEqualTo(historico);
    }

    @Test
    void cadastroRecusaCamposObrigatoriosVazios() throws Exception {
        long total = usuarios.count();
        mvc.perform(post("/cadastro").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("form", "nome", "email", "cpf", "cep", "senha"));
        assertThat(usuarios.count()).isEqualTo(total);
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "cpf", "cep", "senha"})
    void cadastroRecusaValoresInvalidos(String campo) throws Exception {
        long total = usuarios.count();
        var request = cadastro("/cadastro", Map.of(campo, switch (campo) {
            case "email" -> "email-invalido";
            case "cpf" -> "abc52998224725";
            case "cep" -> "abc13560000";
            default -> "1234567";
        }));
        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("form", campo));
        assertThat(usuarios.count()).isEqualTo(total);
    }

    @Test
    void cadastroPublicoNaoPermiteEscolherPerfilDeFuncionario() throws Exception {
        mvc.perform(cadastro("/cadastro").param("tipoUsuario", "FUNCIONARIO"))
            .andExpect(redirectedUrl("/login"));
        assertThat(usuarios.findByEmailIgnoreCaseOrCpf(EMAIL, CPF).orElseThrow().getTipoUsuario())
            .isEqualTo(TipoUsuario.COMUM);
    }

    @ParameterizedTest
    @ValueSource(strings = {"leitor@example.com", "LEITOR@EXAMPLE.COM", "  LEITOR@EXAMPLE.COM  ",
        "52998224725", "529.982.247-25", " 529.982.247-25 "})
    void loginAceitaEmailOuCpf(String identificador) throws Exception {
        criarLeitor();
        mvc.perform(post("/login").with(csrf()).param("identificador", identificador).param("senha", SENHA))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andExpect(authenticated().withUsername(EMAIL).withRoles("COMUM"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"senha-incorreta", " SenhaDeTeste123! "})
    void loginRecusaSenhaIncorretaSemRemoverEspacosDaSenha(String senha) throws Exception {
        criarLeitor();
        mvc.perform(post("/login").with(csrf()).param("identificador", EMAIL).param("senha", senha))
            .andExpect(redirectedUrl("/login?erro=true"))
            .andExpect(unauthenticated());
    }

    @Test
    void loginNaoTransformaLetrasEmCpfValido() throws Exception {
        criarLeitor();
        mvc.perform(post("/login").with(csrf()).param("identificador", "abc" + CPF).param("senha", SENHA))
            .andExpect(redirectedUrl("/login?erro=true"))
            .andExpect(unauthenticated());
    }

    @Test
    void visitantePrecisaEntrarParaAcessarAdministracao() throws Exception {
        mvc.perform(get("/admin/usuarios"))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", endsWith("/login")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/admin/usuarios", "/admin/livros/novo", "/emprestimos", "/historico"})
    void usuarioComumNaoAcessaAdministracao(String rota) throws Exception {
        mvc.perform(get(rota).with(user(EMAIL).roles("COMUM")))
            .andExpect(status().isForbidden());
    }

    @Test
    void usuarioComumNaoCadastraFuncionario() throws Exception {
        long total = usuarios.count();
        mvc.perform(cadastro("/admin/usuarios/funcionario").with(user(EMAIL).roles("COMUM")))
            .andExpect(status().isForbidden());
        assertThat(usuarios.count()).isEqualTo(total);
    }

    @Test
    void funcionarioAcessaAdministracaoECadastraFuncionario() throws Exception {
        mvc.perform(get("/admin/usuarios").with(user("admin@biblioteca.local").roles("FUNCIONARIO")))
            .andExpect(status().isOk()).andExpect(view().name("usuarios/admin"));
        mvc.perform(cadastro("/admin/usuarios/funcionario")
                .with(user("admin@biblioteca.local").roles("FUNCIONARIO")))
            .andExpect(redirectedUrl("/admin/usuarios"));
        assertThat(usuarios.findByEmailIgnoreCaseOrCpf(EMAIL, CPF).orElseThrow().getTipoUsuario())
            .isEqualTo(TipoUsuario.FUNCIONARIO);
    }

    @Test
    void cadastroSemTokenCsrfEhRecusado() throws Exception {
        long total = usuarios.count();
        mvc.perform(post("/cadastro")).andExpect(status().isForbidden());
        assertThat(usuarios.count()).isEqualTo(total);
    }

    @Test
    void formularioPermiteDigitarDocumentosComPontuacao() throws Exception {
        mvc.perform(get("/cadastro"))
            .andExpect(status().isOk())
            .andExpect(content().string(matchesPattern("(?s).*<input(?=[^>]*name=\"cpf\")(?=[^>]*maxlength=\"14\")[^>]*>.*")))
            .andExpect(content().string(matchesPattern("(?s).*<input(?=[^>]*name=\"cep\")(?=[^>]*maxlength=\"9\")[^>]*>.*")));
    }

    private MockHttpServletRequestBuilder cadastro(String rota) {
        return cadastro(rota, Map.of());
    }

    private MockHttpServletRequestBuilder cadastro(String rota, Map<String, String> alteracoes) {
        var campos = new LinkedHashMap<>(Map.of("nome", "Leitor de Teste", "email", EMAIL,
            "cpf", CPF, "cep", "13560000", "senha", SENHA));
        campos.putAll(alteracoes);
        var request = post(rota).with(csrf());
        campos.forEach((nome, valor) -> request.param(nome, valor));
        return request;
    }

    private void criarLeitor() {
        var form = new UsuarioForm();
        form.setNome("Leitor de Teste"); form.setEmail(EMAIL); form.setCpf(CPF);
        form.setCep("13560000"); form.setSenha(SENHA);
        service.cadastrar(form, TipoUsuario.COMUM, "Teste automatizado");
    }
}
