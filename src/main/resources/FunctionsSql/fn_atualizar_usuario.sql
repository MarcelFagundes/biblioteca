CREATE OR REPLACE FUNCTION fn_atualizar_usuario(
    p_id BIGINT,
    p_nome VARCHAR(100),
    p_email VARCHAR(100)
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_rows_updated INTEGER;
BEGIN
    UPDATE usuario 
    SET 
        nome = p_nome,
        email = p_email
    WHERE id = p_id;
    
    GET DIAGNOSTICS v_rows_updated = ROW_COUNT;
    RETURN v_rows_updated;
END;
$$;