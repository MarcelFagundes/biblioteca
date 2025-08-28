package com.bibliotecalivrosemprestimos.adapter.input.request;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;

public record UsuarioRequest(
    Long id,
    String nome,
    String email
) {
    public static UsuarioRequest fromEntity(Usuario usuario) {
        return new UsuarioRequest(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}