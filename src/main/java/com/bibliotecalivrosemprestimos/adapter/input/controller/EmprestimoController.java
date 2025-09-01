package com.bibliotecalivrosemprestimos.adapter.input.controller;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.EmprestimoMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.port.input.EmprestimoInputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.DevolverLivroRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoInputPort emprestimoInputPort;

    private EmprestimoRequest emprestimoRequest;


    // CREATE
    @PostMapping
    public ResponseEntity<EmprestimoRequest> criarEmprestimo(@Valid @RequestBody CriarEmprestimoRequest request) {
        EmprestimoRequest emprestimo = emprestimoInputPort.criarEmprestimo(request);
        return ResponseEntity.status(201).body(emprestimo);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<EmprestimoRequest>> listarEmprestimos(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Boolean ativo) {
        List<EmprestimoRequest> emprestimos = emprestimoInputPort.listarEmprestimos(usuarioId, ativo);
        return ResponseEntity.ok(emprestimos);
    }

    // UPDATE (devolução)
    @PutMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoRequest> registrarDevolucao(
            @PathVariable Long id,
            @Valid @RequestBody DevolverLivroRequest request) {
        EmprestimoRequest emprestimo = emprestimoInputPort.registrarDevolucao(id);
        return ResponseEntity.ok(emprestimo);
    }

    // Cálculo de multa
    @GetMapping("/{id}/multa")
    public ResponseEntity<MultaRequest> calcularMulta(@PathVariable Long id) {
        MultaRequest multa = emprestimoInputPort.calcularMulta(id);
        return ResponseEntity.ok(multa);
    }
}