package com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper;

import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EmprestimoRowMapper implements RowMapper<EmprestimoEntity> {
    @Override
    public EmprestimoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        EmprestimoEntity emprestimoEntity = new EmprestimoEntity();

        // Dados tabelas estrangeiras
        emprestimoEntity.setLivroId(rs.getLong("livro_id"));
        emprestimoEntity.setUsuarioId(rs.getLong("usuario_id"));
        emprestimoEntity.setUsuarioNome(rs.getString("usuario_nome"));
        emprestimoEntity.setLivroTitulo(rs.getString("livro_titulo"));

        // Dados básicos do empréstimoq
        emprestimoEntity.setId(rs.getLong("id"));

        emprestimoEntity.setRetiradoEm(rs.getTimestamp("retirado_em").toLocalDateTime());
        emprestimoEntity.setDevolucaoPrevista(rs.getTimestamp("devolucao_prevista").toLocalDateTime());

        Timestamp devolvidoEm = rs.getTimestamp("devolvido_em");
        emprestimoEntity.setDevolvidoEm(devolvidoEm != null ? devolvidoEm.toLocalDateTime() : null);

        emprestimoEntity.setRetiradoEm(rs.getObject("retirado_em", LocalDateTime.class));
        emprestimoEntity.setDevolucaoPrevista(rs.getObject("devolucao_prevista", LocalDateTime.class));

        return emprestimoEntity;
    }
}