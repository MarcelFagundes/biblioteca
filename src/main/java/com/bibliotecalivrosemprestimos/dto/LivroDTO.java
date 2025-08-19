package com.bibliotecalivrosemprestimos.dto;

import com.bibliotecalivrosemprestimos.entity.LivroEntity;

public record LivroDTO(
    Long id,
    String isbn,
    String titulo,
    String autor,
    Integer estoque,
    Boolean ativo
) {
    public static LivroDTO fromEntity(LivroEntity livro) {
        return new LivroDTO(
            livro.getId(),
            livro.getIsbn(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getEstoque(),
            livro.getAtivo()
        );
    }
}