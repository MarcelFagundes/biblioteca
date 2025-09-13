CREATE OR REPLACE FUNCTION fn_deletar_emprestimo(
    p_id BIGINT
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM emprestimo WHERE id = p_id;
    RETURN FOUND; 
END;
$$;