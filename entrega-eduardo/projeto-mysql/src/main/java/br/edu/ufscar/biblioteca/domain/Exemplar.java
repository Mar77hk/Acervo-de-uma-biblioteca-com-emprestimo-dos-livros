package br.edu.ufscar.biblioteca.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exemplares", uniqueConstraints = @UniqueConstraint(name = "uk_exemplar_codigo", columnNames = "codigoPatrimonio"))
public class Exemplar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 40)
    private String codigoPatrimonio;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private EstadoExemplar estado = EstadoExemplar.DISPONIVEL;
    private LocalDate dataCadastro = LocalDate.now();
    @Column(length = 300)
    private String observacoes;
    public Long getId() { return id; }
    public String getCodigoPatrimonio() { return codigoPatrimonio; }
    public void setCodigoPatrimonio(String codigoPatrimonio) { this.codigoPatrimonio = codigoPatrimonio; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public EstadoExemplar getEstado() { return estado; }
    public void setEstado(EstadoExemplar estado) { this.estado = estado; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
