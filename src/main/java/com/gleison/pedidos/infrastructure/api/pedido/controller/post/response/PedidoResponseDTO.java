package com.gleison.pedidos.infrastructure.api.pedido.controller.post.response;

import com.gleison.pedidos.domain.enums.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        Long clienteId,
        LocalDateTime dataCriacao,
        StatusPedido status,
        BigDecimal valorTotal,
        List<ItemPedidoResponseDTO> itens
) {
}