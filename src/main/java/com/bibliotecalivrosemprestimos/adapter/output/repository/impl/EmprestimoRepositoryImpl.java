//package com.bibliotecalivrosemprestimos.adapter.output.repository.impl;
//
//import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
//import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
//import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface EmprestimoRepositoryImpl extends JpaRepository<EmprestimoEntity, Long> {
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