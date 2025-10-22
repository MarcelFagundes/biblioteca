CREATE OR REPLACE FUNCTION fn_buscar_todos_livros()
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
    SELECT 	l.id,
        	l.titulo,
        	l.autor,
        	l.isbn,
			l.estoque,
			l.ativo
    FROM livro l
    ORDER BY l.id ASC;
END;
$$;