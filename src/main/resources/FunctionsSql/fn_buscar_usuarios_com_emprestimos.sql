CREATE OR REPLACE FUNCTION fn_buscar_usuarios_com_emprestimos()
RETURNS TABLE (
    id BIGINT,
    nome VARCHAR(100),
    email VARCHAR(100),
    total_emprestimos BIGINT,
    emprestimos_ativos BIGINT,
    emprestimos_finalizados BIGINT,
    emprestimos_atrasados BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        u.id,
        u.nome,
        u.email,
        COUNT(e.id)::BIGINT,
        SUM(CASE WHEN e.devolvido_em IS NULL THEN 1 ELSE 0 END)::BIGINT,
        SUM(CASE WHEN e.devolvido_em IS NOT NULL THEN 1 ELSE 0 END)::BIGINT,
        SUM(CASE WHEN e.devolvido_em IS NULL AND e.devolucao_prevista < CURRENT_TIMESTAMP THEN 1 ELSE 0 END)::BIGINT
    FROM usuario u
    LEFT JOIN emprestimo e ON e.usuario_id = u.id
    GROUP BY u.id, u.nome, u.email
    ORDER BY u.nome;
END;
$$;