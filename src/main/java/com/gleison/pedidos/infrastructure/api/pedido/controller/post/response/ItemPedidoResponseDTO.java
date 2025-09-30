package com.gleison.pedidos.infrastructure.api.pedido.controller.post.response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}