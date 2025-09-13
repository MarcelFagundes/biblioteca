CREATE OR REPLACE FUNCTION fn_buscar_emprestimos_finalizados()
RETURNS TABLE (
    id BIGINT,
    livro_id BIGINT,
    usuario_id BIGINT,
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP,
    dias_emprestimo INTEGER,
    dias_atraso INTEGER,
    status VARCHAR(20)
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
        EXTRACT(DAY FROM (e.devolvido_em - e.retirado_em))::INTEGER,
        CASE 
            WHEN e.devolvido_em > e.devolucao_prevista 
            THEN EXTRACT(DAY FROM (e.devolvido_em - e.devolucao_prevista))::INTEGER
            ELSE 0
        END,
        'FINALIZADO'
    FROM emprestimo e
    WHERE e.devolvido_em IS NOT NULL
    ORDER BY e.devolvido_em DESC;
END;
$$;