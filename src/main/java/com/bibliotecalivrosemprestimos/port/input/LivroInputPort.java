package com.bibliotecalivrosemprestimos.port.input;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.validation.CriarLivroRequest;

import java.util.List;

public interface LivroInputPort {
    LivroRequest criarLivro(CriarLivroRequest request);
    List<LivroRequest> listarLivros(String titulo, Boolean ativo);
    LivroRequest buscarPorId(Long id);
    LivroRequest atualizarLivro(Long id, AtualizarLivroRequest request);
    public void desativarLivro(Long id);
    public List<Livro> listarTodosLivros();
    List<LivroComEmprestimoRequest> listarLivrosEmprestados();

}
