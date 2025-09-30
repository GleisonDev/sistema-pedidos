package com.gleison.pedidos.application.contracts;

import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.AtualizaProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.CadastroProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.response.ProdutoResponseDTO;
import java.util.List;

public interface ProdutoContracts {

    ProdutoResponseDTO cadastrar(CadastroProdutoRequestDTO request);

    ProdutoResponseDTO buscarPorId(Long id);

    List<ProdutoResponseDTO> listarTodos();

    ProdutoResponseDTO atualizar(Long id, AtualizaProdutoRequestDTO request);

    void deletar(Long id);
}