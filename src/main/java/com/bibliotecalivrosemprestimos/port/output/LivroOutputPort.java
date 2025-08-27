package com.bibliotecalivrosemprestimos.port.output;

import com.bibliotecalivrosemprestimos.core.domain.model.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroOutputPort {
    // Métodos CRUD básicos
    Livro save(Livro livro);
    Optional<Livro> findById(Long id);
    List<Livro> findAll();
    void deleteById(Long id);

    // Métodos específicos do domínio
    boolean existsByIsbn(String isbn);
    List<Livro> findByTituloContaining(String titulo);
    List<Livro> findByTituloContainingAndAtivo(String titulo, boolean ativo);
    List<Livro> findByAtivo(boolean ativo);
    Optional<Livro> findByIsbn(String isbn);

    // Consulta customizada para livros emprestados
    List<Object[]> findLivrosEmprestados();

    // Método adicional para atualização
    int update(Livro livro);
}