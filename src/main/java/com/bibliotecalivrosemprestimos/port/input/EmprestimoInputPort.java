package com.bibliotecalivrosemprestimos.port.input;

import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import java.util.List;

public interface EmprestimoInputPort {
    EmprestimoRequest criarEmprestimo(CriarEmprestimoRequest request);
    List<EmprestimoRequest> listarEmprestimos(Long usuarioId, Boolean ativo);
    EmprestimoRequest registrarDevolucao(Long id);
    MultaRequest calcularMulta(Long id);
}