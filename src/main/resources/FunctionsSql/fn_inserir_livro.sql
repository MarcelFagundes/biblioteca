CREATE OR REPLACE FUNCTION fn_inserir_livro(
    p_titulo VARCHAR(200),
    p_autor VARCHAR(100),
    p_isbn VARCHAR(20),
    p_estoque INT DEFAULT 1,
    p_ativo BOOLEAN DEFAULT TRUE
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_id BIGINT;
BEGIN
    INSERT INTO livro (
        titulo, 
        autor, 
        isbn, 
        estoque,  
        ativo
    )
    VALUES (
        p_titulo,
        p_autor,
        p_isbn,
        p_estoque,
        p_ativo
    )
    RETURNING id INTO v_id;
    
    RETURN v_id;
END;
$$;