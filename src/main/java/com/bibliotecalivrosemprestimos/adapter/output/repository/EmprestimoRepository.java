package com.bibliotecalivrosemprestimos.adapter.output.repository;

import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.EmprestimoOutputPort;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class EmprestimoRepository implements EmprestimoOutputPort {

    private final JdbcTemplate jdbcTemplate;

    public EmprestimoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper para converter ResultSet em EmprestimoEntity
    private final RowMapper<Emprestimo> emprestimoRowMapper = (rs, rowNum) -> {
        Emprestimo emprestimo = new Emprestimo();

        // Dados tabelas estrangeiras
        emprestimo.setLivroId(rs.getLong("livro_id"));
        emprestimo.setUsuarioId(rs.getLong("usuario_id"));
        emprestimo.setUsuarioNome(rs.getString("usuario_nome"));
        emprestimo.setLivroTitulo(rs.getString("livro_titulo"));

        // Dados básicos do empréstimo
        emprestimo.setId(rs.getLong("id"));

        emprestimo.setRetiradoEm(rs.getTimestamp("retirado_em").toLocalDateTime());
        emprestimo.setDevolucaoPrevista(rs.getTimestamp("devolucao_prevista").toLocalDateTime());

        Timestamp devolvidoEm = rs.getTimestamp("devolvido_em");
        emprestimo.setDevolvidoEm(devolvidoEm != null ? devolvidoEm.toLocalDateTime() : null);

        emprestimo.setRetiradoEm(rs.getObject("retirado_em", LocalDateTime.class));
        emprestimo.setDevolucaoPrevista(rs.getObject("devolucao_prevista", LocalDateTime.class));

        return emprestimo;
    };

    @Override
    public Emprestimo save(Emprestimo emprestimo){
        if (emprestimo.getId() == null) {

//        String sql = "INSERT INTO emprestimo (livro_id, usuario_id, retirado_em, devolucao_prevista, devolvido_em) " +
//                    "VALUES (?, ?, ?, ?, ?) RETURNING id ";

        String sql = "SELECT * FROM fn_inserir_emprestimo(?, ?, ?, ?, ?) ";
        try {
            jdbcTemplate.queryForObject(sql,
                    Long.class,
                    emprestimo.getLivro().getId(),
                    emprestimo.getUsuario().getId(),
                    Timestamp.valueOf(emprestimo.getRetiradoEm()),
                    Timestamp.valueOf(emprestimo.getDevolucaoPrevista()),
                    emprestimo.getDevolvidoEm() != null ? Timestamp.valueOf(emprestimo.getDevolvidoEm()) : null);
        } catch (Exception e) {
            throw new RuntimeException("Erro na atualização do emprestimo: " + e.getMessage(), e);
        }
        return emprestimo;
    } else {
            // UPDATE
            update(emprestimo);
            return emprestimo;
        }
    }


    @Override
    public void update(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET livro_id = ?, usuario_id = ?, retirado_em = ?, " +
                "devolucao_prevista = ?, devolvido_em = ? WHERE usuario_id = ? ";

//        String sql = "SELECT * FROM fn_atualizar_emprestimo(?, ?, ?, ?, ?) ";
        try {
            jdbcTemplate.queryForObject(sql, Boolean.class,
                    emprestimo.getLivro().getId(),
                    emprestimo.getUsuario().getId(),
                    Timestamp.valueOf(emprestimo.getRetiradoEm()),
                    Timestamp.valueOf(emprestimo.getDevolucaoPrevista()),
                    emprestimo.getDevolvidoEm() != null ? Timestamp.valueOf(emprestimo.getDevolvidoEm()) : null);
        } catch (Exception e) {
            throw new RuntimeException("Erro na atualização do emprestimo: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Emprestimo> findById(Long id) {
//        String sql = "SELECT * FROM emprestimo WHERE id = ? RETURNING id";
        String sql = "SELECT * FROM fn_emprestimos_por_usuario(?) ";
        try {
            Emprestimo emprestimo = jdbcTemplate.queryForObject(sql, emprestimoRowMapper, id);
            return Optional.ofNullable(emprestimo);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Emprestimo> findAll() {
        String sql = "SELECT * FROM fn_emprestimos_completos()";

        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }

    @Override
    public void deleteById(Long id) {
//        String sql = "DELETE FROM emprestimo WHERE id = ?";
        String sql = "SELECT * FROM fn_deletar_emprestimo(id) ";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Emprestimo> findByUsuarioId(Long usuario_id) {
//        String sql = "SELECT e.*, l.titulo, u.nome " +
//                "FROM emprestimo e " +
//                "LEFT JOIN livro l ON e.livro_id = l.id " +
//                "LEFT JOIN usuario u ON e.usuario_id = u.id " +
//                "WHERE e.usuario_id = ?" ;
        String sql = "SELECT * FROM fn_buscar_emprestimos_por_usuario(?) ";
        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
    }

    @Override
    public List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNull(Long usuario_id) {
//        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND devolvido_em IS NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_ativos_usuario(?) ";
        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
    }

    @Override
    public List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNotNull(Long usuario_id) {
//        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND devolvido_em IS NOT NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_finalizados_usuario(?)";
        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNull() {
//        String sql = "SELECT e.*, l.titulo, u.nome " +
//                "FROM emprestimo e " +
//                "LEFT JOIN livro l ON e.livro_id = l.id " +
//                "LEFT JOIN usuario u ON e.usuario_id = u.id " +
//                "WHERE e.devolvido_em IS NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_ativos()";
        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNotNull() {
//        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NOT NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_finalizados()";
        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }

    @Override
    public boolean existsByLivroAndUsuarioAndDevolvidoEmIsNull(Livro livro, Usuario usuario) {
//        String sql = "SELECT COUNT(*) FROM emprestimo WHERE livro_id = ? AND usuario_id = ? AND devolvido_em IS NULL";
        String sql = "SELECT * FROM fn_count_emprestimos_ativos(?, ?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, livro.getId(), usuario.getId());
        return count != null && count > 0;
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNullAndDevolucaoPrevistaBefore(LocalDateTime data) {
//        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NULL AND devolucao_prevista < ?";
        String sql = "SELECT * FROM  fn_buscar_emprestimos_atrasados_ate_data";
        return jdbcTemplate.query(sql, emprestimoRowMapper, Timestamp.valueOf(data));
    }

    @Override
    public List<Emprestimo> findEmprestimosAtrasados() {
//        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NULL AND devolucao_prevista < CURRENT_TIMESTAMP";
        String sql = "SELECT * FROM  fn_buscar_emprestimos_atrasados()";
        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }
}