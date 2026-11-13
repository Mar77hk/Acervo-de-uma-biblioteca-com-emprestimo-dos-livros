package br.edu.ufscar.biblioteca.repository;
import br.edu.ufscar.biblioteca.domain.TipoUsuario;
import br.edu.ufscar.biblioteca.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailIgnoreCaseOrCpf(String email, String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByCpf(String cpf);
    long countByTipoUsuario(TipoUsuario tipoUsuario);
}
