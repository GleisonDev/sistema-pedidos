package com.gleison.pedidos.application.service;

import com.gleison.pedidos.application.contracts.PedidoContracts;
import com.gleison.pedidos.application.exception.BusinessValidationException;
import com.gleison.pedidos.application.exception.ResourceNotFoundException;
import com.gleison.pedidos.domain.gateway.ClienteGateway;
import com.gleison.pedidos.domain.gateway.PedidoGateway;
import com.gleison.pedidos.domain.gateway.ProdutoGateway;
import com.gleison.pedidos.domain.model.Cliente;
import com.gleison.pedidos.domain.model.ItemPedido;
import com.gleison.pedidos.domain.model.Pedido;
import com.gleison.pedidos.domain.model.Produto;
import com.gleison.pedidos.domain.enums.StatusPedido;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.CadastroItemPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.CadastroPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.AtualizaStatusPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.response.PedidoResponseDTO;
import com.gleison.pedidos.infrastructure.api.pedido.mapper.PedidoMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService implements PedidoContracts {

    private final PedidoGateway pedidoGateway;
    private final ProdutoGateway produtoGateway;
    private final ClienteGateway clienteGateway;
    private final PedidoMapper mapper;

    private Pedido buscarPedidoAtivo(Long id) {
        return pedidoGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
    }

    @Override
    @Transactional
    public PedidoResponseDTO cadastrar(CadastroPedidoRequestDTO request) {

        Cliente cliente = clienteGateway.findById(request.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado.")); // 404

        Pedido pedido = mapper.toEntity(request);

        pedido.setCliente(cliente);

        pedido.setItens(new ArrayList<>());

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (CadastroItemPedidoRequestDTO itemDto : request.itens()) {
            Produto produto = produtoGateway.findById(itemDto.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + itemDto.produtoId() + " não encontrado."));

            if (produto.getEstoqueAtual() < itemDto.quantidade()) {
                throw new BusinessValidationException("Estoque insuficiente para o produto " + produto.getNome());
            }

            ItemPedido itemPedido = mapper.toItemEntity(itemDto);
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.setProduto(produto);

            pedido.adicionarItem(itemPedido);
            valorTotal = valorTotal.add(produto.getPreco().multiply(new BigDecimal(itemDto.quantidade())));

            produto.setEstoqueAtual(produto.getEstoqueAtual() - itemDto.quantidade());
            produtoGateway.save(produto);
        }

        pedido.setValorTotal(valorTotal);
        pedido = pedidoGateway.save(pedido);

        return mapper.toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoGateway.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = buscarPedidoAtivo(id);
        return mapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, AtualizaStatusPedidoRequestDTO request) {
        Pedido pedido = buscarPedidoAtivo(id);
        StatusPedido novoStatus = request.novoStatus();

        if (pedido.getStatus().equals(StatusPedido.CANCELADO) || pedido.getStatus().equals(StatusPedido.FINALIZADO)) {
            throw new BusinessValidationException("Não é possível alterar o status de um pedido que está " + pedido.getStatus());
        }

        if (novoStatus.equals(StatusPedido.CANCELADO)) {

            for (ItemPedido item : pedido.getItens()) {

                Produto produto = produtoGateway.findById(item.getProduto().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Produto do item " + item.getProduto().getId() + " não encontrado para devolução de estoque."));

                int novaQuantidade = produto.getEstoqueAtual() + item.getQuantidade();
                produto.setEstoqueAtual(novaQuantidade);

                produtoGateway.save(produto);
            }
        }

        pedido.setStatus(novoStatus);

        pedido = pedidoGateway.save(pedido);
        return mapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public void desativar(Long id) {
        Pedido pedido = buscarPedidoAtivo(id);

        pedido.setAtivo(false);
        pedidoGateway.save(pedido);
    }
}