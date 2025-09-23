package com.bibliotecalivrosemprestimos.factory;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;

public class EmprestimoFactory {

    public static Usuario buildUsuarioBasico() {
        return Usuario.builder()
                .nome("João Silva")
                .email("joao.silva@email.com")
                .cpf("123.456.789-00")
                .build();
    }

    public static Usuario buildUsuarioCompleto() {
        return Usuario.builder()
                .id("1")
                .nome("Maria Santos")
                .email("maria.santos@email.com")
                .cpf("987.654.321-00")
                .build();
    }

    public static Usuario buildUsuarioComId(String id) {
        return Usuario.builder()
                .id(id)
                .nome("Carlos Oliveira")
                .email("carlos.oliveira@email.com")
                .cpf("111.222.333-44")
                .build();
    }

    public static Usuario buildUsuarioComEmail(String email) {
        return Usuario.builder()
                .nome("Ana Costa")
                .email(email)
                .cpf("555.666.777-88")
                .build();
    }

    public static Usuario buildUsuarioComCpf(String cpf) {
        return Usuario.builder()
                .nome("Pedro Almeida")
                .email("pedro.almeida@email.com")
                .cpf(cpf)
                .build();
    }
}
