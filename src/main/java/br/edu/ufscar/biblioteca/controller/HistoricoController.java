package br.edu.ufscar.biblioteca.controller;
import br.edu.ufscar.biblioteca.service.MovimentacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/historico")
public class HistoricoController {
    private final MovimentacaoService service;
    public HistoricoController(MovimentacaoService service) { this.service = service; }
    @GetMapping public String listar(@RequestParam(required = false) String tipo, Model model) { model.addAttribute("movimentacoes", service.listarPorTipo(tipo)); model.addAttribute("tipoSelecionado", tipo); return "historico/lista"; }
}
