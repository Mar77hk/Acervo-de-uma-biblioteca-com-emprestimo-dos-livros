package br.edu.ufscar.biblioteca.controller;

import br.edu.ufscar.biblioteca.domain.EstadoExemplar;
import br.edu.ufscar.biblioteca.service.ExemplarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/exemplares")
public class ExemplarController {
    private final ExemplarService service;
    public ExemplarController(ExemplarService service) { this.service = service; }
    @GetMapping public String listar(Model model) { model.addAttribute("exemplares", service.listar()); return "exemplares/lista"; }
    @PostMapping("/{id}/manutencao") public String manutencao(@PathVariable Long id, @RequestParam(defaultValue = "") String observacao, org.springframework.security.core.Authentication auth, RedirectAttributes redirect) {
        try { service.alterarEstado(id, EstadoExemplar.MANUTENCAO, observacao, auth.getName()); redirect.addFlashAttribute("sucesso", "Exemplar encaminhado para manutenção."); }
        catch (RuntimeException e) { redirect.addFlashAttribute("erro", e.getMessage()); }
        return "redirect:/exemplares";
    }
    @PostMapping("/{id}/descartar") public String descartar(@PathVariable Long id, @RequestParam(defaultValue = "") String observacao, org.springframework.security.core.Authentication auth, RedirectAttributes redirect) {
        try { service.alterarEstado(id, EstadoExemplar.DESCARTADO, observacao, auth.getName()); redirect.addFlashAttribute("sucesso", "Exemplar descartado."); }
        catch (RuntimeException e) { redirect.addFlashAttribute("erro", e.getMessage()); }
        return "redirect:/exemplares";
    }
}
