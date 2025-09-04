package com.bibliotecalivrosemprestimos.core.domain.model;

import java.time.LocalDateTime;

public class Emprestimo {

    private Long id;
    private Livro livro;
    private Usuario usuario;
    private LocalDateTime retiradoEm;
    private LocalDateTime devolucaoPrevista;
    private LocalDateTime devolvidoEm;

    // Construtores

    public Emprestimo() {
    }

    public Emprestimo(Livro livro, Usuario usuario, LocalDateTime devolucaoPrevista) {
        this();
        this.livro = livro;
        this.usuario = usuario;
        this.devolucaoPrevista = devolucaoPrevista;
    }

//    public Emprestimo(Long id, Livro livro, Usuario usuario, LocalDateTime retiradoEm, LocalDateTime devolucaoPrevista, LocalDateTime devolvidoEm) {
//        this.id = id;
//        this.livro = livro;
//        this.usuario = usuario;
//        this.retiradoEm = retiradoEm;
//        this.devolucaoPrevista = devolucaoPrevista;
//        this.devolvidoEm = devolvidoEm;
//    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getRetiradoEm() {
        return retiradoEm;
    }

    public void setRetiradoEm(LocalDateTime retiradoEm) {
        this.retiradoEm = retiradoEm;
    }

    public LocalDateTime getDevolucaoPrevista() {
        return devolucaoPrevista;
    }

    public void setDevolucaoPrevista(LocalDateTime devolucaoPrevista) {
        this.devolucaoPrevista = devolucaoPrevista;
    }

    public LocalDateTime getDevolvidoEm() {
        return devolvidoEm;
    }

    public void setDevolvidoEm(LocalDateTime devolvidoEm) {
        this.devolvidoEm = devolvidoEm;
    }

    public void setId(Long id) { this.id = id; }

    // Métodos utilitários
    public boolean isAtivo() {
        return devolvidoEm == null;
    }

    public boolean isAtrasado() {
        return isAtivo() && LocalDateTime.now().isAfter(devolucaoPrevista);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", livro=" + livro +
                ", usuario=" + usuario +
                ", retiradoEm=" + retiradoEm +
                ", devolucaoPrevista=" + devolucaoPrevista +
                ", devolvidoEm=" + devolvidoEm +
                '}';
    }
}