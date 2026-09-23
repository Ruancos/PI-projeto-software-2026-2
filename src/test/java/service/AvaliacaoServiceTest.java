package service;

import insper.com.PI.repository.AvaliacaoRepository;
import insper.com.PI.service.AvaliacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        // Implementar o teste para listar todas as avaliações
    }
}
