package com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper;

import com.bibliotecalivrosemprestimos.adapter.output.entity.UsuarioEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<UsuarioEntity> {
    @Override
    public UsuarioEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(rs.getLong("id"));
            usuarioEntity.setNome(rs.getString("nome"));
            usuarioEntity.setEmail(rs.getString("email"));

            return usuarioEntity;
        }
}