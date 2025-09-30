package com.gleison.pedidos.domain.gateway;

import com.gleison.pedidos.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteGateway {

    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
}
