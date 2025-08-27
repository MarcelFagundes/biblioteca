package com.bibliotecalivrosemprestimos.adapter.input.request;

public record UsuarioComEmprestimosRequest(
        Long id,
        String nome,
        String email,
        long emprestimosAtivos,
        long totalEmprestimos
) {}