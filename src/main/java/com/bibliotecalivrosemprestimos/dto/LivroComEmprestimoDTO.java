package com.bibliotecalivrosemprestimos.dto;

import java.time.LocalDateTime;

// Para o relatório de livros emprestados
public record LivroComEmprestimoDTO(
        Long livroId,
        String titulo,
        String autor,
        String usuarioNome,
        String usuarioEmail,
        LocalDateTime retiradoEm,
        LocalDateTime devolucaoPrevista
) {}