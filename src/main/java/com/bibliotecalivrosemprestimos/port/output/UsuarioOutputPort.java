package com.bibliotecalivrosemprestimos.port.output;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;

public interface UsuarioOutputPort {
    Usuario criarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
    Usuario listarUsuariosComEmprestimos();
}
