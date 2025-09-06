package com.bibliotecalivrosemprestimos.adapter.output.repository;


import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UsuarioRepository implements UsuarioOutputPort {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper para converter ResultSet em UsuarioEntity
    private final RowMapper<Usuario> usuarioRowMapper = (rs, rowNum) -> {
          Usuario usuario = new Usuario();
          usuario.setId(rs.getLong("id"));
          usuario.setNome(rs.getString("nome"));
          usuario.setEmail(rs.getString("email"));

         return usuario;
        };

@Override
public Usuario save(Usuario usuario) {
    //Usando Procedure no SQL
    try {
        String sql = "CALL pr_inserir_usuario(?, ?, ?, ?)"; // Agora 3 parâmetros

        Long generatedId = jdbcTemplate.execute(sql, (CallableStatementCallback<Long>) cs -> {
            cs.setString(1, usuario.getNome());
            cs.setString(2, usuario.getEmail());
            cs.registerOutParameter(3, Types.BIGINT);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            return cs.getLong(3);
        });

        usuario.setId(generatedId);
        return usuario;

    } catch (Exception e) {
        throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
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
        String sql = "SELECT * FROM fn_usuarios_com_emprestimos()";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Object[] result = new Object[3];

            // 1. Criar e popular o objeto Usuario
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));

            // 2. Adicionar ao array
//            result[0] = usuario;                      // Usuario completo
            result[1] = rs.getLong("total_emprestimos"); // Total de empréstimos
            result[2] = rs.getLong("emprestimos_ativos"); // Empréstimos ativos

            // DEBUG: Verifique os valores
            System.out.println("Usuario: id=" + usuario.getId() +
                    ", nome=" + usuario.getNome() +
                    ", email=" + usuario.getEmail());
            System.out.println("Totais: total=" + result[1] + ", ativos=" + result[2]);

            return result;
        });
    }
}