package br.edu.ufscar.biblioteca.repository;
import br.edu.ufscar.biblioteca.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findAllByOrderByDataEmprestimoDesc();
    List<Emprestimo> findByDataDevolucaoIsNullOrderByDataPrevistaDevolucaoAsc();
}
