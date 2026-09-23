package insper.com.PI.service;

import insper.com.PI.dto.AvaliacaoRequest;
import insper.com.PI.dto.AvaliacaoResponse;
import insper.com.PI.models.Avaliacao;
import insper.com.PI.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    // rotas: criar, listar, buscar por id, excluir

    public List<AvaliacaoResponse> listar(String nome) {
        return avaliacaoRepository.findAll().stream()
                .filter(avaliacao -> nome == null || avaliacao.getAutor().contains(nome))
                .map(avaliacao -> new AvaliacaoResponse(avaliacao.getId(),  avaliacao.getAutor(), avaliacao.getConteudo(), avaliacao.getNota(), avaliacao.getDataAvaliacao()))
                .collect(Collectors.toList());
    }

    public AvaliacaoResponse buscarPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .map(avaliacao -> new AvaliacaoResponse(avaliacao.getId(),  avaliacao.getAutor(), avaliacao.getConteudo(), avaliacao.getNota(), avaliacao.getDataAvaliacao()))
                .orElse(null);
    }

    public AvaliacaoResponse criar(AvaliacaoRequest request) {

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAutor(request.getAutor());
        avaliacao.setConteudo(request.getConteudo());
        avaliacao.setNota(request.getNota());
        avaliacao.setDataAvaliacao(request.getDataAvaliacao());

        return AvaliacaoResponse.from(avaliacaoRepository.save(avaliacao));
    }

    public void deletar(Long id) {
        AvaliacaoResponse avaliacao = buscarPorId(id);
        avaliacaoRepository.deleteById(id);
    }
}
