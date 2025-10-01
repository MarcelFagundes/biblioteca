-- PROCEDURE que não retorna valor
CREATE OR REPLACE PROCEDURE sp_inserir_emprestimo(
    p_livro_id BIGINT,
    p_usuario_id BIGINT,
    p_retirado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
    p_devolucao_prevista TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
    p_devolvido_em TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO emprestimo (livro_id, usuario_id, retirado_em, devolucao_prevista, devolvido_em)
    VALUES (p_livro_id, p_usuario_id, p_retirado_em, p_devolucao_prevista, p_devolvido_em);
END;
$$;