package com.bibliotecalivrosemprestimos.core.usecase;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;

public class UsuarioUseCase implements UsuarioInputPort {
    @Override
    public Usuario criarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return null;
    }

    @Override
    public Usuario listarUsuariosComEmprestimos() {
        return null;
    }
}
