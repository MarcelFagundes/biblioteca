CREATE OR REPLACE FUNCTION fn_deletar_usuario(
    p_id BIGINT
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM usuario WHERE id = p_id;
    RETURN FOUND;
END;
$$;