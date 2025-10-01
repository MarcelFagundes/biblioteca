package com.bibliotecalivrosemprestimos.port.input;
import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.DevolverLivroRequest;

import java.util.List;
import java.util.Optional;

public interface EmprestimoInputPort {
    EmprestimoRequest criarEmprestimo(CriarEmprestimoRequest request);
    List<EmprestimoRequest> listarEmprestimos(Long usuarioId, Boolean ativo);
    EmprestimoRequest registrarDevolucao(Long id, DevolverLivroRequest request);
    MultaRequest calcularMulta(Long id);
}