package com.bibliotecalivrosemprestimos.service;

import com.bibliotecalivrosemprestimos.dto.UsuarioComEmprestimosDTO;
import com.bibliotecalivrosemprestimos.dto.UsuarioDTO;
import com.bibliotecalivrosemprestimos.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.exception.BusinessException;
import com.bibliotecalivrosemprestimos.exception.NotFoundException;
import com.bibliotecalivrosemprestimos.repository.UsuarioRepository;
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
    public UsuarioDTO criarUsuario(CriarUsuarioRequest request) {
        // Validação de e-mail único
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um usuário cadastrado com este e-mail");
        }

        UsuarioEntity usuario = new UsuarioEntity(
                request.nome(),
                request.email()
        );

        usuario = usuarioRepository.save(usuario);
        return UsuarioDTO.fromEntity(usuario);
    }

    public UsuarioDTO buscarPorId(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return UsuarioDTO.fromEntity(usuario);
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioDTO::fromEntity)
                .toList();
    }

    public List<UsuarioComEmprestimosDTO> listarUsuariosComEmprestimos() {
        return usuarioRepository.findUsuariosComEmprestimos()
                .stream()
                .map(this::toUsuarioComEmprestimosDTO)
                .toList();
    }

    private UsuarioComEmprestimosDTO toUsuarioComEmprestimosDTO(Object[] result) {
        UsuarioEntity usuario = (UsuarioEntity) result[0];
        Long totalEmprestimos = (Long) result[1];
        Long emprestimosAtivos = (Long) result[2];

        return new UsuarioComEmprestimosDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                emprestimosAtivos,
                totalEmprestimos
        );
    }
}