package br.edu.ufscar.biblioteca.config;

import br.edu.ufscar.biblioteca.domain.TipoUsuario;
import br.edu.ufscar.biblioteca.domain.Usuario;
import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean CommandLineRunner criarFuncionarioInicial(UsuarioRepository usuarios, PasswordEncoder encoder) {
        return args -> {
            if (usuarios.countByTipoUsuario(TipoUsuario.FUNCIONARIO) == 0) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador da Biblioteca");
                admin.setEmail("admin@biblioteca.local");
                admin.setCpf("00000000000");
                admin.setCep("13560000");
                admin.setSenha(encoder.encode("Biblioteca123!"));
                admin.setTipoUsuario(TipoUsuario.FUNCIONARIO);
                usuarios.save(admin);
            }
        };
    }
}
