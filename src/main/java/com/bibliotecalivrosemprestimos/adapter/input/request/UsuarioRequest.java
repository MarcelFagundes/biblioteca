package com.bibliotecalivrosemprestimos.adapter.input.request;

import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;

public record UsuarioRequest(
    Long id,
    String nome,
    String email
) {
    public static UsuarioRequest fromEntity(UsuarioEntity usuario) {
        return new UsuarioRequest(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}