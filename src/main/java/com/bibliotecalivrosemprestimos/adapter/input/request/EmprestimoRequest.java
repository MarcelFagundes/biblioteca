package com.bibliotecalivrosemprestimos.adapter.input.request;

import java.time.LocalDateTime;

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
) {}