package com.bibliotecalivrosemprestimos.adapter.input.request;

public class UsuarioComEmprestimosRequest {
    Long id;
    String nome;
    String email;
    Long emprestimosAtivos;
    Long totalEmprestimos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public void setEmprestimosAtivos(Long emprestimosAtivos) {
        this.emprestimosAtivos = emprestimosAtivos;
    }

    public Long getTotalEmprestimos() {
        return totalEmprestimos;
    }

    public void setTotalEmprestimos(Long totalEmprestimos) {
        this.totalEmprestimos = totalEmprestimos;
    }
}

//public record UsuarioComEmprestimosRequest(
//        Long id,
//        String nome,
//        String email,
//        Long emprestimosAtivos,
//        Long totalEmprestimos
//) {}