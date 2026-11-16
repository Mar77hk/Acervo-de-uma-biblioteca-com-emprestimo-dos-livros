package br.edu.ufscar.biblioteca.config;

import br.edu.ufscar.biblioteca.domain.Autor;
import br.edu.ufscar.biblioteca.domain.Editora;
import br.edu.ufscar.biblioteca.domain.EstadoExemplar;
import br.edu.ufscar.biblioteca.domain.Exemplar;
import br.edu.ufscar.biblioteca.domain.Livro;
import br.edu.ufscar.biblioteca.domain.TipoUsuario;
import br.edu.ufscar.biblioteca.domain.Usuario;
import br.edu.ufscar.biblioteca.repository.AutorRepository;
import br.edu.ufscar.biblioteca.repository.EditoraRepository;
import br.edu.ufscar.biblioteca.repository.ExemplarRepository;
import br.edu.ufscar.biblioteca.repository.LivroRepository;
import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner inicializarDados(
            UsuarioRepository usuarios,
            EditoraRepository editoras,
            AutorRepository autores,
            LivroRepository livros,
            ExemplarRepository exemplares,
            PasswordEncoder encoder) {

        return args -> {
            // Cria um funcionário inicial somente quando ainda não existe nenhum.
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

            // Insere o catálogo demonstrativo apenas se ainda não houver livros.
            // Assim, reiniciar a aplicação não duplica os dados nem sobrescreve o catálogo.
            if (livros.count() == 0) {
                Editora editora = new Editora();
                editora.setRazaoSocial("Editora Demonstração");
                editora.setCnpj("00000000000191");
                editora.setEndereco("Rua das Bibliotecas, 100");
                editora.setCidade("São Carlos");
                editora = editoras.save(editora);

                Autor autor = new Autor();
                autor.setNome("Machado de Assis");
                autor.setDataNascimento(LocalDate.of(1839, 6, 21));
                autor = autores.save(autor);

                Livro livro = new Livro();
                livro.setTitulo("Dom Casmurro");
                livro.setNumeroVolume(1);
                livro.setIsbn("9780000000001");
                livro.setEditora(editora);
                livro.setAutores(Set.of(autor));
                livro = livros.save(livro);

                Exemplar exemplar = new Exemplar();
                exemplar.setCodigoPatrimonio("DEMO-0001");
                exemplar.setLivro(livro);
                exemplar.setEstado(EstadoExemplar.DISPONIVEL);
                exemplar.setObservacoes("Exemplar inicial para demonstração do sistema.");
                exemplares.save(exemplar);
            }
        };
    }
}
