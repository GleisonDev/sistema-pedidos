package com.gleison.pedidos.infrastructure.repository;

import com.gleison.pedidos.domain.gateway.PedidoGateway;
import com.gleison.pedidos.domain.model.Pedido;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, PedidoGateway {

    Optional<Pedido> findByIdAndAtivoTrue(Long id);
    List<Pedido> findAllByAtivoTrue();

    @Override
    default Optional<Pedido> findById(Long id) {
        return findByIdAndAtivoTrue(id);
    }

    @Override
    default List<Pedido> findAll() {
        return findAllByAtivoTrue();
    }
}