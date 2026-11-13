package br.edu.ufscar.biblioteca.service;

import br.edu.ufscar.biblioteca.domain.*;
import br.edu.ufscar.biblioteca.repository.*;
import br.edu.ufscar.biblioteca.web.LivroForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Service
public class LivroService {
    private final LivroRepository livros;
    private final AutorRepository autores;
    private final EditoraRepository editoras;
    private final ExemplarRepository exemplares;
    private final MovimentacaoService movimentacoes;
    public LivroService(LivroRepository livros, AutorRepository autores, EditoraRepository editoras,
                        ExemplarRepository exemplares, MovimentacaoService movimentacoes) {
        this.livros = livros; this.autores = autores; this.editoras = editoras;
        this.exemplares = exemplares; this.movimentacoes = movimentacoes;
    }
    @Transactional(readOnly = true)
    public List<Livro> buscar(String titulo, String autor, String editora, String isbn) {
        return livros.buscarComFiltros(vazioParaNull(titulo), vazioParaNull(autor), vazioParaNull(editora), vazioParaNull(isbn));
    }
    private String vazioParaNull(String s) { return (s == null || s.isBlank()) ? null : s.trim(); }
    @Transactional(readOnly = true) public Livro buscarPorId(Long id) { return livros.findById(id).orElseThrow(() -> new IllegalArgumentException("Livro não encontrado.")); }
    @Transactional(readOnly = true) public List<Livro> listarTodos() { return livros.findAll(); }
    @Transactional
    public Livro salvar(LivroForm form, String responsavel) {
        boolean novo = form.getId() == null;
        Livro livro = novo ? new Livro() : buscarPorId(form.getId());
        livro.setTitulo(form.getTitulo().trim());
        livro.setNumeroVolume(form.getNumeroVolume());
        livro.setIsbn(form.getIsbn().replace("-", "").trim());
        livro.setEditora(editoras.findById(form.getEditoraId()).orElseThrow(() -> new IllegalArgumentException("Selecione uma editora cadastrada.")));
        var ids = form.getAutoresIds() == null ? List.<Long>of() : form.getAutoresIds();
        livro.setAutores(new LinkedHashSet<>(autores.findAllById(ids)));
        Livro salvo = livros.save(livro);
        long existentes = novo ? 0 : exemplares.countByLivroId(salvo.getId());
        int desejados = form.getQuantidadeExemplares() == null ? (int) existentes : form.getQuantidadeExemplares();
        int adicionar = (int) Math.max(0, desejados - existentes);
        for (int i = 0; i < adicionar; i++) {
            Exemplar ex = new Exemplar();
            ex.setLivro(salvo);
            ex.setCodigoPatrimonio("EX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            ex.setEstado(EstadoExemplar.DISPONIVEL);
            exemplares.save(ex);
        }
        movimentacoes.registrar(novo ? "LIVRO_CADASTRADO" : "LIVRO_ATUALIZADO",
            "Livro '" + salvo.getTitulo() + "' " + (novo ? "cadastrado" : "atualizado") + ".", responsavel);
        if (adicionar > 0) movimentacoes.registrar("EXEMPLAR_CADASTRADO", adicionar + " exemplar(es) adicionado(s) ao livro '" + salvo.getTitulo() + "'.", responsavel);
        return salvo;
    }
}
