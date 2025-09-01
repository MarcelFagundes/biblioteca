package com.bibliotecalivrosemprestimos.adapter.output.repository.impl;

import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.EmprestimoOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class EmprestimoRepositoryImpl implements EmprestimoOutputPort {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para converter ResultSet em EmprestimoEntity
    private final RowMapper<Emprestimo> emprestimoRowMapper = (rs, rowNum) -> {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(rs.getLong("id"));

        // Configurar livro
        Livro livro = new Livro();
        livro.setId(rs.getLong("livro_id"));
        livro.setTitulo(rs.getString("titulo"));
        emprestimo.setLivro(livro);

        // Configurar usuário
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("usuario_id"));
        usuario.setNome(rs.getString("nome"));
        emprestimo.setUsuario(usuario);

        emprestimo.setRetiradoEm(rs.getObject("retirado_em", LocalDateTime.class));
        emprestimo.setDevolucaoPrevista(rs.getObject("devolucao_prevista", LocalDateTime.class));

        Timestamp devolvidoEm = rs.getTimestamp("devolvido_em");
        emprestimo.setDevolvidoEm(devolvidoEm != null ? devolvidoEm.toLocalDateTime() : null);

        return emprestimo;
    };

    @Override
    public Emprestimo save(Emprestimo emprestimo) {
        if (emprestimo.getId() == null) {
            // INSERT
            String sql = "INSERT INTO emprestimo (livro_id, usuario_id, retirado_em, devolucao_prevista, devolvido_em) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING id ";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, emprestimo.getLivro().getId());
                ps.setLong(2, emprestimo.getUsuario().getId());
                ps.setTimestamp(3, Timestamp.valueOf(emprestimo.getRetiradoEm()));
                ps.setTimestamp(4, Timestamp.valueOf(emprestimo.getDevolucaoPrevista()));
                ps.setTimestamp(5, emprestimo.getDevolvidoEm() != null ?
                        Timestamp.valueOf(emprestimo.getDevolvidoEm()) : null);
                return ps;
            }, keyHolder);

            emprestimo.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return emprestimo;
        } else {
            // UPDATE
            update(emprestimo);
            return emprestimo;
        }
    }

    @Override
    public int update(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET livro_id = ?, usuario_id = ?, retirado_em = ?, " +
                "devolucao_prevista = ?, devolvido_em = ? WHERE usuario_id = ? ";

        return jdbcTemplate.update(sql,
                emprestimo.getLivro().getId(),
                emprestimo.getUsuario().getId(),
                Timestamp.valueOf(emprestimo.getRetiradoEm()),
                Timestamp.valueOf(emprestimo.getDevolucaoPrevista()),
                emprestimo.getDevolvidoEm() != null ? Timestamp.valueOf(emprestimo.getDevolvidoEm()) : null,
                emprestimo.getId());
    }

    @Override
    public Optional<Emprestimo> findById(Long id) {
        String sql = "SELECT * FROM emprestimo WHERE id = ? RETURNING id";
        try {
            Emprestimo emprestimo = jdbcTemplate.queryForObject(sql, emprestimoRowMapper, id);
            return Optional.ofNullable(emprestimo);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Emprestimo> findAll() {
        String sql = "SELECT e.*, l.titulo, u.nome " +
                "FROM emprestimo e " +
                "LEFT JOIN livro l ON e.livro_id = l.id " +
                "LEFT JOIN usuario u ON e.usuario_id = u.id";
        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Emprestimo> findByUsuarioId(Long usuario_id) {
        String sql = "SELECT e.*, l.titulo, u.nome " +
                "FROM emprestimo e " +
                "LEFT JOIN livro l ON e.livro_id = l.id " +
                "LEFT JOIN usuario u ON e.usuario_id = u.id " +
                "WHERE e.usuario_id = ?" ;
        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
    }

    @Override
    public List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNull(Long usuario_id) {
        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND devolvido_em IS NULL";
        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
    }

    @Override
    public List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNotNull(Long usuario_id) {
        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND devolvido_em IS NOT NULL";
        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNull() {
        String sql = "SELECT e.*, l.titulo, u.nome " +
                "FROM emprestimo e " +
                "LEFT JOIN livro l ON e.livro_id = l.id " +
                "LEFT JOIN usuario u ON e.usuario_id = u.id " +
                "WHERE e.devolvido_em IS NULL";
        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNotNull() {
        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NOT NULL";
        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }

    @Override
    public boolean existsByLivroAndUsuarioAndDevolvidoEmIsNull(Livro livro, Usuario usuario) {
        String sql = "SELECT COUNT(*) FROM emprestimo WHERE livro_id = ? AND usuario_id = ? AND devolvido_em IS NULL";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, livro.getId(), usuario.getId());
        return count != null && count > 0;
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNullAndDevolucaoPrevistaBefore(LocalDateTime data) {
        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NULL AND devolucao_prevista < ?";
        return jdbcTemplate.query(sql, emprestimoRowMapper, Timestamp.valueOf(data));
    }

    @Override
    public List<Emprestimo> findEmprestimosAtrasados() {
        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NULL AND devolucao_prevista < CURRENT_TIMESTAMP";
        return jdbcTemplate.query(sql, emprestimoRowMapper);
    }
}