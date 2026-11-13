package br.edu.ufscar.biblioteca.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class LivroForm {
    private Long id;
    @NotBlank @Size(max = 180) private String titulo;
    @NotNull @Min(1) private Integer numeroVolume;
    @NotBlank @Pattern(regexp = "[0-9Xx-]{10,20}", message = "Informe um ISBN válido (10 ou 13 dígitos, podendo conter hífen).")
    private String isbn;
    @NotNull private Long editoraId;
    @NotEmpty(message = "Selecione pelo menos um autor.") private List<Long> autoresIds = new ArrayList<>();
    @NotNull @Min(1) private Integer quantidadeExemplares = 1;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getNumeroVolume() { return numeroVolume; }
    public void setNumeroVolume(Integer numeroVolume) { this.numeroVolume = numeroVolume; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Long getEditoraId() { return editoraId; }
    public void setEditoraId(Long editoraId) { this.editoraId = editoraId; }
    public List<Long> getAutoresIds() { return autoresIds; }
    public void setAutoresIds(List<Long> autoresIds) { this.autoresIds = autoresIds; }
    public Integer getQuantidadeExemplares() { return quantidadeExemplares; }
    public void setQuantidadeExemplares(Integer quantidadeExemplares) { this.quantidadeExemplares = quantidadeExemplares; }
}
