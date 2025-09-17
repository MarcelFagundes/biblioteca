package com.bibliotecalivrosemprestimos.adapter.output.entity;

import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;

import java.time.LocalDateTime;
import java.util.Objects;


public class EmprestimoEntity {

    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private String livroTitulo;
    private Long livroId;
    private LivroEntity livroEntity;
    private UsuarioEntity usuarioEntity;
    private LocalDateTime retiradoEm;
    private LocalDateTime devolucaoPrevista;
    private LocalDateTime devolvidoEm;

    // Construtores
    public EmprestimoEntity() {
    }

    public EmprestimoEntity(LivroEntity livroEntity, UsuarioEntity usuarioEntity, LocalDateTime retiradoEm,
                      LocalDateTime devolucaoPrevista) {
        this();
        this.livroEntity = livroEntity;
        this.usuarioEntity = usuarioEntity;
        this.retiradoEm = retiradoEm;
        this.devolucaoPrevista = devolucaoPrevista;
    }


    public EmprestimoEntity(Long id, Long usuarioId, String livroTitulo, Long livroId,  String usuarioNome,
                      LivroEntity livroEntity, UsuarioEntity usuarioEntity,
                            LocalDateTime retiradoEm, LocalDateTime devolucaoPrevista) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.livroTitulo = livroTitulo;
        this.livroId = livroId;
        this.usuarioNome = usuarioNome;
        this.livroEntity = livroEntity;
        this.usuarioEntity = usuarioEntity;
        this.retiradoEm = retiradoEm;
        this.devolucaoPrevista = devolucaoPrevista;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
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

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public LivroEntity getLivroEntity() {
        return livroEntity;
    }

    public void setLivroEntity(LivroEntity livroEntity) {
        this.livroEntity = livroEntity;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
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
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EmprestimoEntity{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", usuarioNome='" + usuarioNome + '\'' +
                ", livroTitulo='" + livroTitulo + '\'' +
                ", livroId=" + livroId +
                ", livro=" + livroEntity +
                ", usuario=" + usuarioEntity +
                ", retiradoEm=" + retiradoEm +
                ", devolucaoPrevista=" + devolucaoPrevista +
                ", devolvidoEm=" + devolvidoEm +
                '}';
    }
}