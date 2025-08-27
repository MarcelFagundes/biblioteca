package com.bibliotecalivrosemprestimos.adapter.input.request;

import com.bibliotecalivrosemprestimos.core.domain.model.Livro;

public record LivroRequest(
    Long id,
    String isbn,
    String titulo,
    String autor,
    Integer estoque,
    Boolean ativo
) {
    public static LivroRequest fromEntity(Livro livro) {
        return new LivroRequest(
            livro.getId(),
            livro.getIsbn(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getEstoque(),
            livro.getAtivo()
        );
    }
}