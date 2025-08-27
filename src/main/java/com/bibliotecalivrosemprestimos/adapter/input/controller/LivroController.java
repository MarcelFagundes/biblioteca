package com.bibliotecalivrosemprestimos.adapter.input.controller;

import java.util.List;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.validation.CriarLivroRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.core.service.LivroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<LivroRequest> criarLivro(@RequestBody CriarLivroRequest request) {
        LivroRequest livro = livroService.criarLivro(request);
        return ResponseEntity.status(201).body(livro);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<LivroRequest>> listarLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean ativo) {
        List<LivroRequest> livros = livroService.listarLivros(titulo, ativo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroRequest> buscarLivroPorId(@PathVariable Long id) {
        LivroRequest livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<LivroRequest> atualizarLivro(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarLivroRequest request) {
        LivroRequest livro = livroService.atualizarLivro(id, request);
        return ResponseEntity.ok(livro);
    }

    // DELETE (lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarLivro(@PathVariable Long id) {
        livroService.desativarLivro(id);
        return ResponseEntity.noContent().build();
    }

   // Relatório de livros emprestados
    @GetMapping("/emprestados")
    public ResponseEntity<List<LivroComEmprestimoRequest>> listarLivrosEmprestados() {
        List<LivroComEmprestimoRequest> livros = livroService.listarLivrosEmprestados();
        return ResponseEntity.ok(livros);
    }
}