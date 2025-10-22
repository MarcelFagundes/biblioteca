CREATE OR REPLACE FUNCTION fn_buscar_usuario_por_email(
    p_email VARCHAR(100)
)
RETURNS TABLE (
    id BIGINT,
    nome VARCHAR,
    email VARCHAR
) AS $$
BEGIN
    RETURN QUERY
    SELECT u.id, u.nome, u.email
    FROM usuario u
    WHERE u.email = p_email;
END;
$$ LANGUAGE plpgsql;