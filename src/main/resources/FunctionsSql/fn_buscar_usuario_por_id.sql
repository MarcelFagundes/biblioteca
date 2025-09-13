CREATE OR REPLACE FUNCTION fn_buscar_usuario_por_id(
    p_id BIGINT
)
RETURNS TABLE (
    id BIGINT,
    nome VARCHAR(100),
    email VARCHAR(100)
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        u.id,
        u.nome,
        u.email
    FROM usuario u
    WHERE u.id = p_id;
END;
$$;