package com.bibliotecalivrosemprestimos.validation;

public record AtualizarLivroRequest(
        String titulo,
        String autor,
        Integer estoque,
        Boolean ativo
) {}