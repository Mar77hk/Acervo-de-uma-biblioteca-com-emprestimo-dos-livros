package br.edu.ufscar.biblioteca.service;

import br.edu.ufscar.biblioteca.domain.EstadoExemplar;
import br.edu.ufscar.biblioteca.domain.Exemplar;
import br.edu.ufscar.biblioteca.repository.ExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ExemplarService {
    private final ExemplarRepository exemplares;
    private final MovimentacaoService movimentacoes;
    public ExemplarService(ExemplarRepository exemplares, MovimentacaoService movimentacoes) { this.exemplares = exemplares; this.movimentacoes = movimentacoes; }
    @Transactional(readOnly = true) public List<Exemplar> listar() { return exemplares.findAllByOrderByIdDesc(); }
    @Transactional
    public void alterarEstado(Long id, EstadoExemplar estado, String observacao, String responsavel) {
        Exemplar ex = exemplares.findById(id).orElseThrow(() -> new IllegalArgumentException("Exemplar não encontrado."));
        if (ex.getEstado() == EstadoExemplar.EMPRESTADO) throw new IllegalArgumentException("Um exemplar emprestado não pode ser alterado aqui.");
        ex.setEstado(estado); ex.setObservacoes(observacao); exemplares.save(ex);
        String tipo = estado == EstadoExemplar.DESCARTADO ? "DESCARTE" : "MANUTENCAO";
        movimentacoes.registrar(tipo, "Exemplar " + ex.getCodigoPatrimonio() + " alterado para " + estado + ". " + (observacao == null ? "" : observacao), responsavel);
    }
}
