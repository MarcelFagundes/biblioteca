package com.bibliotecalivrosemprestimos.adapter.input.request;

public record UsuarioRequest(
    Long id,
    String nome,
    String email
) {}