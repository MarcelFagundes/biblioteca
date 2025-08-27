package com.bibliotecalivrosemprestimos.core.usecase;

import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

public class LivroUseCase implements LivroInputPort {

    @Autowired
    private LivroOutputPort livroOutputPort;


    @Override
    public Livro criarLivro(Livro livro) {
        return null;
    }

    @Override
    public Livro listarLivros(String titulo, Boolean ativo) {
        return null;
    }

    @Override
    public Livro buscarLivroPorId(Long id) {
        return null;
    }

    @Override
    public Livro atualizarLivro(Long id, LivroRequest livroRequest) {
        return null;
    }

    @Override
    public Livro desativarLivro(Long id) {
        return null;
    }

    @Override
    public Livro listarLivrosEmprestados() {
        return null;
    }
}