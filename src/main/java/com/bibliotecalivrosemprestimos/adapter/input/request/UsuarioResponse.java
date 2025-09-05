package com.bibliotecalivrosemprestimos.adapter.input.request;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    long emprestimosAtivos,
    long totalEmprestimos
) {}