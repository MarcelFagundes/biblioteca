CREATE OR REPLACE FUNCTION fn_atualizar_emprestimo(
    p_id BIGINT,
    p_livro_id BIGINT,
    p_usuario_id BIGINT,
    p_retirado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
    p_devolucao_prevista TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
    p_devolvido_em TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL
)
RETURNS BIGINT
LANGUAGE plpgsql
AS $$
DECLARE
    v_rows_updated INTEGER;
BEGIN
    UPDATE emprestimo 
    SET 
        livro_id = p_livro_id,
        usuario_id = p_usuario_id,
        retirado_em = COALESCE(p_retirado_em, retirado_em),
        devolucao_prevista = COALESCE(p_devolucao_prevista, devolucao_prevista),
        devolvido_em = COALESCE(p_devolvido_em, devolvido_em)
    WHERE id = p_id;

	GET DIAGNOSTICS v_rows_updated = ROW_COUNT;
	RETURN v_rows_updated;
END;
$$;