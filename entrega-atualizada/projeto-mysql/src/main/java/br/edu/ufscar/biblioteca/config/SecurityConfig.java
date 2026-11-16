package br.edu.ufscar.biblioteca.config;

import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarios) {
        return identificador -> {
            String valor = identificador.trim();
            String cpf = valor.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}")
                ? valor.replace(".", "").replace("-", "") : valor;
            return usuarios.findByEmailIgnoreCaseOrCpf(valor, cpf)
                .map(u -> User.withUsername(u.getEmail())
                    .password(u.getSenha())
                    .roles(u.getTipoUsuario().name())
                    .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Paginas publicas e arquivos estaticos.
                .requestMatchers(
                    "/", "/login", "/cadastro", "/css/**", "/js/**", "/images/**",
                    "/livros", "/livros/**", "/acessibilidade/**", "/error", "/favicon.ico"
                ).permitAll()
                // Operacoes administrativas exigem perfil FUNCIONARIO.
                .requestMatchers(
                    "/admin/**", "/autores/**", "/editoras/**", "/exemplares/**",
                    "/emprestimos/**", "/historico/**"
                ).hasRole("FUNCIONARIO")
                // Qualquer rota nova fica protegida por padrao.
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("identificador")
                .passwordParameter("senha")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?erro=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/?saiu=true")
                .permitAll()
            );

        return http.build();
    }
}
