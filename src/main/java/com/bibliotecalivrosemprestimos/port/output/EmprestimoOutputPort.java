package com.bibliotecalivrosemprestimos.port.output;

import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;

public interface EmprestimoOutputPort {
    Emprestimo criarEmprestimo(Usuario usuario);
    Emprestimo listarEmprestimos(Long id, Boolean ativo);
    Emprestimo registrarDevolucao(Long id);
    Emprestimo calcularMulta(Long id);
}