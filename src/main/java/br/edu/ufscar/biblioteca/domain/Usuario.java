package br.edu.ufscar.biblioteca.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
    @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"),
    @UniqueConstraint(name = "uk_usuario_cpf", columnNames = "cpf")
})
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Size(max = 120)
    private String nome;
    @NotBlank @Email @Size(max = 160)
    @Column(nullable = false, length = 160)
    private String email;
    @NotBlank @Pattern(regexp = "[0-9]{11}", message = "Informe o CPF com 11 números.")
    @Column(nullable = false, length = 11)
    private String cpf;
    @NotBlank @Pattern(regexp = "[0-9]{8}", message = "Informe o CEP com 8 números.")
    private String cep;
    @NotBlank @Size(min = 8, max = 100)
    @Column(nullable = false)
    private String senha;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private TipoUsuario tipoUsuario = TipoUsuario.COMUM;

    @PrePersist @PreUpdate
    void normalizar() {
        if (email != null) email = email.trim().toLowerCase();
        if (cpf != null) cpf = cpf.replaceAll("\\D", "");
        if (cep != null) cep = cep.replaceAll("\\D", "");
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
