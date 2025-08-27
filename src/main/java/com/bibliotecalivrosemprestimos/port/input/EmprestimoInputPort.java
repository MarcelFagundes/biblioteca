package com.bibliotecalivrosemprestimos.port.input;

import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;

public interface EmprestimoInputPort {
    Emprestimo criarEmprestimo(Usuario usuario);
    Emprestimo listarEmprestimos(Long id, Boolean ativo);
    Emprestimo registrarDevolucao(Long id);
    Emprestimo calcularMulta(Long id);
}