CREATE OR REPLACE FUNCTION fn_count_emprestimos_ativos(
    p_livro_id BIGINT,
    p_usuario_id BIGINT
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM emprestimo 
    WHERE livro_id = p_livro_id 
      AND usuario_id = p_usuario_id 
      AND devolvido_em IS NULL;
    
    RETURN v_count;
END;
$$;