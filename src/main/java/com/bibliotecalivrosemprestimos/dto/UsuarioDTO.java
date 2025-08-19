package com.bibliotecalivrosemprestimos.dto;

import com.bibliotecalivrosemprestimos.entity.UsuarioEntity;

public record UsuarioDTO(
    Long id,
    String nome,
    String email
) {
    public static UsuarioDTO fromEntity(UsuarioEntity usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}