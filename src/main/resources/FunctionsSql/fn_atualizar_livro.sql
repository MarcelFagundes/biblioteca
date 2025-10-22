CREATE OR REPLACE FUNCTION fn_atualizar_livro(
    p_id BIGINT,
    p_titulo VARCHAR(200),
    p_autor VARCHAR(100),
    p_isbn VARCHAR(20),
    p_estoque INT,
    p_ativo BOOLEAN
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_rows_updated INTEGER;
BEGIN
    UPDATE livro 
    SET titulo = p_titulo,
        autor = p_autor,
        isbn = p_isbn,
        estoque = p_estoque,
        ativo = p_ativo
    WHERE id = p_id;

    GET DIAGNOSTICS v_rows_updated = ROW_COUNT;
    RETURN v_rows_updated;
END;
$$;