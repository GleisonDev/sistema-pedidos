package com.gleison.pedidos.infrastructure.api.cliente.controller.post.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record AtualizaClienteRequestDTO(

        String nome,

        @Email(message = "Formato de e-mail inválido.")
        String email,

        @CPF(message = "CPF inválido.")
        String cpf
) {
}
