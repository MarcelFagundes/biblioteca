package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarLivroRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    LivroMapper INSTANCE =  Mappers.getMapper(LivroMapper.class);

    Livro toEntity(LivroRequest livroRequest);
    Livro toEntity(CriarLivroRequest livroRequest);
    LivroRequest fromEntity(Livro livro);

    CriarLivroRequest toRequest(CriarLivroRequest request);
    AtualizarLivroRequest toRequest(AtualizarLivroRequest request);
}