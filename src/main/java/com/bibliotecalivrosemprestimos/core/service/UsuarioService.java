package com.bibliotecalivrosemprestimos.core.service;

import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.exception.BusinessException;
import com.bibliotecalivrosemprestimos.exception.NotFoundException;
import com.bibliotecalivrosemprestimos.adapter.output.repository.UsuarioRepository;
import com.bibliotecalivrosemprestimos.validation.CriarUsuarioRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioRequest criarUsuario(CriarUsuarioRequest request) {
        // Validação de e-mail único
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um usuário cadastrado com este e-mail");
        }

        UsuarioEntity usuario = new UsuarioEntity(
                request.nome(),
                request.email()
        );

        usuario = usuarioRepository.save(usuario);
        return UsuarioRequest.fromEntity(usuario);
    }

    public UsuarioRequest buscarPorId(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return UsuarioRequest.fromEntity(usuario);
    }

    public List<UsuarioRequest> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioRequest::fromEntity)
                .toList();
    }

    public List<UsuarioComEmprestimosRequest> listarUsuariosComEmprestimos() {
        return usuarioRepository.findUsuariosComEmprestimos()
                .stream()
                .map(this::toUsuarioComEmprestimosDTO)
                .toList();
    }

    private UsuarioComEmprestimosRequest toUsuarioComEmprestimosDTO(Object[] result) {
        UsuarioEntity usuario = (UsuarioEntity) result[0];
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