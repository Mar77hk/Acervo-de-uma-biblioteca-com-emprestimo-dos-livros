package br.edu.ufscar.biblioteca.repository;
import br.edu.ufscar.biblioteca.domain.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findAllByOrderByDataHoraDesc();
    List<Movimentacao> findByTipoOrderByDataHoraDesc(String tipo);
}
