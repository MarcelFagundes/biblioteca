package com.bibliotecalivrosemprestimos.core.domain.model;

import java.time.LocalDateTime;

public class Emprestimo {

    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private String livroTitulo;
    private Long livroId;
    private Livro livro;
    private Usuario usuario;
    private LocalDateTime retiradoEm;
    private LocalDateTime devolucaoPrevista;
    private LocalDateTime devolvidoEm;

    // Construtores

    public Emprestimo() {
    }

    public Emprestimo(Livro livro, Usuario usuario, LocalDateTime retiradoEm,
                      LocalDateTime devolucaoPrevista) {
        this();
        this.livro = livro;
        this.usuario = usuario;
        this.retiradoEm = retiradoEm;
        this.devolucaoPrevista = devolucaoPrevista;
    }


    public Emprestimo(Long id, Long usuarioId, String livroTitulo, Long livroId,  String usuarioNome,
                      Livro livro, Usuario usuario, LocalDateTime retiradoEm, LocalDateTime devolucaoPrevista) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.livroTitulo = livroTitulo;
        this.livroId = livroId;
        this.usuarioNome = usuarioNome;
        this.livro = livro;
        this.usuario = usuario;
        this.retiradoEm = retiradoEm;
        this.devolucaoPrevista = devolucaoPrevista;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getLivroTitulo() {
        return livroTitulo;
    }

    public void setLivroTitulo(String livroTitulo) {
        this.livroTitulo = livroTitulo;
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
                ", usuarioId=" + usuarioId +
                ", usuarioNome='" + usuarioNome + '\'' +
                ", livroTitulo='" + livroTitulo + '\'' +
                ", livroId=" + livroId +
                ", livro=" + livro +
                ", usuario=" + usuario +
                ", retiradoEm=" + retiradoEm +
                ", devolucaoPrevista=" + devolucaoPrevista +
                ", devolvidoEm=" + devolvidoEm +
                '}';
    }
}