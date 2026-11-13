package br.edu.ufscar.biblioteca.repository;
import br.edu.ufscar.biblioteca.domain.EstadoExemplar;
import br.edu.ufscar.biblioteca.domain.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
    List<Exemplar> findAllByOrderByIdDesc();
    List<Exemplar> findByEstadoOrderByIdAsc(EstadoExemplar estado);
    long countByLivroId(Long livroId);
}
