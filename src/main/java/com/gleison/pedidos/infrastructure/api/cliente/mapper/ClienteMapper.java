package com.gleison.pedidos.infrastructure.api.cliente.mapper;

import com.gleison.pedidos.domain.model.Cliente;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.AtualizaClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.CadastroClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.response.ClienteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClienteMapper {

    ClienteResponseDTO toResponse(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    Cliente toEntity(CadastroClienteRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    void updateEntityFromDto(AtualizaClienteRequestDTO request, @MappingTarget Cliente cliente);
}