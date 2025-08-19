package com.bibliotecalivrosemprestimos.dto;

// Para o cálculo de multa
public record MultaDTO(
        long diasAtraso,
        double valor
) {}
