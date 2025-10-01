package com.bibliotecalivrosemprestimos.port.output;

import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmprestimoOutputPort {
    // Métodos CRUD básicos
    Emprestimo save(Emprestimo emprestimo);

    // Busca empréstimos por ID
    Optional<Emprestimo> findById(Long usuarioId);

    // Busca todos os empréstimos
    List<Emprestimo> findAll();

    //Deleta por ID
    void deleteById(Long id);

    // Busca empresa por id do usuário
    List<Emprestimo> findByUsuarioId(Long usuarioId);
    List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNull(Long usuarioId);
    List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNotNull(Long usuarioId);
    List<Emprestimo> findByDevolvidoEmIsNull();
    List<Emprestimo> findByDevolvidoEmIsNotNull();


    // Verifica se usuário já tem empréstimo ativo para um livro específico
    boolean existsByLivroAndUsuarioAndDevolvidoEmIsNull(Livro livro, Usuario usuario);
    List<Emprestimo> findByDevolvidoEmIsNullAndDevolucaoPrevistaBefore(LocalDateTime data);
    List<Emprestimo> findEmprestimosAtrasados();
    Optional<Emprestimo> buscarEmprestimoAtivoPorUsuarioELivro(Long usuarioId, Long livroId);

    // Método adicional para atualização
    void update(Emprestimo emprestimo);
}