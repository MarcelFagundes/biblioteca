-- Função para buscar todos os empréstimos
CREATE OR REPLACE FUNCTION fn_emprestimos_completas()
RETURNS TABLE (
    id BIGINT,
    livro_id BIGINT,
    livro_titulo VARCHAR(200),
    usuario_id BIGINT,
    usuario_nome VARCHAR(100),
    retirado_em TIMESTAMP,
    devolucao_prevista TIMESTAMP,
    devolvido_em TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        e.id,
        e.livro_id,
        l.titulo,
        e.usuario_id,
        u.nome,
        e.retirado_em,
        e.devolucao_prevista,
        e.devolvido_em
    FROM emprestimo e
    LEFT JOIN livro l ON e.livro_id = l.id
    LEFT JOIN usuario u ON e.usuario_id = u.id
    ORDER BY e.retirado_em DESC;
END;
$$ LANGUAGE plpgsql;