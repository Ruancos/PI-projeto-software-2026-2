package insper.com.PI.dto;

import insper.com.PI.models.Avaliacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AvaliacaoResponse {

    private Long id;
    private String autor;
    private String conteudo;
    private Integer nota;
    private java.time.LocalDateTime dataAvaliacao;

    public static AvaliacaoResponse from(Avaliacao avaliacao) {
        return new AvaliacaoResponse(
                avaliacao.getId(),
                avaliacao.getAutor(),
                avaliacao.getConteudo(),
                avaliacao.getNota(),
                avaliacao.getDataAvaliacao()
        );
    }
}


