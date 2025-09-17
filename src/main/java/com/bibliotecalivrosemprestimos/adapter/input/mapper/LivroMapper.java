package com.bibliotecalivrosemprestimos.adapter.input.mapper;

import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    LivroMapper INSTANCE =  Mappers.getMapper(LivroMapper.class);

    Livro toEntity(LivroRequest livroRequest);
    LivroRequest toRequest(LivroEntity livroEntity);
    LivroRequest fromEntity(Livro livro);

    CriarLivroRequest toRequest(LivroRequest request);
    AtualizarLivroRequest toRequest(AtualizarLivroRequest request);

    LivroComEmprestimoRequest toDto(Long livroId, String livroTitulo, String livroAutor, Long usuarioId,
                                    String usuarioNome, LocalDateTime retiradoEm, LocalDateTime devolucaoPrevista,
                                    LocalDateTime devolvidoEm, boolean ativo, boolean atrasado);

    default LivroComEmprestimoRequest objectArrayToDto(Object[] result) {
        LivroRequest livro = (LivroRequest) result[0];
        UsuarioRequest usuario = (UsuarioRequest) result[1];
        EmprestimoRequest emprestimo = (EmprestimoRequest) result[2];

        return toDto(livro.id(), livro.titulo(), livro.autor(), usuario.id(), usuario.nome(),
                emprestimo.retiradoEm(), emprestimo.devolucaoPrevista(), emprestimo.devolvidoEm(),
                emprestimo.ativo(), emprestimo.atrasado());
    }

    Livro toDomain(LivroEntity livroEntity);

    LivroEntity toEntity(Livro livro);

    List<Livro> toDomain(List<LivroEntity> livroEntity);
}