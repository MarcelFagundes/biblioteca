package com.bibliotecalivrosemprestimos.adapter.input.controller;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioInputPort usuarioInputPort;

    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioInputPort usuarioInputPort, UsuarioMapper usuarioMapper) {
        this.usuarioInputPort = usuarioInputPort;
        this.usuarioMapper = usuarioMapper;
    }


    // CREATE
    @PostMapping
    public ResponseEntity<UsuarioRequest> criarUsuario(@Valid @RequestBody CriarUsuarioRequest request) {
        CriarUsuarioRequest criarUsuarioRequest = usuarioMapper.toRequest(request);
        UsuarioRequest usuarioNovo = usuarioInputPort.criarUsuario(request);
        return ResponseEntity.status(201).body(usuarioNovo);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRequest> buscarUsuarioPorId(@PathVariable Long id) {
        UsuarioRequest usuario = usuarioInputPort.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<UsuarioRequest>> listarLivros() {
        List<UsuarioRequest> usuario = usuarioInputPort.listarTodos();
        return ResponseEntity.ok(usuario);
    }

    // Relatório de usuários com empréstimos
    @GetMapping("/com-emprestimos")
    public ResponseEntity<List<UsuarioRequest>> listarUsuariosComEmprestimos() {
        List<UsuarioRequest> usuarios = usuarioInputPort.listarUsuariosComEmprestimos();
        return ResponseEntity.ok(usuarios);
    }
}