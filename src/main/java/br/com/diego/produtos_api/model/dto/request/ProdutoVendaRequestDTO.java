package br.com.diego.produtos_api.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ProdutoVendaRequestDTO {
    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade de venda necessita ser maior que zero.")
    private Long quantidade;
}
