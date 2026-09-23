package insper.com.PI.controller;

import insper.com.PI.dto.AvaliacaoRequest;
import insper.com.PI.dto.AvaliacaoResponse;
import insper.com.PI.models.Avaliacao;
import insper.com.PI.repository.AvaliacaoRepository;
import insper.com.PI.service.AvaliacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    private AvaliacaoService avaliacaoService;

    @BeforeEach
    public void setUp() {
        avaliacaoService = new AvaliacaoService(avaliacaoRepository);
    }

    @Test
    void deveListarTodasAsAvaliacoes() {
        Avaliacao primeira = avaliacao(1L, "Ana", "Muito bom", 5);
        Avaliacao segunda = avaliacao(2L, "Bruno", "Pode melhorar", 3);
        when(avaliacaoRepository.findAll()).thenReturn(List.of(primeira, segunda));

        List<AvaliacaoResponse> resultado = avaliacaoService.listar(null);

        assertEquals(2, resultado.size());
        assertEquals("Ana", resultado.get(0).getAutor());
        assertEquals("Bruno", resultado.get(1).getAutor());
        verify(avaliacaoRepository).findAll();
    }

    @Test
    void deveListarAvaliacoesFiltrandoPorAutor() {
        Avaliacao ana = avaliacao(1L, "Ana", "Muito bom", 5);
        Avaliacao bruno = avaliacao(2L, "Bruno", "Pode melhorar", 3);
        when(avaliacaoRepository.findAll()).thenReturn(List.of(ana, bruno));

        List<AvaliacaoResponse> resultado = avaliacaoService.listar("Ana");

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        verify(avaliacaoRepository).findAll();
    }

    @Test
    void deveBuscarAvaliacaoPorId() {
        Avaliacao avaliacao = avaliacao(1L, "Ana", "Muito bom", 5);
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        AvaliacaoResponse resultado = avaliacaoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Muito bom", resultado.getConteudo());
    }

    @Test
    void deveRetornarNuloQuandoAvaliacaoNaoForEncontrada() {
        when(avaliacaoRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(avaliacaoService.buscarPorId(99L));
    }

    @Test
    void deveCriarAvaliacao() {
        LocalDateTime data = LocalDateTime.of(2026, 9, 23, 11, 30);
        AvaliacaoRequest request = new AvaliacaoRequest();
        request.setAutor("Ana");
        request.setConteudo("Muito bom");
        request.setNota(5);
        request.setDataAvaliacao(data);

        Avaliacao salva = new Avaliacao(10L, "Ana", "Muito bom", 5, data);
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(salva);

        AvaliacaoResponse resultado = avaliacaoService.criar(request);

        assertEquals(10L, resultado.getId());
        assertEquals("Ana", resultado.getAutor());
        assertEquals("Muito bom", resultado.getConteudo());
        assertEquals(5, resultado.getNota());
        assertEquals(data, resultado.getDataAvaliacao());
        verify(avaliacaoRepository).save(argThat(avaliacao ->
                avaliacao.getId() == null
                        && "Ana".equals(avaliacao.getAutor())
                        && "Muito bom".equals(avaliacao.getConteudo())
                        && avaliacao.getNota() == 5
                        && data.equals(avaliacao.getDataAvaliacao())));
    }

    @Test
    void deveDeletarAvaliacao() {
        Avaliacao avaliacao = avaliacao(1L, "Ana", "Muito bom", 5);
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        avaliacaoService.deletar(1L);

        verify(avaliacaoRepository).findById(1L);
        verify(avaliacaoRepository).deleteById(1L);
    }

    @Test
    void deveDeletarMesmoQuandoAvaliacaoNaoForEncontrada() {
        when(avaliacaoRepository.findById(99L)).thenReturn(Optional.empty());

        avaliacaoService.deletar(99L);

        verify(avaliacaoRepository).deleteById(99L);
    }

    private Avaliacao avaliacao(Long id, String autor, String conteudo, Integer nota) {
        return new Avaliacao(id, autor, conteudo, nota, LocalDateTime.of(2026, 9, 23, 10, 0));
    }
}
