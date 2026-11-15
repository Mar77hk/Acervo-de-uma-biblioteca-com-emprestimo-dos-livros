package br.edu.ufscar.biblioteca.repository;
import br.edu.ufscar.biblioteca.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("select distinct l from Livro l left join l.autores a left join l.editora e " +
           "where (:titulo is null or lower(l.titulo) like lower(concat('%', :titulo, '%'))) " +
           "and (:autor is null or lower(a.nome) like lower(concat('%', :autor, '%'))) " +
           "and (:editora is null or lower(e.razaoSocial) like lower(concat('%', :editora, '%'))) " +
           "and (:isbn is null or lower(l.isbn) like lower(concat('%', :isbn, '%'))) order by l.titulo")
    List<Livro> buscarComFiltros(@Param("titulo") String titulo, @Param("autor") String autor,
                                 @Param("editora") String editora, @Param("isbn") String isbn);
}
