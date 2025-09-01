package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioEmprestimosMapper {

    UsuarioEmprestimosMapper INSTANCE = Mappers.getMapper(UsuarioEmprestimosMapper.class);

    @Mapping(target = "id", source = "usuario.id")
    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "emprestimosAtivos", source = "emprestimosAtivos")
    @Mapping(target = "totalEmprestimos", source = "totalEmprestimos")
    UsuarioComEmprestimosRequest toDto(UsuarioRequest usuario, Long totalEmprestimos, Long emprestimosAtivos);

    default UsuarioComEmprestimosRequest objectArrayToDto(Object[] result) {
        UsuarioRequest usuario = (UsuarioRequest) result[0];
        Long totalEmprestimos = (Long) result[1];
        Long emprestimosAtivos = (Long) result[2];

        return toDto(usuario, totalEmprestimos, emprestimosAtivos);
    }
}