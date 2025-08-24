package com.bibliotecalivrosemprestimos.adapter.input.controller;

import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.core.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<UsuarioRequest> criarUsuario(@Valid @RequestBody CriarUsuarioRequest request) {
        UsuarioRequest usuario = usuarioService.criarUsuario(request);
        return ResponseEntity.status(201).body(usuario);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRequest> buscarUsuarioPorId(@PathVariable Long id) {
        UsuarioRequest usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // Relatório de usuários com empréstimos
    @GetMapping("/com-emprestimos")
    public ResponseEntity<List<UsuarioComEmprestimosRequest>> listarUsuariosComEmprestimos() {
        List<UsuarioComEmprestimosRequest> usuarios = usuarioService.listarUsuariosComEmprestimos();
        return ResponseEntity.ok(usuarios);
    }
}