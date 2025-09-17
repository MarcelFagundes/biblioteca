package com.bibliotecalivrosemprestimos.adapter.output.repository;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper.UsuarioRowMapper;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository implements UsuarioOutputPort {

    private final JdbcTemplate jdbcTemplate;
    private final UsuarioRowMapper usuarioRowMapper;
    private final UsuarioMapper usuarioMapper;

    public UsuarioRepository(JdbcTemplate jdbcTemplate, UsuarioRowMapper usuarioRowMapper, UsuarioMapper usuarioMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioRowMapper = usuarioRowMapper;
        this.usuarioMapper = usuarioMapper;
    }

    // RowMapper para converter ResultSet em UsuarioEntity
//    private final RowMapper<Usuario> usuarioRowMapper = (rs, rowNum) -> {
//        Usuario usuario = new Usuario();
//        usuario.setId(rs.getLong("id"));
//        usuario.setNome(rs.getString("nome"));
//        usuario.setEmail(rs.getString("email"));
//
//        return usuario;
//    };

    @Override
    public Usuario save(Usuario usuario) {
        //Usando Procedure no SQL
        try {
            String sql = "CALL pr_inserir_usuario(?, ?, ?, ?)";

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
        String sql = "SELECT * FROM fn_atualizar_usuario(?, ?, ?)";

        try {
            return jdbcTemplate.update(sql, Integer.class,
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getId());
        } catch (Exception e) {
            throw new RuntimeException("Erro na atualização do usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT * FROM fn_buscar_usuario_por_id(?)";
//        String sql = "SELECT * FROM usuario WHERE id = ?";
        try {
            UsuarioEntity usuarioEntity = jdbcTemplate.queryForObject(sql, usuarioRowMapper, id);
            Usuario usuario = usuarioMapper.toDomain(usuarioEntity);
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM fn_buscar_todos_usuarios()";
//        return jdbcTemplate.query(sql, usuarioRowMapper);
        List<UsuarioEntity> usuarioEntity = jdbcTemplate.query(sql, usuarioRowMapper);
        List<Usuario> usuario = usuarioMapper.toDomain(usuarioEntity);
        return usuario;
    }

    @Override
    public void deleteById(Long id) {
//        String sql = "DELETE FROM usuario WHERE id = ?";
        String sql = "SELECT * FROM fn_deletar_usuario()";
//        jdbcTemplate.update(sql, id);
        try {
            jdbcTemplate.query(sql, usuarioRowMapper);
        } catch (Exception e) {
            throw new RuntimeException("Usuário não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
//        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
//        String sql = "SELECT * FROM fn_verificar_email_existe(?)";
//
//        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
//        return count != null && count > 0;
        String sql = "SELECT * FROM fn_verificar_email_existe(?)";
        try {

            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Usuário não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
//        String sql = "SELECT * FROM usuario WHERE email = ?";
//        String sql = "SELECT * FROM fn_buscar_usuario_por_email(?)";
//        try {
//            Usuario usuario = jdbcTemplate.queryForObject(sql, usuarioRowMapper, email);
//            return Optional.ofNullable(usuario);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
        String sql = "SELECT * FROM fn_buscar_usuario_por_email(?)";
        try {
            UsuarioEntity usuarioEntity = jdbcTemplate.queryForObject(sql, usuarioRowMapper, email);
            Usuario usuario = usuarioMapper.toDomain(usuarioEntity);
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UsuarioComEmprestimosRequest> findUsuariosComEmprestimos() {
        String sql = "SELECT * FROM fn_usuarios_com_emprestimos()";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UsuarioComEmprestimosRequest usuario = new UsuarioComEmprestimosRequest();
            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setTotalEmprestimos(rs.getLong("total_emprestimos"));
            usuario.setEmprestimosAtivos(rs.getLong("emprestimos_ativos"));

            return usuario;
        });
    }
}