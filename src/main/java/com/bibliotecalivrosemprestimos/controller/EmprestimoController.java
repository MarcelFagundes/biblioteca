package com.bibliotecalivrosemprestimos.controller;

import com.bibliotecalivrosemprestimos.dto.EmprestimoDTO;
import com.bibliotecalivrosemprestimos.dto.MultaDTO;
import com.bibliotecalivrosemprestimos.service.EmprestimoService;
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
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(@Valid @RequestBody CriarEmprestimoRequest request) {
        EmprestimoDTO emprestimo = emprestimoService.criarEmprestimo(request);
        return ResponseEntity.status(201).body(emprestimo);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Boolean ativo) {
        List<EmprestimoDTO> emprestimos = emprestimoService.listarEmprestimos(usuarioId, ativo);
        return ResponseEntity.ok(emprestimos);
    }

    // UPDATE (devolução)
    @PutMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoDTO> registrarDevolucao(
            @PathVariable Long id,
            @Valid @RequestBody DevolverLivroRequest request) {
        EmprestimoDTO emprestimo = emprestimoService.registrarDevolucao(id);
        return ResponseEntity.ok(emprestimo);
    }

    // Cálculo de multa
    @GetMapping("/{id}/multa")
    public ResponseEntity<MultaDTO> calcularMulta(@PathVariable Long id) {
        MultaDTO multa = emprestimoService.calcularMulta(id);
        return ResponseEntity.ok(multa);
    }
}