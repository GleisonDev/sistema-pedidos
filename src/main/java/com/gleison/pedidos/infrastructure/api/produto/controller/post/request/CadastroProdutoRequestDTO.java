package com.gleison.pedidos.infrastructure.api.produto.controller.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CadastroProdutoRequestDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        String descricao,

        @NotNull(message = "O preço é obrigatório.")
        @Positive(message = "O preço deve ser positivo.")
        BigDecimal preco,

        @NotNull(message = "O estoque é obrigatório.")
        @Positive(message = "O estoque deve ser maior ou igual a zero.")
        Integer estoqueAtual
) {
}
