package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.DevolverLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    EmprestimoMapper INSTANCE =  Mappers.getMapper(EmprestimoMapper.class);

    Emprestimo toEntity(EmprestimoRequest emprestimoRequest);
    EmprestimoRequest toRequest(EmprestimoEntity emprestimoEntity);
    EmprestimoRequest fromEntity(Emprestimo emprestimo);
    CriarEmprestimoRequest toRequest(CriarEmprestimoRequest request);
    DevolverLivroRequest toRequest(DevolverLivroRequest request);

    EmprestimoEntity toEntity(Emprestimo emprestimo);

    Emprestimo toDomain(EmprestimoEntity emprestimoEntity);

    List<Emprestimo> toDomain(List<EmprestimoEntity> emprestimoEntity);
}