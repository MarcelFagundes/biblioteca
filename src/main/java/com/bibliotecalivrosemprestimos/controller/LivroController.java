package com.bibliotecalivrosemprestimos.controller;

import java.util.List;

import com.bibliotecalivrosemprestimos.dto.LivroComEmprestimoDTO;
import com.bibliotecalivrosemprestimos.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.validation.CriarLivroRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bibliotecalivrosemprestimos.dto.LivroDTO;
import com.bibliotecalivrosemprestimos.service.LivroService;
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
    public ResponseEntity<LivroDTO> criarLivro(@RequestBody CriarLivroRequest request) {
        LivroDTO livro = livroService.criarLivro(request);
        return ResponseEntity.status(201).body(livro);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean ativo) {
        List<LivroDTO> livros = livroService.listarLivros(titulo, ativo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id) {
        LivroDTO livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarLivroRequest request) {
        LivroDTO livro = livroService.atualizarLivro(id, request);
        return ResponseEntity.ok(livro);
    }

    // DELETE (lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarLivro(@PathVariable Long id) {
        livroService.desativarLivro(id);
        return ResponseEntity.noContent().build();
    }

//   // Relatório de livros emprestados
//    @GetMapping("/emprestados")
//    public ResponseEntity<List<LivroComEmprestimoDTO>> listarLivrosEmprestados() {
//        List<LivroComEmprestimoDTO> livros = livroService.listarLivrosEmprestados();
//        return ResponseEntity.ok(livros);
//    }
}