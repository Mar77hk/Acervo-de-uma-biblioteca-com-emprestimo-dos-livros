package br.edu.ufscar.biblioteca.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "exemplar_id", nullable = false)
    private Exemplar exemplar;
    @ManyToOne(optional = false) @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(optional = false) @JoinColumn(name = "funcionario_id", nullable = false)
    private Usuario funcionario;
    @Column(nullable = false) private LocalDate dataEmprestimo;
    @Column(nullable = false) private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private boolean avaria;
    @Column(length = 500) private String observacaoAvaria;
    public Long getId() { return id; }
    public Exemplar getExemplar() { return exemplar; }
    public void setExemplar(Exemplar exemplar) { this.exemplar = exemplar; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Usuario getFuncionario() { return funcionario; }
    public void setFuncionario(Usuario funcionario) { this.funcionario = funcionario; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) { this.dataPrevistaDevolucao = dataPrevistaDevolucao; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public boolean isAvaria() { return avaria; }
    public void setAvaria(boolean avaria) { this.avaria = avaria; }
    public String getObservacaoAvaria() { return observacaoAvaria; }
    public void setObservacaoAvaria(String observacaoAvaria) { this.observacaoAvaria = observacaoAvaria; }
    @Transient public boolean isAtrasado() { return dataDevolucao == null && dataPrevistaDevolucao != null && LocalDate.now().isAfter(dataPrevistaDevolucao); }
}
