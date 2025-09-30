package com.gleison.pedidos.infrastructure.api.pedido.mapper;

import com.gleison.pedidos.domain.model.Cliente;
import com.gleison.pedidos.domain.model.ItemPedido;
import com.gleison.pedidos.domain.model.Pedido;
import com.gleison.pedidos.domain.model.Produto;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.CadastroItemPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.CadastroPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.response.ItemPedidoResponseDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.response.PedidoResponseDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "status", constant = "PENDENTE")
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "cliente", source = "clienteId")
    Pedido toEntity(CadastroPedidoRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "precoUnitario", ignore = true)
    @Mapping(target = "produto", source = "produtoId")
    ItemPedido toItemEntity(CadastroItemPedidoRequestDTO itemDto);

    default Cliente mapIdToCliente(Long id) {
        if (id == null) return null;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }

    default Produto mapIdToProduto(Long id) {
        if (id == null) return null;
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }

    @Mapping(target = "clienteId", source = "cliente.id")
    PedidoResponseDTO toResponse(Pedido pedido);

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "nomeProduto", source = "produto.nome")
    @Mapping(target = "subtotal", expression = "java(entity.getPrecoUnitario().multiply(new java.math.BigDecimal(entity.getQuantidade())))")
    ItemPedidoResponseDTO toItemResponse(ItemPedido entity);
}