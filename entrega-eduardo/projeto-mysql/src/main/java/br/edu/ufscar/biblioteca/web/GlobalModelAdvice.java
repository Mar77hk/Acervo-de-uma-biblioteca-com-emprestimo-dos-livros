package br.edu.ufscar.biblioteca.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GlobalModelAdvice {
    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated(Authentication authentication) { return authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal()); }
    @ModelAttribute("isFuncionario")
    public boolean isFuncionario(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream().anyMatch(a -> "ROLE_FUNCIONARIO".equals(a.getAuthority()));
    }
    @ModelAttribute("nomeLogado")
    public String nomeLogado(Authentication authentication) { return authentication == null ? "" : authentication.getName(); }
}
