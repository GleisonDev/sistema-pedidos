package com.gleison.pedidos.infrastructure.api.produto.controller.post.response;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoqueAtual,
        Boolean ativo

) {
}
