package com.bibliotecalivrosemprestimos.adapter.input.request;

public record LivroRequest(
    Long id,
    String isbn,
    String titulo,
    String autor,
    Integer estoque,
    Boolean ativo
) {}