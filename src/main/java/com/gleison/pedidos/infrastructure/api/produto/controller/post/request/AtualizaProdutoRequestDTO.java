package com.gleison.pedidos.infrastructure.api.produto.controller.post.request;

import java.math.BigDecimal;

public record AtualizaProdutoRequestDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoqueAtual,
        Boolean ativo

) {
}
