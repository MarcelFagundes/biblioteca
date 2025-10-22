CREATE OR REPLACE FUNCTION fn_emprestimos_por_usuario(
    p_usuario_id BIGINT
)
RETURNS TABLE (
    emprestimo_id BIGINT,
    livro_id BIGINT,
    livro_titulo VARCHAR(200),
    livro_autor VARCHAR(100),
    usuario_id BIGINT,
    usuario_nome VARCHAR(100),
    usuario_email VARCHAR(100),
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP,
    status VARCHAR(20),
    dias_atraso INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        e.id,
        l.id,
        l.titulo,
        l.autor,
        u.id,
        u.nome,
        u.email,
        e.retirado_em,
        e.devolucao_prevista,
        e.devolvido_em,
        CASE 
            WHEN e.devolvido_em IS NOT NULL THEN 'DEVOLVIDO'
            WHEN e.devolucao_prevista < CURRENT_TIMESTAMP THEN 'ATRASADO'
            ELSE 'EM_ABERTO'
        END,
        CASE 
            WHEN e.devolvido_em IS NULL AND e.devolucao_prevista < CURRENT_TIMESTAMP 
            THEN EXTRACT(DAY FROM (CURRENT_TIMESTAMP - e.devolucao_prevista))
            ELSE 0
        END
    FROM emprestimo e
    INNER JOIN livro l ON e.livro_id = l.id
    INNER JOIN usuario u ON e.usuario_id = u.id
    WHERE e.usuario_id = p_usuario_id
    ORDER BY e.retirado_em DESC;
END;
$$;