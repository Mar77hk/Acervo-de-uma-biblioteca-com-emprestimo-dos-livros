package br.edu.ufscar.biblioteca.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "livros", uniqueConstraints = @UniqueConstraint(name = "uk_livro_isbn", columnNames = "isbn"))
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Size(max = 180)
    @Column(nullable = false, length = 180)
    private String titulo;
    @NotNull @Column(nullable = false)
    private Integer numeroVolume;
    @NotBlank @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "editora_id", nullable = false)
    private Editora editora;
    @ManyToMany
    @JoinTable(name = "livro_autor",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<Autor> autores = new LinkedHashSet<>();
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getNumeroVolume() { return numeroVolume; }
    public void setNumeroVolume(Integer numeroVolume) { this.numeroVolume = numeroVolume; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }
    public Set<Autor> getAutores() { return autores; }
    public void setAutores(Set<Autor> autores) { this.autores = autores; }
}
