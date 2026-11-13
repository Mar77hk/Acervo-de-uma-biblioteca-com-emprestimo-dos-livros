package br.edu.ufscar.biblioteca.controller;

import br.edu.ufscar.biblioteca.domain.EstadoExemplar;
import br.edu.ufscar.biblioteca.repository.ExemplarRepository;
import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import br.edu.ufscar.biblioteca.service.EmprestimoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService service; private final ExemplarRepository exemplares; private final UsuarioRepository usuarios;
    public EmprestimoController(EmprestimoService service, ExemplarRepository exemplares, UsuarioRepository usuarios) { this.service = service; this.exemplares = exemplares; this.usuarios = usuarios; }
    @GetMapping public String listar(Model model) { model.addAttribute("emprestimos", service.listar()); return "emprestimos/lista"; }
    @GetMapping("/novo") public String novo(Model model) { model.addAttribute("exemplares", exemplares.findByEstadoOrderByIdAsc(EstadoExemplar.DISPONIVEL)); model.addAttribute("usuarios", usuarios.findAll()); return "emprestimos/form"; }
    @PostMapping("/salvar") public String salvar(@RequestParam Long exemplarId, @RequestParam Long usuarioId, @RequestParam(defaultValue = "14") int dias, org.springframework.security.core.Authentication auth, RedirectAttributes redirect) {
        try { service.emprestar(exemplarId, usuarioId, dias, auth.getName()); redirect.addFlashAttribute("sucesso", "Empréstimo registrado."); }
        catch (RuntimeException e) { redirect.addFlashAttribute("erro", e.getMessage()); }
        return "redirect:/emprestimos";
    }
    @PostMapping("/{id}/devolver") public String devolver(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean avaria, @RequestParam(defaultValue = "") String observacao, org.springframework.security.core.Authentication auth, RedirectAttributes redirect) {
        try { service.devolver(id, avaria, observacao, auth.getName()); redirect.addFlashAttribute("sucesso", "Devolução registrada."); }
        catch (RuntimeException e) { redirect.addFlashAttribute("erro", e.getMessage()); }
        return "redirect:/emprestimos";
    }
}
