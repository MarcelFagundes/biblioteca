CREATE OR REPLACE FUNCTION fn_deletar_livro_por_id(p_id BIGINT)
RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM livro WHERE id = p_id;
END;
$$;