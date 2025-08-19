package com.bibliotecalivrosemprestimos.validation;

import jakarta.validation.constraints.Future;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public record CriarEmprestimoRequest(
        @NotNull
        Long livroId,

        @NotNull
        Long usuarioId,

        @Future
        LocalDateTime devolucaoPrevista
) {}