package com.bibliotecalivrosemprestimos.port.input;

import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.validation.CriarUsuarioRequest;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioInputPort {
    UsuarioRequest criarUsuario(CriarUsuarioRequest request);
    UsuarioRequest buscarPorId(Long id);
    List<UsuarioRequest> listarTodos();
    List<UsuarioComEmprestimosRequest> listarUsuariosComEmprestimos();
}