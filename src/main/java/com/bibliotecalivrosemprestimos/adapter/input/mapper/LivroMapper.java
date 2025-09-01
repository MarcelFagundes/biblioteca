package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    LivroMapper INSTANCE =  Mappers.getMapper(LivroMapper.class);

    Livro toEntity(LivroRequest livroRequest);
    LivroRequest toRequest(LivroEntity livroEntity);
    LivroRequest fromEntity(Livro livro);
}