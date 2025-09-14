package com.bibliotecalivrosemprestimos.adapter.output.repository;

import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LivroRepository implements LivroOutputPort {

    private final JdbcTemplate jdbcTemplate;

    public LivroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper para converter ResultSet em LivroEntity
    private final RowMapper<Livro> livroRowMapper = (rs, rowNum) -> {
        Livro livro = new Livro();
        livro.setId(rs.getLong("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setIsbn(rs.getString("isbn"));
        livro.setEstoque(rs.getInt("estoque"));
        livro.setAtivo(rs.getBoolean("ativo"));

        return livro;
    };

    @Override
    public Livro save(Livro livro) {
        String sql = "SELECT fn_inserir_livro(?, ?, ?, ?, ?)";

        try {
            Long generatedId = jdbcTemplate.queryForObject(
                    sql,
                    Long.class,
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.getEstoque(),
                    livro.getAtivo()
            );

            livro.setId(generatedId);
            return livro;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Livro livro) {
        String sql = "SELECT fn_atualizar_livro(?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.queryForObject(sql, Long.class,
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.getEstoque(),
                    livro.getAtivo());
        } catch (Exception e) {
            throw new RuntimeException("Erro na atualização do livro: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Livro> findById(Long id) {
        String sql = "SELECT * FROM fn_buscar_livro_por_id(?)";
        try {
            Livro livro = jdbcTemplate.queryForObject(sql, livroRowMapper, id);
            return Optional.ofNullable(livro);
        } catch (Exception e) {
            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> findAll() {
        String sql = "SELECT * FROM fn_buscar_todos_livros()";
        try {
            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper);
            return livro;
        } catch (Exception e) {
            throw new RuntimeException("Não tem livro cadastrado: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "SELECT * FROM fn_deletar_livro_por_id(?)";
        try {
            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper);
        } catch (Exception e) {
            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        try {
            String sql = "SELECT fn_existe_livro_por_isbn(?)";
            Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, isbn);
            return Boolean.TRUE.equals(exists);
        } catch (Exception e) {
            String fallbackSql = "SELECT fn_buscar_livro_por_isbn(?)";
            Integer count = jdbcTemplate.queryForObject(fallbackSql, Integer.class, isbn);
            return count != null && count > 0;
        }
    }

    @Override
    public List<Livro> findByTituloContaining(String titulo) {
        String sql = "SELECT * FROM fn_buscar_livros_por_titulo(?)";
        try {
            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%");
            return livro;
        } catch (Exception e) {
            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> findByTituloContainingAndAtivo(String titulo, boolean ativo) {
        String sql = "SELECT * FROM fn_buscar_livros_por_titulo_e_status(? , ?)";
        try {
            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%", ativo);
            return livro;
        } catch (Exception e) {
            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> findByAtivo(boolean ativo) {
        String sql = "SELECT * FROM fn_buscar_livros_por_status(?)";

        return jdbcTemplate.query(sql, livroRowMapper, ativo);
    }

    @Override
    public Optional<Livro> findByIsbn(String isbn) {
        String sql = "SELECT * FROM fn_buscar_livro_por_isbn(?)";
        try {
            Livro livro = jdbcTemplate.queryForObject(sql, livroRowMapper, isbn);
            return Optional.ofNullable(livro);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Object[]> findLivrosEmprestados() {

        String sql = "SELECT * FROM fn_buscar_livros_emprestados()";

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
            usuarioInfo[2] = rs.getString("usuario_email");
            result[2] = usuarioInfo;

            return result;
        });
    }
}