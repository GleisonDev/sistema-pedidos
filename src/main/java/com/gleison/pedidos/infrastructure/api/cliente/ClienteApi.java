package com.gleison.pedidos.infrastructure.api.cliente.controller;

import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.AtualizaClienteRequestDTO;
import com.gleison.pedidos.infrastructure.api.cliente.controller.post.request.CadastroClienteRequestDTO;


import com.gleison.pedidos.infrastructure.api.cliente.controller.post.response.ClienteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.List;

@Tag(
        name = "Clientes",
        description = "Endpoints responsáveis pela gestão de clientes."
)
@RequestMapping("/clientes")
public interface ClienteApi {

    @PostMapping
    @Operation(
            summary = "Cadastrar um cliente",
            description = "Registra um novo cliente no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida (CPF/E-mail já cadastrado, ou validação falhou).")
            }
    )
    ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody @Valid CadastroClienteRequestDTO request);

    @GetMapping
    @Operation(
            summary = "Listar clientes",
            description = "Retorna a lista de todos os clientes ativos."
    )
    ResponseEntity<List<ClienteResponseDTO>> listarTodos();

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna um cliente específico pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado ou inativo.")
            }
    )
    ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id);

    @GetMapping("/cpf/{cpf}")
    @Operation(
            summary = "Buscar cliente por CPF",
            description = "Retorna um cliente específico pelo CPF.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado ou inativo.")
            }
    )
    ResponseEntity<ClienteResponseDTO> buscarPorCpf(@PathVariable String cpf);

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar um cliente (Parcial)",
            description = "Atualiza somente os campos fornecidos (PATCH).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos (CPF/E-mail já cadastrado, ou formato inválido).")
            }
    )
    ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id,
                                                 @RequestBody @Valid AtualizaClienteRequestDTO request);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Inativar um cliente (Soft Delete)",
            description = "Define a flag 'ativo' como false. Resposta 204 No Content.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente inativado com sucesso")
            }
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void desativar(@PathVariable Long id);
}