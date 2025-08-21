package com.bibliotecalivrosemprestimos.repository;

import com.bibliotecalivrosemprestimos.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UsuarioEntity> findByEmail(String email);

    @Query("SELECT u, COUNT(e) as total, " +
            "SUM(CASE WHEN e.devolvidoEm IS NULL THEN 1 ELSE 0 END) as ativos " +
            "FROM UsuarioEntity u LEFT JOIN EmprestimoEntity e ON e.usuario = u " +
            "GROUP BY u")
    List<Object[]> findUsuariosComEmprestimos();
}