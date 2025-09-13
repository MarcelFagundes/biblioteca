CREATE OR REPLACE FUNCTION fn_buscar_emprestimos_atrasados_ate_data(
    p_data_limite TIMESTAMP
)
RETURNS TABLE (
    id BIGINT,
    livro_id BIGINT,
    usuario_id BIGINT,
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP,
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
        CASE 
            WHEN e.devolucao_prevista < p_data_limite 
            THEN EXTRACT(DAY FROM (p_data_limite - e.devolucao_prevista))::INTEGER
            ELSE 0
        END,
        CASE 
            WHEN e.devolucao_prevista < p_data_limite THEN 'ATRASADO'
            ELSE 'EM_ABERTO'
        END
    FROM emprestimo e
    WHERE e.devolvido_em IS NULL 
    AND e.devolucao_prevista < p_data_limite
    ORDER BY e.devolucao_prevista ASC;
END;
$$;