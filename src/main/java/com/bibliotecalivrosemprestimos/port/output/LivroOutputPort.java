package com.bibliotecalivrosemprestimos.port.output;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;

public interface LivroOutputPort {
    Livro criarLivro(Livro livro);
    Livro listarLivros(String titulo, Boolean ativo);
    Livro buscarLivroPorId(Long id);
    Livro atualizarLivro(Long id, LivroRequest livroRequest);
    Livro desativarLivro(Long id);
    Livro listarLivrosEmprestados();
}
