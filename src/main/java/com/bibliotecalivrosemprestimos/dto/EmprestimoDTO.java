package com.bibliotecalivrosemprestimos.dto;

import java.time.LocalDateTime;

import com.bibliotecalivrosemprestimos.entity.EmprestimoEntity;

public record EmprestimoDTO(
    Long id,
    Long livroId,
    String livroTitulo,
    Long usuarioId,
    String usuarioNome,
    LocalDateTime retiradoEm,
    LocalDateTime devolucaoPrevista,
    LocalDateTime devolvidoEm,
    boolean ativo,
    boolean atrasado
) {
    public static EmprestimoDTO fromEntity(EmprestimoEntity emprestimo) {
        return new EmprestimoDTO(
            emprestimo.getId(),
            emprestimo.getLivro().getId(),
            emprestimo.getLivro().getTitulo(),
            emprestimo.getUsuario().getId(),
            emprestimo.getUsuario().getNome(),
            emprestimo.getRetiradoEm(),
            emprestimo.getDevolucaoPrevista(),
            emprestimo.getDevolvidoEm(),
            emprestimo.isAtivo(),
            emprestimo.isAtrasado()
        );
    }
}
