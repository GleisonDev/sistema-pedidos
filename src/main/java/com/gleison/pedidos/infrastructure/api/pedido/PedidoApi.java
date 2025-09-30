package com.gleison.pedidos.infrastructure.api.pedido;

import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.CadastroPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.request.AtualizaStatusPedidoRequestDTO;
import com.gleison.pedidos.infrastructure.api.pedido.controller.post.response.PedidoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Tag(
        name = "Pedidos",
        description = "Endpoints responsáveis pela gestão de pedidos, incluindo criação, busca e alteração de status."
)
@RequestMapping("/pedidos")
public interface PedidoApi {

    @PostMapping
    @Operation(
            summary = "Cadastrar um pedido",
            description = "Cria um novo pedido, verificando estoque e congelando preços.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Cliente ou Produto(s) não encontrado(s)."),
                    @ApiResponse(responseCode = "400", description = "Estoque insuficiente ou requisição inválida.")
            }
    )
    ResponseEntity<PedidoResponseDTO> cadastrar(@RequestBody @Valid CadastroPedidoRequestDTO request);

    @GetMapping
    @Operation(
            summary = "Listar pedidos",
            description = "Retorna a lista de todos os pedidos ativos."
    )
    ResponseEntity<List<PedidoResponseDTO>> listarTodos();

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar pedido por ID",
            description = "Retorna um pedido específico pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado ou inativo.")
            }
    )
    ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id);

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Atualizar Status do Pedido",
            description = "Altera o status de um pedido (ex: PENDENTE -> PAGO).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado."),
                    @ApiResponse(responseCode = "400", description = "Transição de status inválida (ex: alterar um pedido já CANCELADO).")
            }
    )
    ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id,
                                                      @RequestBody @Valid AtualizaStatusPedidoRequestDTO request);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Inativar um pedido (Soft Delete)",
            description = "Define a flag 'ativo' como false para um pedido. Resposta 204 No Content.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido inativado com sucesso")
            }
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void desativar(@PathVariable Long id);
}