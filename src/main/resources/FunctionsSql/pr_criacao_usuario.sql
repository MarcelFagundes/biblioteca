-- Procedure para criação de usuário
CREATE OR REPLACE PROCEDURE pr_inserir_usuario(
    IN p_nome VARCHAR(100),	
    IN p_email VARCHAR(100),
    OUT p_id BIGINT,
    OUT p_mensagem VARCHAR(200)
)
LANGUAGE plpgsql
AS $$
BEGIN
--     Verificar se email já existe
    IF EXISTS (SELECT * FROM usuario WHERE email = p_email) THEN
        p_mensagem := 'Erro: Email já cadastrado';
        p_id := NULL;
        RETURN;
    END IF;
    
    -- Inserir usuário
    INSERT INTO usuario (nome, email)
    VALUES (p_nome, p_email)
    RETURNING id INTO p_id;
    
    p_mensagem := 'Usuário inserido com sucesso';
END;
$$;