package com.gleison.pedidos.infrastructure.repository;

import com.gleison.pedidos.domain.gateway.ClienteGateway;
import com.gleison.pedidos.domain.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends CustomJpaRepository<Cliente, Long>, ClienteGateway {

    Optional<Cliente> findByIdAndAtivoTrue(Long id);
    List<Cliente> findAllByAtivoTrue();

    Optional<Cliente> findByCpfAndAtivoTrue(String cpf);
    Optional<Cliente> findByEmailAndAtivoTrue(String email);

    @Override
    default Optional<Cliente> findById(Long id) {
        return findByIdAndAtivoTrue(id);
    }

    @Override
    default List<Cliente> findAll() {
        return findAllByAtivoTrue();
    }
}