package com.bibliotecalivrosemprestimos.adapter.input.request.validation;

public record AtualizarLivroRequest(
        String titulo,
        String autor,
        String isbn,
        Integer estoque,
        Boolean ativo
) {}