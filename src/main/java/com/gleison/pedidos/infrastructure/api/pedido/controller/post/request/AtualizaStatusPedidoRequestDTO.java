package com.gleison.pedidos.infrastructure.api.pedido.controller.post.request;

import com.gleison.pedidos.domain.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record AtualizaStatusPedidoRequestDTO(

        @NotNull(message = "O novo status do pedido é obrigatório.")
        StatusPedido novoStatus
) {
}