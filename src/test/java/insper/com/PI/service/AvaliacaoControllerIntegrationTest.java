package insper.com.PI.service;

import insper.com.PI.dto.AvaliacaoResponse;
import insper.com.PI.repository.AvaliacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import(AvaliacaoService.class)
class AvaliacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AvaliacaoRepository avaliacaoRepository;

    @Test
    void deveCriarAvaliacaoPelaRotaPost() throws Exception {
        LocalDateTime data = LocalDateTime.of(2026, 9, 23, 11, 30);
        AvaliacaoResponse resposta = new AvaliacaoResponse(
                1L, "Ana", "Muito bom", 5, data);

        when(avaliacaoRepository.save(any())).thenAnswer(invocation -> {
            insper.com.PI.models.Avaliacao avaliacao = invocation.getArgument(0);
            avaliacao.setId(1L);
            return avaliacao;
        });

        String request = """
                {
                  "autor": "Ana",
                  "conteudo": "Muito bom",
                  "nota": 5,
                  "dataAvaliacao": "2026-09-23T11:30:00"
                }
                """;

        mockMvc.perform(post("/avaliacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(resposta.getId()))
                .andExpect(jsonPath("$.autor").value(resposta.getAutor()))
                .andExpect(jsonPath("$.conteudo").value(resposta.getConteudo()))
                .andExpect(jsonPath("$.nota").value(resposta.getNota()))
                .andExpect(jsonPath("$.dataAvaliacao").value("2026-09-23T11:30:00"));
    }
}
