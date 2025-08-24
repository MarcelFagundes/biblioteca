package com.bibliotecalivrosemprestimos.core.usecase;

import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.input.EmprestimoInputPort;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;

public class EmprestimoUseCase implements EmprestimoInputPort {
    @Override
    public Emprestimo criarEmprestimo(Usuario usuario) {
        return null;
    }

    @Override
    public Emprestimo listarEmprestimos(Long id, Boolean ativo) {
        return null;
    }

    @Override
    public Emprestimo registrarDevolucao(Long id) {
        return null;
    }

    @Override
    public Emprestimo calcularMulta(Long id) {
        return null;
    }
}
