package com.gleison.pedidos.domain.gateway;

import com.gleison.pedidos.domain.model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {

    Produto save(Produto produto);
    Optional<Produto> findById(Long id);
    Optional<Produto> findByIdAndAtivoTrue(Long id);
    List<Produto> findAllByAtivoTrue();
}