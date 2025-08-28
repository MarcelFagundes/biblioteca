package com.bibliotecalivrosemprestimos.port.input;

import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.validation.CriarEmprestimoRequest;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmprestimoInputPort {
    EmprestimoRequest criarEmprestimo(CriarEmprestimoRequest request);
    List<EmprestimoRequest> listarEmprestimos(Long usuarioId, Boolean ativo);
    EmprestimoRequest registrarDevolucao(Long id);
    MultaRequest calcularMulta(Long id);
}