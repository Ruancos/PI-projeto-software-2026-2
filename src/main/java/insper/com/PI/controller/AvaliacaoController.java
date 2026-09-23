package insper.com.PI.controller;

import insper.com.PI.dto.AvaliacaoRequest;
import insper.com.PI.dto.AvaliacaoResponse;
import insper.com.PI.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @GetMapping
    public List<AvaliacaoResponse> listar(@RequestParam(name = "nome", required = false) String nome) {
        return avaliacaoService.listar(nome);
    }

    @GetMapping("/{id}")
    public AvaliacaoResponse buscarPorid(@PathVariable(name = "id") Long id) {
        return avaliacaoService.buscarPorId(id);
    }

    @PostMapping
    public AvaliacaoResponse criar(@RequestBody AvaliacaoRequest request) {
        return avaliacaoService.criar(request);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable(name = "id") Long id) {
        avaliacaoService.deletar(id);
    }
}
