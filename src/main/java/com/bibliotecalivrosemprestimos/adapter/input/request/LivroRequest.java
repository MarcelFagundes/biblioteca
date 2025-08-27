package com.bibliotecalivrosemprestimos.adapter.input.request;

import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;

public record LivroRequest(
    Long id,
    String isbn,
    String titulo,
    String autor,
    Integer estoque,
    Boolean ativo
) {
    public static LivroRequest fromEntity(LivroEntity livro) {
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