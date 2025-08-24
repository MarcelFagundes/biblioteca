package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LivroMapper {
    LivroMapper INSTANCE =  Mappers.getMapper(LivroMapper.class);
}