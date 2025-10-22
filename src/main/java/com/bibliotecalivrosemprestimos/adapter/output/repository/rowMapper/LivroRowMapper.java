package com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper;

import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivroRowMapper implements RowMapper<LivroEntity> {
    @Override
    public LivroEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

            LivroEntity livroEntity = new LivroEntity();
            livroEntity.setId(rs.getLong("id"));
            livroEntity.setTitulo(rs.getString("titulo"));
            livroEntity.setAutor(rs.getString("autor"));
            livroEntity.setIsbn(rs.getString("isbn"));
            livroEntity.setEstoque(rs.getInt("estoque"));
            livroEntity.setAtivo(rs.getBoolean("ativo"));

            return livroEntity;
        }
}