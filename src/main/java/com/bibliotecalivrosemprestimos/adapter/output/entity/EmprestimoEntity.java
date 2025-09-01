package com.bibliotecalivrosemprestimos.adapter.output.entity;

import java.time.LocalDateTime;
import java.util.Objects;


public class EmprestimoEntity {

    private Long id;
    private LivroEntity livro;
    private UsuarioEntity usuario;
    private LocalDateTime retiradoEm;
    private LocalDateTime devolucaoPrevista;
    private LocalDateTime devolvidoEm;

    // Construtores
    public EmprestimoEntity() {
        this.retiradoEm = LocalDateTime.now();
    }

    public EmprestimoEntity(LivroEntity livro, UsuarioEntity usuario, LocalDateTime devolucaoPrevista) {
        this();
        this.livro = livro;
        this.usuario = usuario;
        this.devolucaoPrevista = devolucaoPrevista;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public LivroEntity getLivro() {
        return livro;
    }

    public void setLivro(LivroEntity livro) {
        this.livro = livro;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
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

    // Métodos utilitários
    public boolean isAtivo() {
        return devolvidoEm == null;
    }

    public boolean isAtrasado() {
        return isAtivo() && LocalDateTime.now().isAfter(devolucaoPrevista);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmprestimoEntity emprestimo = (EmprestimoEntity) o;
        return Objects.equals(id, emprestimo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", livro=" + livro.getTitulo() +
                ", usuario=" + usuario.getNome() +
                ", retiradoEm=" + retiradoEm +
                ", devolucaoPrevista=" + devolucaoPrevista +
                ", devolvidoEm=" + devolvidoEm +
                '}';
    }
}