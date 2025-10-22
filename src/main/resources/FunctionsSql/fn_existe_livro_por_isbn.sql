CREATE OR REPLACE FUNCTION fn_existe_livro_por_isbn(p_isbn VARCHAR(20))
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE
    v_count INT;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM livro 
    WHERE isbn = p_isbn;
    
    RETURN v_count > 0;
END;
$$;