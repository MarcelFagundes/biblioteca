package com.bibliotecalivrosemprestimos.core.usecase.strategy;

import java.time.LocalDate;

public interface CalculoMultaStrategy {
    double calcularMulta(LocalDate dataDevolucaoPrevista, LocalDate dataDevolucaoReal, double valorBase);
    String getTipo();
}