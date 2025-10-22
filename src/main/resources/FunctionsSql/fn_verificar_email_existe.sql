CREATE OR REPLACE FUNCTION fn_verificar_email_existe(
    p_email VARCHAR(100)
)
RETURNS VARCHAR
LANGUAGE plpgsql
AS $$
DECLARE
    v_email VARCHAR(100);
BEGIN
    SELECT email
    INTO v_email
    FROM usuario 
    WHERE email = p_email;

    RETURN v_email;
END;
$$;