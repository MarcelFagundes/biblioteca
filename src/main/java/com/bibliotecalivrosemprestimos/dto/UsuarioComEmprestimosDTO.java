package com.bibliotecalivrosemprestimos.dto;

public record UsuarioComEmprestimosDTO(
        Long id,
        String nome,
        String email,
        long emprestimosAtivos,
        long totalEmprestimos
) {}