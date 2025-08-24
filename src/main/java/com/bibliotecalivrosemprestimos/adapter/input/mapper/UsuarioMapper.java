package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE =  Mappers.getMapper(UsuarioMapper.class);
}