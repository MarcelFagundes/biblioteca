CREATE OR REPLACE FUNCTION fn_inserir_emprestimo(
    p_livro_id BIGINT,
    p_usuario_id BIGINT,
    p_retirado_em TIMESTAMP DEFAULT NULL,
    p_devolucao_prevista TIMESTAMP DEFAULT NULL,
    p_devolvido_em TIMESTAMP DEFAULT NULL
)
RETURNS BIGINT
LANGUAGE plpgsql
AS $$
DECLARE
    v_id BIGINT;
BEGIN
    IF p_retirado_em IS NULL THEN
        p_retirado_em := CURRENT_TIMESTAMP;
    END IF;
    
    IF p_devolucao_prevista IS NULL THEN
        p_devolucao_prevista := p_retirado_em + INTERVAL '7 days';
    END IF;
    
    INSERT INTO emprestimo (
        livro_id, 
        usuario_id, 
        retirado_em, 
        devolucao_prevista, 
        devolvido_em
    )
    VALUES (
        p_livro_id,
        p_usuario_id,
        p_retirado_em,
        p_devolucao_prevista,
        p_devolvido_em
    )
    RETURNING id INTO v_id;
    
    RETURN v_id;
END;
$$;