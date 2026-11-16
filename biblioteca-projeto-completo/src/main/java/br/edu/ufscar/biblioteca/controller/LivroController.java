package br.edu.ufscar.biblioteca.controller;

import br.edu.ufscar.biblioteca.domain.Livro;
import br.edu.ufscar.biblioteca.repository.AutorRepository;
import br.edu.ufscar.biblioteca.repository.EditoraRepository;
import br.edu.ufscar.biblioteca.repository.ExemplarRepository;
import br.edu.ufscar.biblioteca.service.LivroService;
import br.edu.ufscar.biblioteca.web.LivroForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.stream.Collectors;

@Controller
public class LivroController {
    private final LivroService service;
    private final AutorRepository autores;
    private final EditoraRepository editoras;
    private final ExemplarRepository exemplares;
    public LivroController(LivroService service, AutorRepository autores, EditoraRepository editoras, ExemplarRepository exemplares) {
        this.service = service; this.autores = autores; this.editoras = editoras; this.exemplares = exemplares;
    }
    @GetMapping("/livros")
    public String listar(@RequestParam(required = false) String titulo, @RequestParam(required = false) String autor,
                         @RequestParam(required = false) String editora, @RequestParam(required = false) String isbn, Model model) {
        model.addAttribute("livros", service.buscar(titulo, autor, editora, isbn));
        model.addAttribute("fTitulo", titulo); model.addAttribute("fAutor", autor); model.addAttribute("fEditora", editora); model.addAttribute("fIsbn", isbn);
        return "livros/lista";
    }
    @GetMapping("/livros/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        Livro livro = service.buscarPorId(id);
        model.addAttribute("livro", livro);
        model.addAttribute("totalExemplares", exemplares.countByLivroId(id));
        model.addAttribute("disponiveis", exemplares.findByEstadoOrderByIdAsc(br.edu.ufscar.biblioteca.domain.EstadoExemplar.DISPONIVEL).stream().filter(e -> e.getLivro().getId().equals(id)).count());
        return "livros/detalhe";
    }
    @GetMapping("/admin/livros/novo")
    public String novo(Model model) { prepararFormulario(model, new LivroForm()); return "livros/form"; }
    @GetMapping("/admin/livros/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Livro livro = service.buscarPorId(id); LivroForm f = new LivroForm();
        f.setId(id); f.setTitulo(livro.getTitulo()); f.setNumeroVolume(livro.getNumeroVolume()); f.setIsbn(livro.getIsbn());
        f.setEditoraId(livro.getEditora().getId()); f.setAutoresIds(livro.getAutores().stream().map(a -> a.getId()).collect(Collectors.toList()));
        f.setQuantidadeExemplares((int) exemplares.countByLivroId(id)); prepararFormulario(model, f); return "livros/form";
    }
    @PostMapping("/admin/livros/salvar")
    public String salvar(@Valid @ModelAttribute("form") LivroForm form, BindingResult result, Model model,
                         RedirectAttributes redirect, org.springframework.security.core.Authentication auth) {
        if (result.hasErrors()) { prepararListas(model); return "livros/form"; }
        try { service.salvar(form, auth.getName()); redirect.addFlashAttribute("sucesso", "Livro salvo. Os exemplares foram atualizados quando necessário."); return "redirect:/livros"; }
        catch (RuntimeException e) { model.addAttribute("erro", e.getMessage()); prepararListas(model); return "livros/form"; }
    }
    private void prepararFormulario(Model model, LivroForm form) { model.addAttribute("form", form); prepararListas(model); }
    private void prepararListas(Model model) { model.addAttribute("autores", autores.findAllByOrderByNomeAsc()); model.addAttribute("editoras", editoras.findAllByOrderByRazaoSocialAsc()); }
}
