package com.bibliotecalivrosemprestimos.adapter.input.controller.swagger;

import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
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

import java.util.List;

@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public interface UsuarioControllerSwagger {

    @Operation(
            summary = "Criar novo usuário",
            description = "Endpoint para criar um novo usuário no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioRequest.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário já existe (email ou CPF duplicado)",
                    content = @Content
            )
    })
    ResponseEntity<UsuarioRequest> criarUsuario(@Valid @RequestBody CriarUsuarioRequest request);

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Endpoint para buscar um usuário específico pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioRequest.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<UsuarioRequest> buscarUsuarioPorId(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id
    );

    @Operation(
            summary = "Listar todos os usuários",
            description = "Endpoint para listar todos os usuários cadastrados no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioRequest.class))
            )
    })
    ResponseEntity<List<UsuarioRequest>> listarUsuarios();

    @Operation(
            summary = "Listar usuários com empréstimos ativos",
            description = "Endpoint para listar usuários que possuem empréstimos ativos no momento"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários com empréstimos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioRequest.class))
            )
    })
    ResponseEntity<List<UsuarioComEmprestimosRequest>> listarUsuariosComEmprestimos();
}