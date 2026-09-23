package insper.com.PI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoRequest {

    private String autor;
    private String conteudo;
    private Integer nota;
    private java.time.LocalDateTime dataAvaliacao;
}
