-- Método para buscar usuários com empréstimos
CREATE OR REPLACE FUNCTION fn_usuarios_com_emprestimos()
RETURNS TABLE (
    id BIGINT,
    nome VARCHAR(100),
    email VARCHAR(100),
    total_emprestimos BIGINT,
    emprestimos_ativos BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        u.id as usuario_id,
        u.nome as usuario_nome,
        u.email as usuario_email,
        COUNT(e.id)::BIGINT,
        SUM(CASE WHEN e.devolvido_em IS NULL THEN 1 ELSE 0 END)::BIGINT
    FROM usuario u
    LEFT JOIN emprestimo e ON e.usuario_id = u.id
    GROUP BY u.id, u.nome, u.email
    ORDER BY u.nome;
END;
$$ LANGUAGE plpgsql;