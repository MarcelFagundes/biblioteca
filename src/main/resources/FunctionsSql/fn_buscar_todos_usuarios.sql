CREATE OR REPLACE FUNCTION fn_buscar_todos_usuarios()
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
    ORDER BY u.nome ASC;
END;
$$;