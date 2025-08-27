package com.bibliotecalivrosemprestimos.adapter.output.repository;

import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.EmprestimoOutputPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmprestimoRepository extends EmprestimoOutputPort {
    // Métodos CRUD básicos
    Emprestimo save(Emprestimo emprestimo);

    // Busca empréstimos por ID
    Optional<Emprestimo> findById(Long id);

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

    // Método adicional para atualização
    int update(Emprestimo emprestimo);
}

//package com.bibliotecalivrosemprestimos.adapter.output.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
//import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
//import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface EmprestimoRepository {
//
//    // Busca empréstimos por usuário
//    List<EmprestimoEntity> findByUsuarioId(Long usuarioId);
//
//    // Busca empréstimos ativos por usuário (não devolvidos)
//    List<EmprestimoEntity> findByUsuarioIdAndDevolvidoEmIsNull(Long usuarioId);
//
//    // Busca empréstimos finalizados por usuário (devolvidos)
//    List<EmprestimoEntity> findByUsuarioIdAndDevolvidoEmIsNotNull(Long usuarioId);
//
//    // Busca todos os empréstimos ativos
//    List<EmprestimoEntity> findByDevolvidoEmIsNull();
//
//    // Busca todos os empréstimos finalizados
//    List<EmprestimoEntity> findByDevolvidoEmIsNotNull();
//
//    // Verifica se usuário já tem empréstimo ativo para um livro específico
//    boolean existsByLivroAndUsuarioAndDevolvidoEmIsNull(LivroEntity livro, UsuarioEntity usuario);
//
//    // Busca empréstimos ativos com devolução prevista antes de uma data (para notificações)
//    List<EmprestimoEntity> findByDevolvidoEmIsNullAndDevolucaoPrevistaBefore(LocalDateTime data);
//
//    // Consulta para relatório de atrasos
//    @Query("SELECT e FROM EmprestimoEntity e " +
//           "WHERE e.devolvidoEm IS NULL " +
//           "AND e.devolucaoPrevista < CURRENT_DATE")
//    List<EmprestimoEntity> findEmprestimosAtrasados();
//}