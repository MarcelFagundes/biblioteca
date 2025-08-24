package com.bibliotecalivrosemprestimos.adapter.input.request;

// Para o cálculo de multa
public record MultaRequest(
        long diasAtraso,
        double valor
) {}