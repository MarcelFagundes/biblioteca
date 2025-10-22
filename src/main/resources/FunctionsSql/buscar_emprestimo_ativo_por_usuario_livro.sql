-- Função para buscar empréstimo ativo por usuário e livro
CREATE OR REPLACE FUNCTION buscar_emprestimo_ativo_por_usuario_livro(
    p_usuario_id BIGINT,
    p_livro_id BIGINT
)
RETURNS TABLE(
    id BIGINT,
    usuario_id BIGINT,
    livro_id BIGINT,
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT e.id, e.usuario_id, e.livro_id, e.retirado_em, 
           e.devolucao_prevista, e.devolvido_em
    FROM emprestimo e
    WHERE e.usuario_id = p_usuario_id 
    AND e.livro_id = p_livro_id
	AND e.devolvido_em IS NULL
    LIMIT 1;
END;
$$ LANGUAGE plpgsql;