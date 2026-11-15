package br.edu.ufscar.biblioteca;

import br.edu.ufscar.biblioteca.domain.TipoUsuario;
import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class BibliotecaApplicationTests {

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextoInicia() {
        // A inicializacao do contexto confirma que os componentes Spring foram carregados.
    }

    @Test
    void senhaDoFuncionarioInicialEhArmazenadaComHashBcrypt() {
        var funcionario = usuarios.findByEmailIgnoreCaseOrCpf(
            "admin@biblioteca.local", "00000000000"
        ).orElseThrow();

        assertNotNull(funcionario.getSenha());
        assertNotEquals("Biblioteca123!", funcionario.getSenha());
        assertTrue(funcionario.getSenha().startsWith("$2"));
        assertTrue(passwordEncoder.matches("Biblioteca123!", funcionario.getSenha()));
        assertTrue(funcionario.getTipoUsuario() == TipoUsuario.FUNCIONARIO);
    }
}
