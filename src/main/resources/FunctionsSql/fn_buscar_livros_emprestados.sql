CREATE OR REPLACE FUNCTION fn_buscar_livros_emprestados()
RETURNS TABLE (
    livro_id BIGINT,
    livro_titulo VARCHAR(200),
    livro_autor VARCHAR(100),
    livro_isbn VARCHAR(20),
    emprestimo_id BIGINT,
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    usuario_id BIGINT,
    usuario_nome VARCHAR(100),
    usuario_email VARCHAR(100)
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        l.id as livro_id,
        l.titulo as livro_titulo,
        l.autor as livro_autor,
        l.isbn as livro_isbn,
        e.id as emprestimo_id,
        e.retirado_em,
        e.devolucao_prevista,
        u.id as usuario_id,
        u.nome as usuario_nome,
        u.email as usuario_email
    FROM livro l
    JOIN emprestimo e ON e.livro_id = l.id
    JOIN usuario u ON e.usuario_id = u.id
    WHERE e.devolvido_em IS NULL
    ORDER BY l.titulo;
END;
$$;