package com.bibliotecalivrosemprestimos.adapter.input.request;

import java.time.LocalDateTime;
import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;

public record EmprestimoRequest(
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
    public static EmprestimoRequest fromEntity(Emprestimo emprestimo) {
        return new EmprestimoRequest(
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