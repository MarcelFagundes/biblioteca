package com.bibliotecalivrosemprestimos.controller;

import com.bibliotecalivrosemprestimos.dto.UsuarioComEmprestimosDTO;
import com.bibliotecalivrosemprestimos.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.dto.UsuarioDTO;
import com.bibliotecalivrosemprestimos.service.UsuarioService;
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
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody CriarUsuarioRequest request) {
        UsuarioDTO usuario = usuarioService.criarUsuario(request);
        return ResponseEntity.status(201).body(usuario);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // Relatório de usuários com empréstimos
    @GetMapping("/com-emprestimos")
    public ResponseEntity<List<UsuarioComEmprestimosDTO>> listarUsuariosComEmprestimos() {
        List<UsuarioComEmprestimosDTO> usuarios = usuarioService.listarUsuariosComEmprestimos();
        return ResponseEntity.ok(usuarios);
    }
}