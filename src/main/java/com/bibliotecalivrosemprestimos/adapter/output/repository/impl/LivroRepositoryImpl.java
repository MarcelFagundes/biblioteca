//package com.bibliotecalivrosemprestimos.adapter.output.repository.impl;
//
//import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface LivroRepositoryImpl extends JpaRepository<LivroEntity, Long> {
//
//    // Busca por todos livros
//    List<LivroEntity> findAll();
//
//    // Verifica se ISBN já existe (para validação de unicidade)
//    boolean existsByIsbn(String isbn);
//
//    // Busca por título (com LIKE)
//    List<LivroEntity> findByTituloContaining(String titulo);
//
//    // Busca por título e status ativo
//    List<LivroEntity> findByTituloContainingAndAtivo(String titulo, boolean ativo);
//
//    // Busca por status ativo
//    List<LivroEntity> findByAtivo(boolean ativo);
//
//    // Busca por ISBN
//    Optional<LivroEntity> findByIsbn(String isbn);
//
//    // Consulta customizada para livros emprestados (com informações do empréstimo e usuário)
//    @Query("SELECT l, e, u FROM LivroEntity l " +
//           "JOIN EmprestimoEntity e ON e.livro = l " +
//           "JOIN UsuarioEntity u ON e.usuario = u " +
//           "WHERE e.devolvidoEm IS NULL")
//    List<Object[]> findLivrosEmprestados();
//}