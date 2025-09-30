package com.gleison.pedidos.infrastructure.api.produto.mapper;

import com.gleison.pedidos.domain.model.Produto;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.AtualizaProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.CadastroProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.response.ProdutoResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProdutoMapper {

    ProdutoResponseDTO toResponse(Produto produto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    Produto toEntity(CadastroProdutoRequestDTO request);


    void updateEntityFromDto(AtualizaProdutoRequestDTO request, @MappingTarget Produto produto);
}