package com.bibliotecalivrosemprestimos.adapter.output.repository;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.EmprestimoMapper;
import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
import com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper.EmprestimoRowMapper;
import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.EmprestimoOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EmprestimoRepository implements EmprestimoOutputPort {

    private final JdbcTemplate jdbcTemplate;

    private final EmprestimoRowMapper emprestimoRowMapper;

    private final EmprestimoMapper emprestimoMapper;

    public EmprestimoRepository(JdbcTemplate jdbcTemplate, EmprestimoRowMapper emprestimoRowMapper,
                                EmprestimoMapper emprestimoMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.emprestimoRowMapper = emprestimoRowMapper;
        this.emprestimoMapper = emprestimoMapper;
    }

    public Emprestimo save(Emprestimo emprestimo) {

        if (emprestimo.getId() == null) {

//            String sql = "SELECT FROM fn_inserir_emprestimo(?, ?, ?, ?, ?) "
            String sql = "CALL sp_inserir_emprestimo(?, ?, ?, ?, ?) ";

            // INSERT
//            String sql = "INSERT INTO emprestimo (livro_id, usuario_id, retirado_em, devolucao_prevista, devolvido_em) " +
//                    "VALUES (?, ?, ?, ?, ?) RETURNING id ";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setLong(1, emprestimo.getLivroId());
                ps.setLong(2, emprestimo.getUsuarioId());
                ps.setTimestamp(3, Timestamp.valueOf(emprestimo.getRetiradoEm()));
                ps.setTimestamp(4, Timestamp.valueOf(emprestimo.getDevolucaoPrevista()));
                ps.setTimestamp(5, null);

                return ps;
            }, keyHolder);

            Long idGerado = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
            emprestimo.setId(idGerado);

            return emprestimo;

        } else {
            update(emprestimo);
            return emprestimo;
        }
    }

    @Override
    public void update(Emprestimo emprestimo) {

        EmprestimoEntity emprestimoEntity = emprestimoMapper.toEntity(emprestimo);

//        String sql = "UPDATE emprestimo SET livro_id = ?, usuario_id = ?, retirado_em = ?, " +
//                "devolucao_prevista = ?, devolvido_em = ? WHERE usuario_id = ? ";

        String sql = "SELECT * FROM fn_atualizar_emprestimo(?, ?, ?, ?, ?, ?) ";

        try {
            jdbcTemplate.queryForObject(sql, Long.class,
                    emprestimoEntity.getId(),
                    emprestimoEntity.getLivroId(),
                    emprestimoEntity.getUsuarioId(),
                    Timestamp.valueOf(emprestimoEntity.getRetiradoEm()),
                    Timestamp.valueOf(emprestimoEntity.getDevolucaoPrevista()),
                    Timestamp.valueOf(emprestimoEntity.getDevolvidoEm())
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro na atualização do emprestimo: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Emprestimo> findById(Long usuario_id) {
//        String sql = "SELECT * FROM emprestimo WHERE id = ? RETURNING usuario_id";
//        String sql = "SELECT * FROM fn_buscar_emprestimos_por_usuario(?)";
//        try {
//            Emprestimo emprestimo = jdbcTemplate.queryForObject(sql, emprestimoRowMapper, usuario_id);
//            return Optional.ofNullable(emprestimo);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
        String sql = "SELECT * FROM fn_buscar_emprestimos_por_usuario(?)";
        try {
            List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
            List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
            return emprestimo.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Emprestimo não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Emprestimo> findAll() {
        String sql = "SELECT * FROM fn_emprestimos_completas()";
//        return jdbcTemplate.query(sql, emprestimoRowMapper);
        List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper);
        List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
        return emprestimo;
    }

    @Override
    public void deleteById(Long id) {
//        String sql = "DELETE FROM emprestimo WHERE id = ?";
        String sql = "SELECT * FROM fn_deletar_emprestimo(id) ";
//        jdbcTemplate.update(sql, id);
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Emprestimo não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Emprestimo> findByUsuarioId(Long usuario_id) {
//        String sql = "SELECT e.*, l.titulo, u.nome " +
//                "FROM emprestimo e " +
//                "LEFT JOIN livro l ON e.livro_id = l.id " +
//                "LEFT JOIN usuario u ON e.usuario_id = u.id " +
//                "WHERE e.usuario_id = ?";
        String sql = "SELECT * FROM fn_buscar_emprestimos_por_usuario(?) ";
//        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
//        String sql = "SELECT * FROM fn_buscar_emprestimos_por_usuario(?) ";
        try {
            List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
            List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
            return emprestimo;
        } catch (Exception e) {
            throw new RuntimeException("Usuário não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNull(Long usuario_id) {
//        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND devolvido_em IS NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_ativos_usuario(?) ";
//        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
        List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
        List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
        return emprestimo;
    }

    @Override
    public List<Emprestimo> findByUsuarioIdAndDevolvidoEmIsNotNull(Long usuario_id) {
//        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND devolvido_em IS NOT NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_finalizados_usuario(?)";
//        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
        List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
        List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
        return emprestimo;
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNull() {
//        String sql = "SELECT e.*, l.titulo, u.nome " +
//                "FROM emprestimo e " +
//                "LEFT JOIN livro l ON e.livro_id = l.id " +
//                "LEFT JOIN usuario u ON e.usuario_id = u.id " +
//                "WHERE e.devolvido_em IS NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_ativos()";
//        return jdbcTemplate.query(sql, emprestimoRowMapper);
        List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper);
        List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
        return emprestimo;
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNotNull() {
//        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NOT NULL";
        String sql = "SELECT * FROM fn_buscar_emprestimos_finalizados()";
//        return jdbcTemplate.query(sql, emprestimoRowMapper);
        List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper);
        List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
        return emprestimo;
    }

    @Override
    public boolean existsByLivroAndUsuarioAndDevolvidoEmIsNull(Livro livro, Usuario usuario) {
//        String sql = "SELECT COUNT(*) FROM emprestimo WHERE livro_id = ? AND usuario_id = ? AND devolvido_em IS NULL";
        String sql = "SELECT * FROM fn_count_emprestimos_ativos(?, ?)";
//        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, livro.getId(), usuario.getId());
//        return count != null && count > 0;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, livro.getId(), usuario.getId());
        return count != null && count > 0;
    }

    @Override
    public List<Emprestimo> findByDevolvidoEmIsNullAndDevolucaoPrevistaBefore(LocalDateTime data) {
//        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NULL AND devolucao_prevista < ?";
        String sql = "SELECT * FROM  fn_buscar_emprestimos_atrasados_ate_data";
//        return jdbcTemplate.query(sql, emprestimoRowMapper, Timestamp.valueOf(data));
        List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper, Timestamp.valueOf(data));
        List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
        return emprestimo;
    }

    @Override
    public List<Emprestimo> findEmprestimosAtrasados() {
//        String sql = "SELECT * FROM emprestimo WHERE devolvido_em IS NULL AND devolucao_prevista < CURRENT_TIMESTAMP";
        String sql = "SELECT * FROM  fn_buscar_emprestimos_atrasados()";
//        return jdbcTemplate.query(sql, emprestimoRowMapper);
        List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper);
        List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
        return emprestimo;
    }

    @Override
    public Optional<Emprestimo> buscarEmprestimoAtivoPorUsuarioELivro(Long usuarioId, Long livroId) {
//        String sql = """
//                SELECT id, usuario_id, livro_id, retirado_em, devolucao_prevista, devolvido_em
//                FROM emprestimo
//                WHERE usuario_id = ?
//                AND livro_id = ?
//                LIMIT 1
//                """;
        String sql = "SELECT * FROM buscar_emprestimo_ativo_por_usuario_livro(?,?)";
        try {
            List<EmprestimoEntity> emprestimoEntity = jdbcTemplate.query(sql, emprestimoRowMapper, usuarioId, livroId);
            List<Emprestimo> emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
            return emprestimo.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Usuário já possui um empréstimo ativo para este livro" + e.getMessage(), e);
        }
    }
}