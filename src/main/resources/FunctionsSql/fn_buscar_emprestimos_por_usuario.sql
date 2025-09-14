CREATE OR REPLACE FUNCTION fn_buscar_emprestimos_por_usuario(
    p_usuario_id BIGINT
)
RETURNS TABLE (
    id BIGINT,
    livro_id BIGINT,
    usuario_id BIGINT,
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP,
    livro_titulo VARCHAR(200),
    usuario_nome VARCHAR(100)
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        e.id,
        e.livro_id,
        e.usuario_id,
        e.retirado_em,
        e.devolucao_prevista,
        e.devolvido_em,
        l.titulo,
        u.nome
    FROM emprestimo e
--    LEFT JOIN livro l ON e.livro_id = l.id
--    LEFT JOIN usuario u ON e.usuario_id = u.id
    WHERE e.usuario_id = p_usuario_id
    ORDER BY e.retirado_em DESC;
END;
$$;