package br.edu.ufscar.biblioteca.repository;
import br.edu.ufscar.biblioteca.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AutorRepository extends JpaRepository<Autor, Long> { List<Autor> findAllByOrderByNomeAsc(); }
