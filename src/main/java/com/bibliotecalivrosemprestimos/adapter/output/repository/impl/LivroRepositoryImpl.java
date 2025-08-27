package com.bibliotecalivrosemprestimos.adapter.output.repository.impl;

import com.bibliotecalivrosemprestimos.adapter.output.repository.LivroRepository;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class LivroRepositoryImpl implements LivroRepository {

    private final JdbcTemplate jdbcTemplate;

    public LivroRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper para converter ResultSet em LivroEntity
    private final RowMapper<Livro> livroRowMapper = (rs, rowNum) -> {
        Livro livro = new Livro();
        livro.setId(rs.getLong("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setIsbn(rs.getString("isbn"));

//        livro.setQuantidadeTotal(rs.getInt("quantidade_total"));
//        livro.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
        livro.setEstoque(rs.getInt("estoque"));
        livro.setAtivo(rs.getBoolean("ativo"));


        return livro;
    };

    @Override
    public Livro save(Livro livro) {
        if (livro.getId() == null) {
            // INSERT
//            String sql = "INSERT INTO livros (titulo, autor, isbn, ano_publicacao, editora, " +
//                    "quantidade_total, quantidade_disponivel, ativo, data_cadastro) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sql = "INSERT INTO livro (titulo, autor, isbn, " +
                    "estoque, ativo) " +
                    "VALUES (?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, livro.getTitulo());
                ps.setString(2, livro.getAutor());
                ps.setString(3, livro.getIsbn());


//                ps.setInt(6, livro.getQuantidadeTotal());
//                ps.setInt(7, livro.getQuantidadeDisponivel());
                ps.setInt(4, livro.getEstoque());
                ps.setBoolean(5, livro.getAtivo());
                return ps;
            }, keyHolder);

            livro.setId(keyHolder.getKey().longValue());
            return livro;
        } else {
            // UPDATE
            update(livro);
            return livro;
        }
    }

    @Override
    public int update(Livro livro) {
//        String sql = "UPDATE livro SET titulo = ?, autor = ?, isbn = ?, ano_publicacao = ?, " +
//                "editora = ?, quantidade_total = ?, quantidade_disponivel = ?, ativo = ?, " +
//                "data_cadastro = ? WHERE id = ?";

          String sql = "UPDATE livro SET titulo = ?, autor = ?, isbn = ?, " +
                 "estoque = ?, ativo = ? WHERE id = ?";

        return jdbcTemplate.update(sql,
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
//                livro.getQuantidadeTotal(),
//                livro.getQuantidadeDisponivel(),
                livro.getEstoque(),
                livro.getAtivo(),
                livro.getId());
    }

    @Override
    public Optional<Livro> findById(Long id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        try {
            Livro livro = jdbcTemplate.queryForObject(sql, livroRowMapper, id);
            return Optional.ofNullable(livro);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Livro> findAll() {
        String sql = "SELECT * FROM livro";
        return jdbcTemplate.query(sql, livroRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        String sql = "SELECT COUNT(*) FROM livro WHERE isbn = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
        return count != null && count > 0;
    }

    @Override
    public List<Livro> findByTituloContaining(String titulo) {
        String sql = "SELECT * FROM livro WHERE titulo ILIKE ?";
        return jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%");
    }

    @Override
    public List<Livro> findByTituloContainingAndAtivo(String titulo, boolean ativo) {
        String sql = "SELECT * FROM livro WHERE titulo ILIKE ? AND ativo = ?";
        return jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%", ativo);
    }

    @Override
    public List<Livro> findByAtivo(boolean ativo) {
        String sql = "SELECT * FROM livro WHERE ativo = ?";
        return jdbcTemplate.query(sql, livroRowMapper, ativo);
    }

    @Override
    public Optional<Livro> findByIsbn(String isbn) {
        String sql = "SELECT * FROM livro WHERE isbn = ?";
        try {
            Livro livro = jdbcTemplate.queryForObject(sql, livroRowMapper, isbn);
            return Optional.ofNullable(livro);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Object[]> findLivrosEmprestados() {
//        String sql = "SELECT l.*, e.id as emprestimo_id, e.data_emprestimo, e.devolucao_prevista, " +
//                "u.id as usuario_id, u.nome as usuario_nome, u.email as usuario_email " +
//                "FROM livro l " +
//                "JOIN emprestimos e ON e.livro_id = l.id " +
//                "JOIN usuarios u ON e.usuario_id = u.id " +
//                "WHERE e.devolvido_em IS NULL " +
//                "ORDER BY l.titulo";
         String sql = "SELECT l.*, e.id as emprestimo_id, e.retirado_em, e.devolucao_prevista, " +
                 "u.id as usuario_id, u.nome as usuario_nome, u.email as usuario_email " +
                 "FROM livro l " +
                 "JOIN emprestimo e ON e.livro_id = l.id " +
                 "JOIN usuario u ON e.usuario_id = u.id " +
                 "WHERE e.devolvido_em IS NULL " +
                 "ORDER BY l.titulo";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Object[] result = new Object[3];

            // Mapear LivroEntity
            Livro livro = livroRowMapper.mapRow(rs, rowNum);
            result[0] = livro;

            // Mapear informações básicas do empréstimo
            Object[] emprestimoInfo = new Object[3];
            emprestimoInfo[0] = rs.getLong("emprestimo_id");
            emprestimoInfo[1] = rs.getTimestamp("retirado_em").toLocalDateTime();
            emprestimoInfo[2] = rs.getTimestamp("devolucao_prevista").toLocalDateTime();
            result[1] = emprestimoInfo;

            // Mapear informações básicas do usuário
            Object[] usuarioInfo = new Object[3];
            usuarioInfo[0] = rs.getLong("usuario_id");
            usuarioInfo[1] = rs.getString("usuario_nome");
            System.out.println(usuarioInfo[1]);
            usuarioInfo[2] = rs.getString("usuario_email");
            result[2] = usuarioInfo;

            return result;
        });
    }
}