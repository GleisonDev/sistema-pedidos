package com.gleison.pedidos.infrastructure.api.produto;

import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.AtualizaProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.request.CadastroProdutoRequestDTO;
import com.gleison.pedidos.infrastructure.api.produto.controller.post.response.ProdutoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Produtos",
        description = "Endpoints responsáveis pela gestão do catálogo de produtos."
)
@RequestMapping("/produtos")
public interface ProdutoApi {

    @PostMapping
    @Operation(
            summary = "Cadastrar um produto",
            description = "Registra um novo produto no catálogo.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida - dados incorretos")
            }
    )
    ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid CadastroProdutoRequestDTO request);

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto específico pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id);

    @GetMapping
    @Operation(
            summary = "Listar produtos",
            description = "Retorna a lista de todos os produtos do catálogo."
    )
    ResponseEntity<List<ProdutoResponseDTO>> listarProdutos();

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar um produto",
            description = "Atualiza somente os campos fornecidos (PATCH).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id,
                                                        @RequestBody @Valid AtualizaProdutoRequestDTO request);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Inativar um produto (Soft Delete)",
            description = "Define a flag 'ativo' como false para um produto, removendo-o da visualização. Resposta 204 No Content.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto inativado com sucesso")
            }
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void desativarProduto(@PathVariable Long id);
}