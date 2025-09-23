CREATE OR REPLACE FUNCTION fn_inserir_emprestimo(
    p_livro_id BIGINT,
    p_usuario_id BIGINT,
    p_retirado_em TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
    p_devolucao_prevista TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL,
    p_devolvido_em TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL
)
RETURNS BIGINT
LANGUAGE plpgsql
AS $$
DECLARE
    v_id BIGINT;
    v_livro_exists BOOLEAN;
    v_usuario_exists BOOLEAN;
BEGIN
    -- Verificar se livro existe
    SELECT EXISTS(SELECT 1 FROM livro WHERE id = p_livro_id) INTO v_livro_exists;
    IF NOT v_livro_exists THEN
        RAISE EXCEPTION 'Livro com ID % não existe', p_livro_id;
    END IF;
    
    -- Verificar se usuário existe
    SELECT EXISTS(SELECT 1 FROM usuario WHERE id = p_usuario_id) INTO v_usuario_exists;
    IF NOT v_usuario_exists THEN
        RAISE EXCEPTION 'Usuário com ID % não existe', p_usuario_id;
    END IF;
    
    -- Validação dos parâmetros obrigatórios
    IF p_livro_id IS NULL THEN
        RAISE EXCEPTION 'livro_id não pode ser nulo';
    END IF;
    
    IF p_usuario_id IS NULL THEN
        RAISE EXCEPTION 'usuario_id não pode ser nulo';
    END IF;
    
    -- Definição de valores padrão
    IF p_retirado_em IS NULL THEN
        p_retirado_em := CURRENT_TIMESTAMP;
    END IF;
    
    IF p_devolucao_prevista IS NULL THEN
        p_devolucao_prevista := p_retirado_em + INTERVAL '7 days';
    END IF;
    
    -- Inserção
    INSERT INTO emprestimo (
        livro_id, 
        usuario_id, 
        retirado_em, 
        devolucao_prevista, 
        devolvido_em
    )
    VALUES (
        p_livro_id,
        p_usuario_id,
        p_retirado_em,
        p_devolucao_prevista,
        p_devolvido_em
    )
    RETURNING id INTO v_id;
    
    RETURN v_id;
END;
$$;
--CREATE OR REPLACE FUNCTION fn_inserir_emprestimo(
--    p_livro_id BIGINT,
--    p_usuario_id BIGINT,
--    p_retirado_em TIMESTAMP DEFAULT NULL,
--    p_devolucao_prevista TIMESTAMP DEFAULT NULL,
--    p_devolvido_em TIMESTAMP DEFAULT NULL
--)
--RETURNS BIGINT
--LANGUAGE plpgsql
--AS $$
--DECLARE
--    v_id BIGINT;
--BEGIN
--    -- Validação dos parâmetros obrigatórios
--    IF p_livro_id IS NULL THEN
--        RAISE EXCEPTION 'livro_id não pode ser nulo';
--    END IF;
--    
--    IF p_usuario_id IS NULL THEN
--        RAISE EXCEPTION 'usuario_id não pode ser nulo';
--    END IF;
--    
--    -- Definição de valores padrão
--    IF p_retirado_em IS NULL THEN
--        p_retirado_em := CURRENT_TIMESTAMP;
--    END IF;
--    
--    IF p_devolucao_prevista IS NULL THEN
--        p_devolucao_prevista := p_retirado_em + INTERVAL '7 days';
--    END IF;
--    
--    -- Inserção com validação adicional
--    INSERT INTO emprestimo (
--        livro_id, 
--        usuario_id, 
--        retirado_em, 
--        devolucao_prevista, 
--        devolvido_em
--    )
--    VALUES (
--        p_livro_id,
--        p_usuario_id,
--        p_retirado_em,
--        p_devolucao_prevista,
--        p_devolvido_em
--    )
--    RETURNING id INTO v_id;
--    
--    RETURN v_id;
--END;
--$$;
--
--
--
--
----CREATE OR REPLACE FUNCTION fn_inserir_emprestimo(
----    p_livro_id BIGINT,
----    p_usuario_id BIGINT,
----    p_retirado_em TIMESTAMP DEFAULT NULL,
----    p_devolucao_prevista TIMESTAMP DEFAULT NULL,
----    p_devolvido_em TIMESTAMP DEFAULT NULL
----)
----RETURNS BIGINT
----LANGUAGE plpgsql
----AS $$
----DECLARE
----    v_id BIGINT;
----BEGIN
----    IF p_retirado_em IS NULL THEN
----        p_retirado_em := CURRENT_TIMESTAMP;
----    END IF;
----    
----    IF p_devolucao_prevista IS NULL THEN
----        p_devolucao_prevista := p_retirado_em + INTERVAL '7 days';
----    END IF;
----    
----    INSERT INTO emprestimo (
----        livro_id, 
----        usuario_id, 
----        retirado_em, 
----        devolucao_prevista, 
----        devolvido_em
----    )
----    VALUES (
----        p_livro_id,
----        p_usuario_id,
----        p_retirado_em,
----        p_devolucao_prevista,
----        p_devolvido_em
----    )
----    RETURNING id INTO v_id;
----    
----    RETURN v_id;
----END;
----$$;