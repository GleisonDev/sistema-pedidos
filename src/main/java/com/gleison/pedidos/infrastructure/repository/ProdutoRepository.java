package com.gleison.pedidos.infrastructure.repository;

import com.gleison.pedidos.domain.gateway.ProdutoGateway;
import com.gleison.pedidos.domain.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoGateway {

    Optional<Produto> findByIdAndAtivoTrue(Long id);

    List<Produto> findAllByAtivoTrue();

}