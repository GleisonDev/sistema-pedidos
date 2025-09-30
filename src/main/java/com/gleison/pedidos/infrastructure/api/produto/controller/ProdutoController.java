package com.gleison.pedidos.infrastructure.api.produto.controller;

import com.gleison.pedidos.application.contracts.ProdutoContracts;
import com.gleison.pedidos.infrastructure.api.produto.ProdutoApi;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.AtualizaProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.CadastroProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.response.ProdutoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProdutoController implements ProdutoApi {

    private final ProdutoContracts contracts;

    @Override
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(CadastroProdutoRequestDTO request) {
        ProdutoResponseDTO response = contracts.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(Long id) {
        ProdutoResponseDTO response = contracts.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        List<ProdutoResponseDTO> response = contracts.listarTodos();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(Long id, AtualizaProdutoRequestDTO request) {
        ProdutoResponseDTO response = contracts.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void desativarProduto(Long id) {
        contracts.deletar(id);
    }
}