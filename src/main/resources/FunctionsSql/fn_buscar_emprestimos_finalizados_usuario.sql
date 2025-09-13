CREATE OR REPLACE FUNCTION fn_buscar_emprestimos_finalizados_usuario(
    p_usuario_id BIGINT
)
RETURNS TABLE (
    id BIGINT,
    livro_id BIGINT,
    usuario_id BIGINT,
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP,
    dias_atraso INTEGER,
    status VARCHAR(20),
    multa_paga NUMERIC
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
        CASE 
            WHEN e.devolvido_em > e.devolucao_prevista 
            THEN EXTRACT(DAY FROM (e.devolvido_em - e.devolucao_prevista))::INTEGER
            ELSE 0
        END,
        'FINALIZADO',
        CASE 
            WHEN e.devolvido_em > e.devolucao_prevista 
            THEN EXTRACT(DAY FROM (e.devolvido_em - e.devolucao_prevista)) * 2.00
            ELSE 0.00
        END
    FROM emprestimo e
    WHERE e.usuario_id = p_usuario_id 
    AND e.devolvido_em IS NOT NULL
    ORDER BY e.devolvido_em DESC;
END;
$$;