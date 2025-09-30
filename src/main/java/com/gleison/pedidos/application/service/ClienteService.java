package com.gleison.pedidos.application.service;

import com.gleison.pedidos.application.contracts.ClienteContracts;
import com.gleison.pedidos.application.exception.BusinessValidationException;
import com.gleison.pedidos.application.exception.ResourceNotFoundException;
import com.gleison.pedidos.domain.gateway.ClienteGateway;
import com.gleison.pedidos.domain.model.Cliente;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.AtualizaClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.CadastroClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.response.ClienteResponseDTO;
import com.gleison.pedidos.infrastructure.api.cliente.mapper.ClienteMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteContracts {

    private final ClienteGateway gateway;
    private final ClienteMapper mapper;

    private void validarUnicidade(String cpf, String email) {
        if (gateway.findByCpf(cpf).isPresent()) {
            throw new BusinessValidationException("CPF já cadastrado.");
        }
        if (gateway.findByEmail(email).isPresent()) {
            throw new BusinessValidationException("E-mail já cadastrado.");
        }
    }

    private Cliente buscarClienteAtivo(Long id) {
        return gateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }

    @Override
    @Transactional
    public ClienteResponseDTO cadastrar(CadastroClienteRequestDTO request) {
        validarUnicidade(request.cpf(), request.email());

        Cliente cliente = mapper.toEntity(request);
        cliente = gateway.save(cliente);

        return mapper.toResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = buscarClienteAtivo(id);
        return mapper.toResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return gateway.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteResponseDTO atualizar(Long id, AtualizaClienteRequestDTO request) {
        Cliente clienteAtual = buscarClienteAtivo(id);

        mapper.updateEntityFromDto(request, clienteAtual);

        if (request.cpf() != null && !request.cpf().equals(clienteAtual.getCpf())) {
            if (gateway.findByCpf(request.cpf()).isPresent()) {
                throw new BusinessValidationException("Novo CPF já cadastrado.");
            }
        }
        if (request.email() != null && !request.email().equals(clienteAtual.getEmail())) {
            if (gateway.findByEmail(request.email()).isPresent()) {
                throw new BusinessValidationException("Novo E-mail já cadastrado.");
            }
        }

        clienteAtual = gateway.save(clienteAtual);
        return mapper.toResponse(clienteAtual);
    }

    @Override
    @Transactional
    public void desativar(Long id) {
        Cliente cliente = buscarClienteAtivo(id);

        cliente.setAtivo(false);
        gateway.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorCpf(String cpf) {
        Cliente cliente = gateway.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com CPF: " + cpf));

        return mapper.toResponse(cliente);
    }
}