CREATE OR REPLACE FUNCTION fn_buscar_livro_por_isbn(p_isbn VARCHAR(20))
RETURNS TABLE (
    id BIGINT,
    titulo VARCHAR(200),
    autor VARCHAR(100),
    isbn VARCHAR(20),
    estoque INT,
    ativo BOOLEAN
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT l.*
    FROM livro l
    WHERE l.isbn = p_isbn;
END;
$$;