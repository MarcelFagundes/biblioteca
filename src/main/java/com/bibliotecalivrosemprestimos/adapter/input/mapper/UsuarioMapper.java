package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE =  Mappers.getMapper(UsuarioMapper.class);

    Usuario toEntity(UsuarioRequest usuarioRequest);
    UsuarioRequest toRequest(UsuarioEntity usuarioEntity);
    UsuarioRequest fromEntity(Usuario usuario);
    CriarUsuarioRequest toRequest(CriarUsuarioRequest request);
    UsuarioComEmprestimosRequest objectArrayToDto(UsuarioComEmprestimosRequest usuarioComEmprestimosRequest);

    Usuario toDomain(UsuarioEntity usuarioEntity);
    List<Usuario> toDomain(List<UsuarioEntity> usuarioEntity);
}