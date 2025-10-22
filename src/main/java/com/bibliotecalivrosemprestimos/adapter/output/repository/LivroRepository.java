package com.bibliotecalivrosemprestimos.adapter.output.repository;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.LivroMapper;
import com.bibliotecalivrosemprestimos.adapter.output.entity.LivroEntity;
import com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper.LivroRowMapper;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LivroRepository implements LivroOutputPort {

    private final JdbcTemplate jdbcTemplate;

    private  final LivroRowMapper livroRowMapper;

    private final LivroMapper livroMapper;

    public LivroRepository(JdbcTemplate jdbcTemplate, LivroRowMapper livroRowMapper, LivroMapper livroMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.livroRowMapper = livroRowMapper;
        this.livroMapper = livroMapper;
    }

    // RowMapper para converter ResultSet em LivroEntity
//    private final RowMapper<Livro> livroRowMapper = (rs, rowNum) -> {
//        Livro livro = new Livro();
//        livro.setId(rs.getLong("id"));
//        livro.setTitulo(rs.getString("titulo"));
//        livro.setAutor(rs.getString("autor"));
//        livro.setIsbn(rs.getString("isbn"));
//        livro.setEstoque(rs.getInt("estoque"));
//        livro.setAtivo(rs.getBoolean("ativo"));
//
//        return livro;
//    };

    @Override
    public Livro save(Livro livro) {
        LivroEntity livroEntity = livroMapper.toEntity(livro);

        String sql = "SELECT fn_inserir_livro(?, ?, ?, ?, ?)";

        try {
            Long generatedId = jdbcTemplate.queryForObject(
                    sql,
                    Long.class,
                    livroEntity.getTitulo(),
                    livroEntity.getAutor(),
                    livroEntity.getIsbn(),
                    livroEntity.getEstoque(),
                    livroEntity.getAtivo()
            );

            livroEntity.setId(generatedId);

            Livro livroNovo = livroMapper.toDomain(livroEntity);

            return livroNovo;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Livro livro) {

        LivroEntity livroEntity = livroMapper.toEntity(livro);

        String sql = "SELECT fn_atualizar_livro(?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.queryForObject(sql, Long.class,
                    livroEntity.getId(),
                    livroEntity.getTitulo(),
                    livroEntity.getAutor(),
                    livroEntity.getIsbn(),
                    livroEntity.getEstoque(),
                    livroEntity.getAtivo());
        } catch (Exception e) {
            throw new RuntimeException("Erro na atualização do livro: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Livro> findById(Long id) {
        String sql = "SELECT * FROM fn_buscar_livro_por_id(?)";
//        try {
//            Livro livro = jdbcTemplate.queryForObject(sql, livroRowMapper, id);
//            return Optional.ofNullable(livro);
//        } catch (Exception e) {
//            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
//        }
        try {
            List<LivroEntity> livroEntity = jdbcTemplate.query(sql, livroRowMapper, id);
            List<Livro> livro = livroMapper.toDomain(livroEntity);
            return livro.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> findAll() {
//        String sql = "SELECT * FROM fn_buscar_todos_livros()";
//        try {
//            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper);
//            return livro;
//        } catch (Exception e) {
//            throw new RuntimeException("Não tem livro cadastrado: " + e.getMessage(), e);
//        }
        String sql = "SELECT * FROM fn_buscar_todos_livros()";
        try {
            List<LivroEntity> livroEntity = jdbcTemplate.query(sql, livroRowMapper);
            List<Livro> livro = livroMapper.toDomain(livroEntity);
            return livro;
        } catch (Exception e) {
            throw new RuntimeException("Não tem livro cadastrado: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
//        String sql = "SELECT * FROM fn_deletar_livro_por_id(?)";
//        try {
//            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper);
//        } catch (Exception e) {
//            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
//        }
        String sql = "SELECT * FROM fn_deletar_livro_por_id(?)";
        try {
            List<LivroEntity> livroEntities = jdbcTemplate.query(sql, livroRowMapper);
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
//        String sql = "SELECT * FROM fn_buscar_livros_por_titulo(?)";
//        try {
//            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%");
//            return livro;
//        } catch (Exception e) {
//            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
//        }
        String sql = "SELECT * FROM fn_buscar_livros_por_titulo(?)";
        try {
            List<LivroEntity> livroEntity = jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%");
            List<Livro> livro = livroMapper.toDomain(livroEntity);
            return livro;
        } catch (Exception e) {
            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> findByTituloContainingAndAtivo(String titulo, boolean ativo) {
//        String sql = "SELECT * FROM fn_buscar_livros_por_titulo_e_status(? , ?)";
//        try {
//            List<Livro> livro = jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%", ativo);
//            return livro;
//        } catch (Exception e) {
//            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
//        }
        String sql = "SELECT * FROM fn_buscar_livros_por_titulo_e_status(? , ?)";
        try {
            List<LivroEntity> livroEntity = jdbcTemplate.query(sql, livroRowMapper, "%" + titulo + "%", ativo);
            List<Livro> livro = livroMapper.toDomain(livroEntity);
            return livro;
        } catch (Exception e) {
            throw new RuntimeException("Livro não encontrado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> findByAtivo(boolean ativo) {
//        String sql = "SELECT * FROM fn_buscar_livros_por_status(?)";
//
//        return jdbcTemplate.query(sql, livroRowMapper, ativo);
        String sql = "SELECT * FROM fn_buscar_livros_por_status(?)";

        List<LivroEntity> livroEntity = jdbcTemplate.query(sql, livroRowMapper, ativo);
        List<Livro> livro = livroMapper.toDomain(livroEntity);
        return livro;
    }

    @Override
    public Optional<Livro> findByIsbn(String isbn) {
//        String sql = "SELECT * FROM fn_buscar_livro_por_isbn(?)";
//        try {
//            Livro livro = jdbcTemplate.queryForObject(sql, livroRowMapper, isbn);
//            return Optional.ofNullable(livro);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
        String sql = "SELECT * FROM fn_buscar_livro_por_isbn(?)";
        try {
            LivroEntity livroEntity = jdbcTemplate.queryForObject(sql, livroRowMapper, isbn);
            Livro livro = livroMapper.toDomain(livroEntity);
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
//            Livro livro = livroRowMapper.mapRow(rs, rowNum);
//            result[0] = livro;
            LivroEntity livroEntity = livroRowMapper.mapRow(rs, rowNum);
            result[0] = livroEntity;

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