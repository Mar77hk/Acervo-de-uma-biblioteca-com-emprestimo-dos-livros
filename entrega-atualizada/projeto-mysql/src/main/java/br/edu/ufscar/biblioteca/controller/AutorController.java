package br.edu.ufscar.biblioteca.controller;

import br.edu.ufscar.biblioteca.domain.Autor;
import br.edu.ufscar.biblioteca.repository.AutorRepository;
import br.edu.ufscar.biblioteca.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores")
public class AutorController {
    private final AutorRepository autores; private final MovimentacaoService movimentacoes;
    public AutorController(AutorRepository autores, MovimentacaoService movimentacoes) { this.autores = autores; this.movimentacoes = movimentacoes; }
    @GetMapping public String listar(Model model) { model.addAttribute("autores", autores.findAllByOrderByNomeAsc()); return "autores/lista"; }
    @GetMapping("/novo") public String novo(Model model) { model.addAttribute("autor", new Autor()); return "autores/form"; }
    @GetMapping("/{id}/editar") public String editar(@PathVariable Long id, Model model) { model.addAttribute("autor", autores.findById(id).orElseThrow()); return "autores/form"; }
    @PostMapping("/salvar") public String salvar(@Valid @ModelAttribute("autor") Autor autor, BindingResult result, Model model, RedirectAttributes redirect, org.springframework.security.core.Authentication auth) {
        if (result.hasErrors()) return "autores/form";
        boolean novo = autor.getId() == null; Autor salvo = autores.save(autor);
        movimentacoes.registrar(novo ? "AUTOR_CADASTRADO" : "AUTOR_ATUALIZADO", "Autor '" + salvo.getNome() + "' salvo.", auth.getName());
        redirect.addFlashAttribute("sucesso", "Autor salvo com sucesso."); return "redirect:/autores";
    }
    @PostMapping("/{id}/excluir") public String excluir(@PathVariable Long id, RedirectAttributes redirect, org.springframework.security.core.Authentication auth) {
        try { Autor a = autores.findById(id).orElseThrow(); autores.delete(a); movimentacoes.registrar("AUTOR_EXCLUIDO", "Autor '" + a.getNome() + "' excluído.", auth.getName()); redirect.addFlashAttribute("sucesso", "Autor excluído."); }
        catch (DataIntegrityViolationException e) { redirect.addFlashAttribute("erro", "Não é possível excluir um autor associado a livros."); }
        return "redirect:/autores";
    }
}
