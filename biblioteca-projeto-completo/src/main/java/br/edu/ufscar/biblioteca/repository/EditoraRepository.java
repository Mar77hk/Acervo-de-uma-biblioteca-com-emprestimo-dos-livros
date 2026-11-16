package br.edu.ufscar.biblioteca.repository;
import br.edu.ufscar.biblioteca.domain.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EditoraRepository extends JpaRepository<Editora, Long> { List<Editora> findAllByOrderByRazaoSocialAsc(); }
