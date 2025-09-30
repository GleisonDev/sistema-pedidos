package com.gleison.pedidos.infrastructure.api.pedido.controller.post.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CadastroItemPedidoRequestDTO(

        @NotNull(message = "O ID do produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser de pelo menos 1 item.")
        Integer quantidade
) {
}
