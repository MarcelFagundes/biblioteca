package com.bibliotecalivrosemprestimos.port.input;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;

public interface UsuarioInputPort {
    Usuario criarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
    Usuario listarUsuariosComEmprestimos();
}
