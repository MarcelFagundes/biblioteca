package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE =  Mappers.getMapper(UsuarioMapper.class);

    Usuario toEntity(UsuarioRequest usuarioRequest);
    UsuarioRequest toRequest(UsuarioEntity usuarioEntity);
    UsuarioRequest fromEntity(Usuario usuario);
    CriarUsuarioRequest toRequest(CriarUsuarioRequest request);
    UsuarioRequest toDto(Long id, String nome, String email, long l, long l1);

    default UsuarioRequest objectArrayToDto(Object[] result) {
        UsuarioRequest usuario = (UsuarioRequest) result[0];
        EmprestimoRequest emprestimo = (EmprestimoRequest) result[1];

        return toDto(usuario.id(),usuario.nome(), usuario.email(),
                       usuario.emprestimosAtivos(), usuario.totalEmprestimos());
    }
}