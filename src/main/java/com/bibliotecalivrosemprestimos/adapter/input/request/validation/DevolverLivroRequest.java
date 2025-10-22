package com.bibliotecalivrosemprestimos.adapter.input.request.validation;

import java.time.LocalDateTime;

public record DevolverLivroRequest(
        Long id,
        Long usuarioId,
        String usuarioNome,
        String livroTitulo,
        Long livroId,
        LocalDateTime retiradoEm,
        LocalDateTime devolucaoPrevista,
        LocalDateTime devolvidoEm,
        boolean ativo,
        boolean atrasado
) {}