package com.bibliotecalivrosemprestimos.port.output;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioOutputPort {
    // Métodos CRUD básicos
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
    void deleteById(Long id);

    // Métodos específicos do domínio
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);

    // Método para buscar usuários com estatísticas de empréstimos
    List<Object[]> findUsuariosComEmprestimos();

    // Método adicional para atualização
    int update(Usuario usuario);
}