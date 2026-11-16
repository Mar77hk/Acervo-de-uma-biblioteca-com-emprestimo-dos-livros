package br.edu.ufscar.biblioteca.controller;

import br.edu.ufscar.biblioteca.domain.Editora;
import br.edu.ufscar.biblioteca.repository.EditoraRepository;
import br.edu.ufscar.biblioteca.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/editoras")
public class EditoraController {
    private final EditoraRepository editoras; private final MovimentacaoService movimentacoes;
    public EditoraController(EditoraRepository editoras, MovimentacaoService movimentacoes) { this.editoras = editoras; this.movimentacoes = movimentacoes; }
    @GetMapping public String listar(Model model) { model.addAttribute("editoras", editoras.findAllByOrderByRazaoSocialAsc()); return "editoras/lista"; }
    @GetMapping("/nova") public String nova(Model model) { model.addAttribute("editora", new Editora()); return "editoras/form"; }
    @GetMapping("/{id}/editar") public String editar(@PathVariable Long id, Model model) { model.addAttribute("editora", editoras.findById(id).orElseThrow()); return "editoras/form"; }
    @PostMapping("/salvar") public String salvar(@Valid @ModelAttribute("editora") Editora editora, BindingResult result, RedirectAttributes redirect, org.springframework.security.core.Authentication auth) {
        if (result.hasErrors()) return "editoras/form";
        boolean novo = editora.getId() == null; Editora e = editoras.save(editora);
        movimentacoes.registrar(novo ? "EDITORA_CADASTRADA" : "EDITORA_ATUALIZADA", "Editora '" + e.getRazaoSocial() + "' salva.", auth.getName());
        redirect.addFlashAttribute("sucesso", "Editora salva com sucesso."); return "redirect:/editoras";
    }
    @PostMapping("/{id}/excluir") public String excluir(@PathVariable Long id, RedirectAttributes redirect, org.springframework.security.core.Authentication auth) {
        try { Editora e = editoras.findById(id).orElseThrow(); editoras.delete(e); movimentacoes.registrar("EDITORA_EXCLUIDA", "Editora '" + e.getRazaoSocial() + "' excluída.", auth.getName()); redirect.addFlashAttribute("sucesso", "Editora excluída."); }
        catch (DataIntegrityViolationException e) { redirect.addFlashAttribute("erro", "Não é possível excluir uma editora vinculada a livros."); }
        return "redirect:/editoras";
    }
}
