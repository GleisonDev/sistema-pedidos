package com.gleison.pedidos.infrastructure.api.pedido.controller;

import com.gleison.pedidos.application.contracts.PedidoContracts;
import com.gleison.pedidos.infrastructure.api.pedido.PedidoApi;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.CadastroPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.AtualizaStatusPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.response.PedidoResponseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PedidoController implements PedidoApi {

    private final PedidoContracts service;

    @Override
    public ResponseEntity<PedidoResponseDTO> cadastrar(CadastroPedidoRequestDTO request) {
        PedidoResponseDTO response = service.cadastrar(request);

        // Cria a URI do recurso criado para o header Location (201 Created)
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Override
    public ResponseEntity<PedidoResponseDTO> buscarPorId(Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Override
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(Long id, AtualizaStatusPedidoRequestDTO request) {
        PedidoResponseDTO response = service.atualizarStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void desativar(Long id) {
        service.desativar(id);
    }
}