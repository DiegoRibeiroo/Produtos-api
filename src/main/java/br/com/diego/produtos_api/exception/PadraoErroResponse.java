package br.com.diego.produtos_api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class PadraoErroResponse {
    private OffsetDateTime timestamp;
    private Integer status;
    private String erro;
    private List<String> mensagens;
}
