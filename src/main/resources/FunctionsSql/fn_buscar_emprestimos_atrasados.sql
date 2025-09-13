CREATE OR REPLACE FUNCTION fn_buscar_emprestimos_atrasados()
RETURNS TABLE (
    id BIGINT,
    livro_id BIGINT,
    usuario_id BIGINT,
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP,
    dias_atraso INTEGER,
    multa_acumulada NUMERIC
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
        EXTRACT(DAY FROM (CURRENT_TIMESTAMP - e.devolucao_prevista))::INTEGER,
        EXTRACT(DAY FROM (CURRENT_TIMESTAMP - e.devolucao_prevista)) * 2.00
    FROM emprestimo e
    WHERE e.devolvido_em IS NULL 
    AND e.devolucao_prevista < CURRENT_TIMESTAMP
    ORDER BY e.devolucao_prevista ASC;
END;
$$;