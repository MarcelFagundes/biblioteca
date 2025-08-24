package com.bibliotecalivrosemprestimos.adapter.input.controller;

import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.core.service.EmprestimoService;
import com.bibliotecalivrosemprestimos.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.validation.DevolverLivroRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<EmprestimoRequest> criarEmprestimo(@Valid @RequestBody CriarEmprestimoRequest request) {
        EmprestimoRequest emprestimo = emprestimoService.criarEmprestimo(request);
        return ResponseEntity.status(201).body(emprestimo);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<EmprestimoRequest>> listarEmprestimos(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Boolean ativo) {
        List<EmprestimoRequest> emprestimos = emprestimoService.listarEmprestimos(usuarioId, ativo);
        return ResponseEntity.ok(emprestimos);
    }

    // UPDATE (devolução)
    @PutMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoRequest> registrarDevolucao(
            @PathVariable Long id,
            @Valid @RequestBody DevolverLivroRequest request) {
        EmprestimoRequest emprestimo = emprestimoService.registrarDevolucao(id);
        return ResponseEntity.ok(emprestimo);
    }

    // Cálculo de multa
    @GetMapping("/{id}/multa")
    public ResponseEntity<MultaRequest> calcularMulta(@PathVariable Long id) {
        MultaRequest multa = emprestimoService.calcularMulta(id);
        return ResponseEntity.ok(multa);
    }
}