package com.gleison.pedidos.application.contracts;

import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.AtualizaClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.CadastroClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.response.ClienteResponseDTO;


import java.util.List;

public interface ClienteContracts {

    ClienteResponseDTO cadastrar(CadastroClienteRequestDTO request);

    ClienteResponseDTO buscarPorId(Long id);

    List<ClienteResponseDTO> listarTodos();

    ClienteResponseDTO atualizar(Long id, AtualizaClienteRequestDTO request);

    void desativar(Long id);

    ClienteResponseDTO buscarPorCpf(String cpf);
}