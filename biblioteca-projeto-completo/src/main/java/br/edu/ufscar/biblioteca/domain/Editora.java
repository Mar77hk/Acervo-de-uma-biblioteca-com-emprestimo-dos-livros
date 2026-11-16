package br.edu.ufscar.biblioteca.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "editoras", uniqueConstraints = @UniqueConstraint(name = "uk_editora_cnpj", columnNames = "cnpj"))
public class Editora {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Size(max = 160)
    @Column(nullable = false, length = 160)
    private String razaoSocial;
    @NotBlank @Pattern(regexp = "[0-9]{14}", message = "Informe o CNPJ com 14 números.")
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;
    @NotBlank @Size(max = 180)
    private String endereco;
    @NotBlank @Size(max = 100)
    private String cidade;
    @PrePersist @PreUpdate void normalizar() { if (cnpj != null) cnpj = cnpj.replaceAll("\\D", ""); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
}
