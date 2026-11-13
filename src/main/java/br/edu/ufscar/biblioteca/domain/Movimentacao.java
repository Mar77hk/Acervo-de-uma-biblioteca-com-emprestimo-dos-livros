package br.edu.ufscar.biblioteca.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40) private String tipo;
    @Column(nullable = false, length = 700) private String descricao;
    @Column(nullable = false, length = 160) private String responsavel;
    @Column(nullable = false) private LocalDateTime dataHora;
    @PrePersist void antesDeSalvar() { if (dataHora == null) dataHora = LocalDateTime.now(); }
    public Long getId() { return id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
