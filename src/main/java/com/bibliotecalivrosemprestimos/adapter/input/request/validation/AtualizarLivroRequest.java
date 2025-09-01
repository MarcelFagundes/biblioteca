package com.bibliotecalivrosemprestimos.adapter.input.request.validation;

public record AtualizarLivroRequest(
        String titulo,
        String autor,
        Integer estoque,
        Boolean ativo
) {}