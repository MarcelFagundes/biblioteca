package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE =  Mappers.getMapper(UsuarioMapper.class);

    Usuario toEntity(UsuarioRequest usuarioRequest);
    UsuarioRequest toRequest(UsuarioEntity usuarioEntity);
    UsuarioRequest fromEntity(Usuario usuario);
    CriarUsuarioRequest toRequest(CriarUsuarioRequest request);



//    @Mapping(target = "id", source = "usuario.id")
//    @Mapping(target = "nome", source = "usuario.nome")
//    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "emprestimosAtivos", source = "emprestimosAtivos")
    @Mapping(target = "totalEmprestimos", source = "totalEmprestimos")

    UsuarioRequest toDto(UsuarioRequest usuario, Long totalEmprestimos, Long emprestimosAtivos);

    default UsuarioRequest objectArrayToDto(Object[] result) {
        UsuarioRequest usuario = (UsuarioRequest) result[0];
        Long totalEmprestimos = (Long) result[1];
        Long emprestimosAtivos = (Long) result[2];

        return toDto(usuario, totalEmprestimos, emprestimosAtivos);
    }
}