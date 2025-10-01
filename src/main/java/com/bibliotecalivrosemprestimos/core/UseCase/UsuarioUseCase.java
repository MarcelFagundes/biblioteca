package com.bibliotecalivrosemprestimos.core.UseCase;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.adapter.input.exception.BusinessException;
import com.bibliotecalivrosemprestimos.adapter.input.exception.NotFoundException;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioUseCase implements UsuarioInputPort {

    private final UsuarioOutputPort usuarioOutputPort;

    public UsuarioUseCase(UsuarioOutputPort usuarioOutputPort) {
        this.usuarioOutputPort = usuarioOutputPort;
    }

    @Override
    public UsuarioRequest criarUsuario(CriarUsuarioRequest request) {
        // Validação de e-mail único
        if (usuarioOutputPort.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um usuário cadastrado com este e-mail");
        }

        Usuario usuario = new Usuario(
                request.nome(),
                request.email()
        );

        usuario = usuarioOutputPort.save(usuario);

        return UsuarioMapper.INSTANCE.fromEntity(usuario);
    }

    @Override
    public UsuarioRequest buscarPorId(Long id) {
        Usuario usuario = usuarioOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return UsuarioMapper.INSTANCE.fromEntity(usuario);
    }

    @Override
    public List<UsuarioRequest> listarTodos() {
        return usuarioOutputPort.findAll()
                .stream()
                .map(UsuarioMapper.INSTANCE::fromEntity)
                .toList();
    }

    @Override
    public List<UsuarioComEmprestimosRequest> listarUsuariosComEmprestimos() {

         return usuarioOutputPort.findUsuariosComEmprestimos()
                .stream()
                .map(UsuarioMapper.INSTANCE::objectArrayToDto)
                .toList();
    }
}