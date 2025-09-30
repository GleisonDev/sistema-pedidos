package com.gleison.pedidos.application.contracts;

import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.CadastroPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.AtualizaStatusPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.response.PedidoResponseDTO;

import java.util.List;

public interface PedidoContracts {

    PedidoResponseDTO cadastrar(CadastroPedidoRequestDTO request);

    List<PedidoResponseDTO> listarTodos();

    PedidoResponseDTO buscarPorId(Long id);

    PedidoResponseDTO atualizarStatus(Long id, AtualizaStatusPedidoRequestDTO request);

    void desativar(Long id);
}