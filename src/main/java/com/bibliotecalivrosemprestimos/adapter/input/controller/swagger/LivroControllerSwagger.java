package com.bibliotecalivrosemprestimos.adapter.input.controller.swagger;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarLivroRequest;
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

@Tag(name = "Livros", description = "API para gerenciamento de livros do acervo")
public interface LivroControllerSwagger {

    @Operation(
            summary = "Criar novo livro",
            description = "Endpoint para adicionar um novo livro ao acervo da biblioteca"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Livro criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LivroRequest.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Livro já existe (ISBN duplicado)",
                    content = @Content
            )
    })
    ResponseEntity<LivroRequest> criarLivro(@Valid @RequestBody CriarLivroRequest request);

    @Operation(
            summary = "Buscar livro por ID",
            description = "Endpoint para buscar um livro específico pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = LivroRequest.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<LivroRequest> buscarLivroPorId(
            @Parameter(description = "ID do livro", example = "1")
            @PathVariable Long id
    );

    @Operation(
            summary = "Listar todos os livros",
            description = "Endpoint para listar todos os livros do acervo com possibilidade de filtros"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de livros retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LivroRequest.class))
            )
    })
    ResponseEntity<List<LivroRequest>> listarLivros(
            @Parameter(description = "Filtrar por disponibilidade", example = "true")
            @RequestParam(required = false) Boolean disponivel,

            @Parameter(description = "Filtrar por autor", example = "Machado de Assis")
            @RequestParam(required = false) String autor,

            @Parameter(description = "Filtrar por gênero", example = "Romance")
            @RequestParam(required = false) String genero
    );

    @Operation(
            summary = "Atualizar livro",
            description = "Endpoint para atualizar os dados de um livro existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LivroRequest.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<LivroRequest> atualizarLivro(
            @Parameter(description = "ID do livro", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody CriarLivroRequest request
    );

    @Operation(
            summary = "Excluir livro",
            description = "Endpoint para excluir um livro do acervo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Livro excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Livro não pode ser excluído (possui empréstimos ativos)",
                    content = @Content
            )
    })
    ResponseEntity<Void> excluirLivro(
            @Parameter(description = "ID do livro", example = "1")
            @PathVariable Long id
    );

    @Operation(
            summary = "Buscar livros por título",
            description = "Endpoint para buscar livros por título (busca parcial case-insensitive)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de livros encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = LivroRequest.class))
            )
    })
    ResponseEntity<List<LivroRequest>> buscarLivrosPorTitulo(
            @Parameter(description = "Título ou parte do título para busca", example = "Dom")
            @RequestParam String titulo
    );

    @Operation(
            summary = "Listar livros mais emprestados",
            description = "Endpoint para listar os livros mais populares baseado no número de empréstimos"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de livros mais emprestados retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LivroRequest.class))
            )
    })
    ResponseEntity<List<LivroRequest>> listarLivrosMaisEmprestados(
            @Parameter(description = "Número máximo de livros a retornar", example = "10")
            @RequestParam(defaultValue = "10") int limite
    );
}
