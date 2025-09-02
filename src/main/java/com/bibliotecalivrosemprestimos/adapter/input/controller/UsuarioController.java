package com.bibliotecalivrosemprestimos.adapter.input.controller;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.port.input.EmprestimoInputPort;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioInputPort usuarioInputPort;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioInputPort usuarioInputPort) {
        this.usuarioInputPort =  usuarioInputPort;
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

    // Relatório de usuários com empréstimos
    @GetMapping("/com-emprestimos")
    public ResponseEntity<List<UsuarioComEmprestimosRequest>> listarUsuariosComEmprestimos() {
        List<UsuarioComEmprestimosRequest> usuarios = usuarioInputPort.listarUsuariosComEmprestimos();
        return ResponseEntity.ok(usuarios);
    }
}