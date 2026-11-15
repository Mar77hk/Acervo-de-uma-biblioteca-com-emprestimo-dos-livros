package br.edu.ufscar.biblioteca.controller;

import br.edu.ufscar.biblioteca.domain.TipoUsuario;
import br.edu.ufscar.biblioteca.repository.UsuarioRepository;
import br.edu.ufscar.biblioteca.service.UsuarioService;
import br.edu.ufscar.biblioteca.web.UsuarioForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
    private final UsuarioService service; private final UsuarioRepository usuarios;
    public UsuarioController(UsuarioService service, UsuarioRepository usuarios) { this.service = service; this.usuarios = usuarios; }
    @GetMapping("/cadastro") public String cadastro(Model model) { model.addAttribute("form", new UsuarioForm()); return "usuarios/cadastro"; }
    @PostMapping("/cadastro") public String cadastrar(@Valid @ModelAttribute("form") UsuarioForm form, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) return "usuarios/cadastro";
        try { service.cadastrar(form, TipoUsuario.COMUM, "Auto cadastro"); redirect.addFlashAttribute("sucesso", "Cadastro realizado. Entre usando e-mail ou CPF e sua senha."); return "redirect:/login"; }
        catch (RuntimeException e) { model.addAttribute("erro", e.getMessage()); return "usuarios/cadastro"; }
    }
    @GetMapping("/admin/usuarios") public String admin(Model model) { model.addAttribute("usuarios", usuarios.findAll()); model.addAttribute("form", new UsuarioForm()); return "usuarios/admin"; }
    @PostMapping("/admin/usuarios/funcionario") public String cadastrarFuncionario(@Valid @ModelAttribute("form") UsuarioForm form, BindingResult result, Model model, RedirectAttributes redirect, org.springframework.security.core.Authentication auth) {
        if (result.hasErrors()) { model.addAttribute("usuarios", usuarios.findAll()); return "usuarios/admin"; }
        try { service.cadastrar(form, TipoUsuario.FUNCIONARIO, auth.getName()); redirect.addFlashAttribute("sucesso", "Funcionário cadastrado."); return "redirect:/admin/usuarios"; }
        catch (RuntimeException e) { model.addAttribute("usuarios", usuarios.findAll()); model.addAttribute("erro", e.getMessage()); return "usuarios/admin"; }
    }
}
