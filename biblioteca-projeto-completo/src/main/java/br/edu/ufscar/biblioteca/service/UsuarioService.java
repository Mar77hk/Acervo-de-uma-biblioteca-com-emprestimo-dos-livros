package br.edu.ufscar.biblioteca.service;

import br.edu.ufscar.biblioteca.domain.TipoUsuario;
import br.edu.ufscar.biblioteca.domain.Usuario;
import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import br.edu.ufscar.biblioteca.web.UsuarioForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final MovimentacaoService movimentacoes;
    public UsuarioService(UsuarioRepository usuarios, PasswordEncoder encoder, MovimentacaoService movimentacoes) {
        this.usuarios = usuarios; this.encoder = encoder; this.movimentacoes = movimentacoes;
    }
    @Transactional
    public Usuario cadastrar(UsuarioForm form, TipoUsuario tipo, String responsavel) {
        String email = form.getEmail().trim().toLowerCase();
        String cpf = form.getCpf().replaceAll("\\D", "");
        if (usuarios.existsByEmailIgnoreCase(email)) throw new IllegalArgumentException("Este e-mail já está cadastrado.");
        if (usuarios.existsByCpf(cpf)) throw new IllegalArgumentException("Este CPF já está cadastrado.");
        Usuario u = new Usuario();
        u.setNome(form.getNome().trim()); u.setEmail(email); u.setCpf(cpf);
        u.setCep(form.getCep().replaceAll("\\D", "")); u.setSenha(encoder.encode(form.getSenha()));
        u.setTipoUsuario(tipo);
        Usuario salvo = usuarios.save(u);
        movimentacoes.registrar("USUARIO_CADASTRADO", "Usuário " + salvo.getNome() + " cadastrado como " + tipo + ".", responsavel);
        return salvo;
    }
}
