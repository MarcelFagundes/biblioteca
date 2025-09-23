package com.bibliotecalivrosemprestimos.adapter.input.controller;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.EmprestimoMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.port.input.EmprestimoInputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.DevolverLivroRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoInputPort emprestimoInputPort;

    private final EmprestimoMapper emprestimoMapper;

    public EmprestimoController(EmprestimoInputPort emprestimoInputPort, EmprestimoMapper emprestimoMapper) {
        this.emprestimoInputPort =  emprestimoInputPort;
        this.emprestimoMapper = emprestimoMapper;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<EmprestimoRequest> criarEmprestimo(@Valid @RequestBody CriarEmprestimoRequest request) {
        CriarEmprestimoRequest criarEmprestimo = emprestimoMapper.toRequest(request);
        EmprestimoRequest emprestimoNovo = emprestimoInputPort.criarEmprestimo(criarEmprestimo);
        return ResponseEntity.status(201).body(emprestimoNovo);
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
        DevolverLivroRequest devolverLivroRequest = emprestimoMapper.toRequest(request);
        EmprestimoRequest emprestimoDevolver = emprestimoInputPort.registrarDevolucao(id, devolverLivroRequest);
        return ResponseEntity.status(201).body(emprestimoDevolver);
    }

    // Cálculo de multa
    @GetMapping("/{id}/multa")
    public ResponseEntity<MultaRequest> calcularMulta(@PathVariable Long id) {
        MultaRequest multa = emprestimoInputPort.calcularMulta(id);
        return ResponseEntity.ok(multa);
    }
}