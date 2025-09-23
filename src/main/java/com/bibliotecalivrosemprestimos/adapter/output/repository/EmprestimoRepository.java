package com.bibliotecalivrosemprestimos.adapter.output.repository;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.EmprestimoMapper;
import com.bibliotecalivrosemprestimos.adapter.output.entity.EmprestimoEntity;
import com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper.EmprestimoRowMapper;
import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.port.output.EmprestimoOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

//    // RowMapper para converter ResultSet em EmprestimoEntity
//    private final RowMapper<Emprestimo> emprestimoRowMapper = (rs, rowNum) -> {
//        Emprestimo emprestimo = new Emprestimo();
//
//        // Dados tabelas estrangeiras
//        emprestimo.setLivroId(rs.getLong("livro_id"));
//        emprestimo.setUsuarioId(rs.getLong("usuario_id"));
//        emprestimo.setUsuarioNome(rs.getString("usuario_nome"));
//        emprestimo.setLivroTitulo(rs.getString("livro_titulo"));
//
//        // Dados básicos do empréstimoq
//        emprestimo.setId(rs.getLong("id"));
//
//        emprestimo.setRetiradoEm(rs.getTimestamp("retirado_em").toLocalDateTime());
//        emprestimo.setDevolucaoPrevista(rs.getTimestamp("devolucao_prevista").toLocalDateTime());
//
//        Timestamp devolvidoEm = rs.getTimestamp("devolvido_em");
//        emprestimo.setDevolvidoEm(devolvidoEm != null ? devolvidoEm.toLocalDateTime() : null);
//
//        emprestimo.setRetiradoEm(rs.getObject("retirado_em", LocalDateTime.class));
//        emprestimo.setDevolucaoPrevista(rs.getObject("devolucao_prevista", LocalDateTime.class));
//
//        return emprestimo;
//    };

//    @Override
//    public CriarEmprestimoRequest save(Emprestimo emprestimo) {
//        return null;
//    }

    @Override
    public Emprestimo save(Emprestimo emprestimo) {

        Emprestimo emprestimo1 = new Emprestimo();

        EmprestimoEntity emprestimoEntity = emprestimoMapper.toEntity(emprestimo1);

            if (emprestimoEntity.getLivroId() == null) {
                // INSERT
//                String sql = "INSERT INTO emprestimo (livro_id, usuario_id, retirado_em, devolucao_prevista, devolvido_em) " +
//                        "VALUES (?, ?, ?, ?, ?) RETURNING id ";

                String sql = "SELECT FROM fn_inserir_emprestimo(?, ?, ?, ?, ?) ";

               try {
                    Long generatedId = jdbcTemplate.queryForObject(
                            sql,
                            Long.class,
                            emprestimoEntity.getLivroId(),
                            emprestimoEntity.getUsuarioId(),
                            Timestamp.valueOf(emprestimoEntity.getRetiradoEm()),
                            Timestamp.valueOf(emprestimoEntity.getDevolucaoPrevista()),
                            emprestimoEntity.getDevolucaoPrevista() != null ?
                                    Timestamp.valueOf(emprestimoEntity.getDevolucaoPrevista()) : null
                    );


                    emprestimoEntity.setId(generatedId);

                    Emprestimo emprestimoNovo = emprestimoMapper.toDomain(emprestimoEntity);

                    return emprestimoNovo;

                } catch (Exception e) {
                    throw new RuntimeException("Erro na criação do empréstimo: " + e.getMessage(), e);
                }
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
                    emprestimoEntity.getRetiradoEm(),
                    emprestimoEntity.getDevolucaoPrevista(),
                    emprestimoEntity.getDevolvidoEm()
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
            EmprestimoEntity emprestimoEntity = jdbcTemplate.queryForObject(sql, emprestimoRowMapper, usuario_id);
            Emprestimo emprestimo = emprestimoMapper.toDomain(emprestimoEntity);
            System.out.println(emprestimo.getLivro().getTitulo());
            return Optional.ofNullable(emprestimo);
        } catch (Exception e) {
            return Optional.empty();
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
        String sql = "SELECT e.*, l.titulo, u.nome " +
                "FROM emprestimo e " +
                "LEFT JOIN livro l ON e.livro_id = l.id " +
                "LEFT JOIN usuario u ON e.usuario_id = u.id " +
                "WHERE e.usuario_id = ?" ;
//        String sql = "SELECT * FROM fn_buscar_emprestimos_por_usuario(?) ";
//        return jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
//        String sql = "SELECT * FROM fn_buscar_emprestimos_por_usuario(?) ";
        try {
            List<EmprestimoEntity> emprestimoEntity =  jdbcTemplate.query(sql, emprestimoRowMapper, usuario_id);
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

//    @Override
//    public Optional<Emprestimo> findById(Long id) {
//        String sql = "SELECT e.*, l.* " +
//                "FROM emprestimo e " +
//                "INNER JOIN livro l ON e.livro_id = l.id " +
//                "WHERE e.id = ?";
//
//        try {
//            Emprestimo emprestimo = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
//                Emprestimo emp = new Emprestimo();
//                emp.setId(rs.getLong("id"));
//                emp.setRetiradoEm(rs.getTimestamp("retirado_em").toLocalDateTime());
//                emp.setDevolucaoPrevista(rs.getTimestamp("devolucao_prevista").toLocalDateTime());
//
//                Timestamp devolvidoEm = rs.getTimestamp("devolvido_em");
//                emp.setDevolvidoEm(devolvidoEm != null ? devolvidoEm.toLocalDateTime() : null);
//
//                // Carrega o livro
//                Livro livro = new Livro();
//                livro.setId(rs.getLong("livro_id"));
//                livro.setTitulo(rs.getString("titulo"));
//                livro.setEstoque(rs.getInt("estoque"));
//                // ... outros campos do livro
//                emp.setLivro(livro);
//
//                return emp;
//            }, id);
//
//            return Optional.ofNullable(emprestimo);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//    }
}