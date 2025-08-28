package com.bibliotecalivrosemprestimos.adapter.input.controller;

import java.util.List;
import com.bibliotecalivrosemprestimos.adapter.input.mapper.LivroMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.validation.CriarLivroRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroInputPort livroInputPort;

    private LivroRequest livroRequest;

    Livro entity = LivroMapper.INSTANCE.toEntity(livroRequest);

    // CREATE
    @PostMapping
    public ResponseEntity<LivroRequest> criarLivro(@RequestBody CriarLivroRequest request) {
        LivroRequest livro = livroInputPort.criarLivro(request);
        return ResponseEntity.status(201).body(livro);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<LivroRequest>> listarLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean ativo) {
        List<LivroRequest> livros = livroInputPort.listarLivros(titulo, ativo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroRequest> buscarLivroPorId(@PathVariable Long id) {
        LivroRequest livro = livroInputPort.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<LivroRequest> atualizarLivro(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarLivroRequest request) {
        LivroRequest livro = livroInputPort.atualizarLivro(id, request);
        return ResponseEntity.ok(livro);
    }

    // DELETE (lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarLivro(@PathVariable Long id) {
        livroInputPort.desativarLivro(id);
        return ResponseEntity.noContent().build();
    }

   // Relatório de livros emprestados
    @GetMapping("/emprestados")
    public ResponseEntity<List<LivroComEmprestimoRequest>> listarLivrosEmprestados() {
        List<LivroComEmprestimoRequest> livros = livroInputPort.listarLivrosEmprestados();
        return ResponseEntity.ok(livros);
    }
}