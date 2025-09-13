package com.bibliotecalivrosemprestimos.adapter.input.controller;

import java.util.List;
import com.bibliotecalivrosemprestimos.adapter.input.mapper.LivroMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarLivroRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/livros")
public class LivroController {


    private final LivroInputPort livroInputPort;

    private final LivroMapper livroMapper;

    public LivroController(LivroInputPort livroInputPort, LivroMapper livroMapper) {
        this.livroInputPort =  livroInputPort;
        this.livroMapper = livroMapper;
    }

    // READ
    @GetMapping
    public ResponseEntity<List<LivroRequest>> listarLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean ativo) {
        List<LivroRequest> livros = livroInputPort.listarLivros(titulo, ativo);
        return ResponseEntity.ok(livros);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<LivroRequest> criarLivro(@RequestBody LivroRequest request) {
        CriarLivroRequest criarLivroRequest = livroMapper.toRequest(request);
        LivroRequest livroNovo = livroInputPort.criarLivro(criarLivroRequest);
        return ResponseEntity.status(201).body(livroNovo);
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
        AtualizarLivroRequest atualizarLivroRequest = livroMapper.toRequest(request);
        LivroRequest livroAtualizar = livroInputPort.atualizarLivro(id, atualizarLivroRequest);
        return ResponseEntity.status(201).body(livroAtualizar);
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