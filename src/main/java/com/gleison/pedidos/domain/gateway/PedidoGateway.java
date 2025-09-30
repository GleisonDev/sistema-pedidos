package com.gleison.pedidos.domain.gateway;

import com.gleison.pedidos.domain.enums.StatusPedido;
import com.gleison.pedidos.domain.model.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoGateway {

    Pedido save(Pedido pedido);

    Optional<Pedido> findById(Long id);

    List<Pedido> findAll();


    List<Pedido> findByStatus(StatusPedido status);
}