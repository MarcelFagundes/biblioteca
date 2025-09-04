package com.bibliotecalivrosemprestimos.adapter.input.request.validation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CriarEmprestimoRequest(
        @NotNull
        Long livroId,
        @NotNull
        Long usuarioId,
        @Future
        LocalDateTime devolucaoPrevista
) {}