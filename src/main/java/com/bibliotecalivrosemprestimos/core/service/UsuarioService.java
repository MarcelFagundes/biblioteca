package com.bibliotecalivrosemprestimos.core.service;

import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.exception.BusinessException;
import com.bibliotecalivrosemprestimos.exception.NotFoundException;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import com.bibliotecalivrosemprestimos.validation.CriarUsuarioRequest;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UsuarioService implements UsuarioInputPort {

    private final UsuarioOutputPort usuarioOutputPort;

    public UsuarioService(UsuarioOutputPort usuarioOutputPort) {
        this.usuarioOutputPort = usuarioOutputPort;
    }

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
        return UsuarioRequest.fromEntity(usuario);
    }

    public UsuarioRequest buscarPorId(Long id) {
        Usuario usuario = usuarioOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return UsuarioRequest.fromEntity(usuario);
    }

    public List<UsuarioRequest> listarTodos() {
        return usuarioOutputPort.findAll()
                .stream()
                .map(UsuarioRequest::fromEntity)
                .toList();
    }

    public List<UsuarioComEmprestimosRequest> listarUsuariosComEmprestimos() {
        return usuarioOutputPort.findUsuariosComEmprestimos()
                .stream()
                .map(this::toUsuarioComEmprestimosDTO)
                .toList();
    }


    private UsuarioComEmprestimosRequest toUsuarioComEmprestimosDTO(Object[] result) {
        Usuario usuario = (Usuario) result[0];
        Long totalEmprestimos = (Long) result[1];
        Long emprestimosAtivos = (Long) result[2];

        return new UsuarioComEmprestimosRequest(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                emprestimosAtivos,
                totalEmprestimos
        );
    }
}