package com.bibliotecalivrosemprestimos.adapter.input.request;

import java.time.LocalDateTime;

// Para o relatório de livros emprestados
public record LivroComEmprestimoRequest(
        Long livroId,
        String titulo,
        String autor,
        String usuarioNome,
        String usuarioEmail,
        LocalDateTime retiradoEm,
        LocalDateTime devolucaoPrevista
) {}