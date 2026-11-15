package br.edu.ufscar.biblioteca.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioForm {
    @NotBlank @Size(max = 120) private String nome;
    @NotBlank @Email @Size(max = 160) private String email;
    @NotBlank @Pattern(regexp = "[0-9]{11}", message = "Informe o CPF com 11 números.") private String cpf;
    @NotBlank @Pattern(regexp = "[0-9]{8}", message = "Informe o CEP com 8 números.") private String cep;
    @NotBlank @Size(min = 8, max = 100, message = "A senha deve ter pelo menos 8 caracteres.") private String senha;
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
}
