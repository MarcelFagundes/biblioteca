package com.bibliotecalivrosemprestimos.adapter.input.request.validation;

import java.time.LocalDateTime;

public record DevolverLivroRequest(
        Long emprestimoId,
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