package br.edu.ufscar.biblioteca.service;

import br.edu.ufscar.biblioteca.domain.Movimentacao;
import br.edu.ufscar.biblioteca.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MovimentacaoService {
    private final MovimentacaoRepository repository;
    public MovimentacaoService(MovimentacaoRepository repository) { this.repository = repository; }
    @Transactional public void registrar(String tipo, String descricao, String responsavel) {
        Movimentacao m = new Movimentacao();
        m.setTipo(tipo);
        m.setDescricao(descricao);
        m.setResponsavel((responsavel == null || responsavel.isBlank()) ? "Sistema" : responsavel);
        repository.save(m);
    }
    @Transactional(readOnly = true) public List<Movimentacao> listar() { return repository.findAllByOrderByDataHoraDesc(); }
    @Transactional(readOnly = true) public List<Movimentacao> listarPorTipo(String tipo) {
        return (tipo == null || tipo.isBlank()) ? listar() : repository.findByTipoOrderByDataHoraDesc(tipo);
    }
}
