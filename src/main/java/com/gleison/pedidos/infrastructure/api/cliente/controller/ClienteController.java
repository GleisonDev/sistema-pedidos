package com.gleison.pedidos.infrastructure.api.cliente.controller;

import com.gleison.pedidos.application.contracts.ClienteContracts;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.AtualizaClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.CadastroClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.response.ClienteResponseDTO;
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
public class ClienteController implements com.gleison.pedidos.infrastructure.api.cliente.controller.ClienteApi {

    private final ClienteContracts contracts;

    @Override
    public ResponseEntity<ClienteResponseDTO> cadastrar(CadastroClienteRequestDTO request) {
        ClienteResponseDTO response = contracts.cadastrar(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        List<ClienteResponseDTO> response = contracts.listarTodos();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ClienteResponseDTO> buscarPorId(Long id) {
        ClienteResponseDTO response = contracts.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ClienteResponseDTO> buscarPorCpf(String cpf) {
        ClienteResponseDTO response = contracts.buscarPorCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ClienteResponseDTO> atualizar(Long id, AtualizaClienteRequestDTO request) {
        ClienteResponseDTO response = contracts.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void desativar(Long id) {
        contracts.desativar(id);
    }
}