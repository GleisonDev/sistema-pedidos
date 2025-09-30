package com.gleison.pedidos.infrastructure.api.cliente.controller.post.response;

public record ClienteResponseDTO(

        Long id,
        String nome,
        String email,
        String cpf,
        Boolean ativo
) {
}
