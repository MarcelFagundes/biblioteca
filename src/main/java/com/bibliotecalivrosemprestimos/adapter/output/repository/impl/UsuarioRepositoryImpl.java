package com.bibliotecalivrosemprestimos.adapter.output.repository.impl;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UsuarioRepositoryImpl implements UsuarioOutputPort {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para converter ResultSet em UsuarioEntity
    private final RowMapper<Usuario> usuarioRowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
//        usuario.setNome(rs.getString("usuario"));
//        usuario.setEmail(rs.getString("email"));

        return usuario;
    };

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            // INSERT
            String sql = "INSERT INTO usuario (nome, email) " +
                    "VALUES (?, ?) RETURNING id" ;

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, usuario.getNome());
                ps.setString(2, usuario.getEmail());
                return ps;
            }, keyHolder);

            usuario.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return usuario;
        } else {
            // UPDATE
            update(usuario);
            return usuario;
        }
    }

    @Override
    public int update(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ? WHERE id = ?";

        return jdbcTemplate.update(sql,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getId());
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, usuarioRowMapper, id);
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuario";
        return jdbcTemplate.query(sql, usuarioRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, usuarioRowMapper, email);
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Object[]> findUsuariosComEmprestimos() {
        String sql = "SELECT u.id, u.nome as nome, u.email as email, " +
                "COUNT(e.id) as total_emprestimos, " +
                "SUM(CASE WHEN e.devolvido_em IS NULL THEN 1 ELSE 0 END) as emprestimos_ativos " +
                "FROM usuario u " +
                "LEFT JOIN emprestimo e ON e.usuario_id = u.id " +
                "GROUP BY u.id, u.nome, u.email " +
                "ORDER BY u.nome";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Object[] result = new Object[10];

//            // Mapear UsuarioEntity
            Usuario usuario = usuarioRowMapper.mapRow(rs, rowNum);
//            result[0] = rs.getLong("id");
//           result[0] = rs.getString("nome");

            // Total de empréstimos
            result[1] = rs.getLong("total_emprestimos");

            // Empréstimos ativos
            result[2] = rs.getLong("emprestimos_ativos");

            return result;
        });
    }
}