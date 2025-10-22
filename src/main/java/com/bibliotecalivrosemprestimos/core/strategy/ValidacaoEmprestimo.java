package com.bibliotecalivrosemprestimos.core.strategy;

import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;

public interface ValidacaoEmprestimo {
    void validar(CriarEmprestimoRequest request);
    int getOrdem(); // Para ordenação das validações
}
