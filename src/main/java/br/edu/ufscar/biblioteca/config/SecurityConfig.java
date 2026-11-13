package br.edu.ufscar.biblioteca.config;

import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean UserDetailsService userDetailsService(UsuarioRepository usuarios) {
        return identificador -> usuarios.findByEmailIgnoreCaseOrCpf(identificador, identificador)
            .map(u -> User.withUsername(u.getEmail()).password(u.getSenha()).roles(u.getTipoUsuario().name()).build())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/cadastro", "/css/**", "/js/**", "/images/**", "/livros", "/livros/**", "/acessibilidade/**", "/h2-console/**", "/error").permitAll()
                .requestMatchers("/admin/**", "/autores/**", "/editoras/**", "/exemplares/**", "/emprestimos/**", "/historico/**", "/usuarios/funcionarios/**").hasRole("FUNCIONARIO")
                .anyRequest().permitAll())
            .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
                .usernameParameter("identificador").passwordParameter("senha")
                .defaultSuccessUrl("/", true).failureUrl("/login?erro=true").permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/?saiu=true").permitAll());
        return http.build();
    }
}
