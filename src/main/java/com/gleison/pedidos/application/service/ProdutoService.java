package com.gleison.pedidos.application.service;

import com.gleison.pedidos.application.contracts.ProdutoContracts;
import com.gleison.pedidos.application.exception.ResourceNotFoundException;
import com.gleison.pedidos.domain.gateway.ProdutoGateway;
import com.gleison.pedidos.domain.model.Produto;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.AtualizaProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.mapper.ProdutoMapper;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.CadastroProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.response.ProdutoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService implements ProdutoContracts {

    private final ProdutoGateway gateway;
    private final ProdutoMapper mapper;

    @Override
    @Transactional
    public ProdutoResponseDTO cadastrar(CadastroProdutoRequestDTO request) {
        Produto produto = mapper.toEntity(request);

        produto = gateway.save(produto);

        return mapper.toResponse(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = gateway.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        return mapper.toResponse(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = gateway.findAllByAtivoTrue();

        return produtos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProdutoResponseDTO atualizar(Long id, AtualizaProdutoRequestDTO request) {
        Produto produtoAtual = gateway.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para atualização com ID: " + id));

        mapper.updateEntityFromDto(request, produtoAtual);

        produtoAtual = gateway.save(produtoAtual);

        return mapper.toResponse(produtoAtual);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Produto produto = gateway.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para inativação com ID: " + id));

        produto.setAtivo(false);

        gateway.save(produto);
    }
}