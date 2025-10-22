package com.bibliotecalivrosemprestimos.adapter.input.controller.swagger;

import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.DevolverLivroRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Empréstimos", description = "API para gerenciamento de empréstimos de livros")
public interface EmprestimoControllerSwagger {

    @Operation(
            summary = "Criar novo empréstimo",
            description = "Endpoint para criar um novo empréstimo de livro para um usuário"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Empréstimo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EmprestimoRequest.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou livro não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Livro já emprestado ou usuário com pendências",
                    content = @Content
            )
    })
    ResponseEntity<EmprestimoRequest> criarEmprestimo(@Valid @RequestBody CriarEmprestimoRequest request);

    @Operation(
            summary = "Listar empréstimos",
            description = "Endpoint para listar empréstimos com possibilidade de filtros opcionais"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de empréstimos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = EmprestimoRequest.class))
            )
    })
    ResponseEntity<List<EmprestimoRequest>> listarEmprestimos(
            @Parameter(description = "ID do usuário para filtrar empréstimos", example = "1")
            @RequestParam(required = false) Long usuarioId,

            @Parameter(description = "Status do empréstimo (true para ativos, false para finalizados)", example = "true")
            @RequestParam(required = false) Boolean ativo
    );

    @Operation(
            summary = "Registrar devolução",
            description = "Endpoint para registrar a devolução de um livro emprestado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Devolução registrada com sucesso",
                    content = @Content(schema = @Schema(implementation = EmprestimoRequest.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de devolução inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Empréstimo não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Devolução já registrada anteriormente",
                    content = @Content
            )
    })
    ResponseEntity<List<EmprestimoRequest>> registrarDevolucao(
            @Parameter(description = "ID do empréstimo", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody DevolverLivroRequest request
    );

    @Operation(
            summary = "Calcular multa",
            description = "Endpoint para calcular multa por atraso na devolução do livro"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cálculo de multa realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = MultaRequest.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Empréstimo não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<MultaRequest> calcularMulta(
            @Parameter(description = "ID do empréstimo", example = "1")
            @PathVariable Long id
    );
}
