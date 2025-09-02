package com.bibliotecalivrosemprestimos.core.service;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioEmprestimosMapper;
import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.adapter.input.exception.BusinessException;
import com.bibliotecalivrosemprestimos.adapter.input.exception.NotFoundException;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
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

//        UsuarioRequest usuarioRequest = UsuarioMapper.INSTANCE.fromEntity(usuario);

        usuario = usuarioOutputPort.save(usuario);

        return UsuarioMapper.INSTANCE.fromEntity(usuario);
    }

    public UsuarioRequest buscarPorId(Long id) {
        Usuario usuario = usuarioOutputPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return UsuarioMapper.INSTANCE.fromEntity(usuario);
    }


    public List<UsuarioRequest> listarTodos() {
        return usuarioOutputPort.findAll()
                .stream()
                .map(UsuarioMapper.INSTANCE::fromEntity)
                .toList();
    }

    public List<UsuarioComEmprestimosRequest> listarUsuariosComEmprestimos() {

         return usuarioOutputPort.findUsuariosComEmprestimos()
                .stream()
                .map(UsuarioEmprestimosMapper.INSTANCE::objectArrayToDto)
                .toList();
    }


//    private UsuarioComEmprestimosRequest toUsuarioComEmprestimosDTO(UsuarioRequest usuarioRequest, UsuarioComEmprestimosRequest usuarioComEmprestimosRequest) {
////        Usuario usuario = (Usuario) result[0];
////        Emprestimo totalEmprestimos = (Emprestimo) result[1];
////        Emprestimo emprestimosAtivos = (Emprestimo) result[2];
//
////        UsuarioRequest usuarioRequest = UsuarioMapper.INSTANCE.fromEntity(usuario);
////        EmprestimoMapper emprestimoMapper = UsuarioMapper.INSTANCE.fromEntity(emprestimo);
//
//        return new UsuarioComEmprestimosRequest(
//                usuarioRequest.id(),
//                usuarioRequest.nome(),
//                usuarioRequest.email(),
//                usuarioComEmprestimosRequest.emprestimosAtivos(),
//                usuarioComEmprestimosRequest.emprestimosAtivos()
//        );
//    }
}