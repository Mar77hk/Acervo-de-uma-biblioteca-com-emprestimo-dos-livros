package br.edu.ufscar.biblioteca.service;

import br.edu.ufscar.biblioteca.domain.*;
import br.edu.ufscar.biblioteca.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimos;
    private final ExemplarRepository exemplares;
    private final UsuarioRepository usuarios;
    private final MovimentacaoService movimentacoes;
    public EmprestimoService(EmprestimoRepository emprestimos, ExemplarRepository exemplares,
                             UsuarioRepository usuarios, MovimentacaoService movimentacoes) {
        this.emprestimos = emprestimos; this.exemplares = exemplares; this.usuarios = usuarios; this.movimentacoes = movimentacoes;
    }
    @Transactional(readOnly = true) public List<Emprestimo> listar() { return emprestimos.findAllByOrderByDataEmprestimoDesc(); }
    @Transactional(readOnly = true) public List<Emprestimo> listarAbertos() { return emprestimos.findByDataDevolucaoIsNullOrderByDataPrevistaDevolucaoAsc(); }
    @Transactional
    public void emprestar(Long exemplarId, Long usuarioId, int dias, String identificadorFuncionario) {
        if (dias < 1 || dias > 90) throw new IllegalArgumentException("O prazo deve estar entre 1 e 90 dias.");
        Exemplar ex = exemplares.findById(exemplarId).orElseThrow(() -> new IllegalArgumentException("Exemplar não encontrado."));
        if (ex.getEstado() != EstadoExemplar.DISPONIVEL) throw new IllegalArgumentException("O exemplar não está disponível.");
        Usuario usuario = usuarios.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        Usuario funcionario = usuarios.findByEmailIgnoreCaseOrCpf(identificadorFuncionario, identificadorFuncionario)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário autenticado não encontrado."));
        Emprestimo e = new Emprestimo();
        e.setExemplar(ex); e.setUsuario(usuario); e.setFuncionario(funcionario);
        e.setDataEmprestimo(LocalDate.now()); e.setDataPrevistaDevolucao(LocalDate.now().plusDays(dias));
        emprestimos.save(e);
        ex.setEstado(EstadoExemplar.EMPRESTADO); exemplares.save(ex);
        movimentacoes.registrar("EMPRESTIMO", "Empréstimo do exemplar " + ex.getCodigoPatrimonio() + " para " + usuario.getNome() + ".", funcionario.getNome());
    }
    @Transactional
    public void devolver(Long emprestimoId, boolean avaria, String observacao, String identificadorFuncionario) {
        Emprestimo e = emprestimos.findById(emprestimoId).orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado."));
        if (e.getDataDevolucao() != null) throw new IllegalArgumentException("Este empréstimo já foi devolvido.");
        Usuario funcionario = usuarios.findByEmailIgnoreCaseOrCpf(identificadorFuncionario, identificadorFuncionario)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário autenticado não encontrado."));
        e.setDataDevolucao(LocalDate.now()); e.setAvaria(avaria); e.setObservacaoAvaria(observacao);
        Exemplar ex = e.getExemplar();
        ex.setEstado(avaria ? EstadoExemplar.MANUTENCAO : EstadoExemplar.DISPONIVEL);
        if (avaria) ex.setObservacoes(observacao);
        exemplares.save(ex); emprestimos.save(e);
        String atraso = LocalDate.now().isAfter(e.getDataPrevistaDevolucao()) ? " Devolução com atraso." : " Devolução no prazo.";
        movimentacoes.registrar("DEVOLUCAO", "Devolução do exemplar " + ex.getCodigoPatrimonio() + "." + atraso, funcionario.getNome());
        if (avaria) movimentacoes.registrar("MANUTENCAO", "Exemplar " + ex.getCodigoPatrimonio() + " encaminhado para manutenção: " + observacao, funcionario.getNome());
    }
}
