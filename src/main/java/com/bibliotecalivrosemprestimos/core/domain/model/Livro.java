package com.bibliotecalivrosemprestimos.core.domain.model;

import java.util.Objects;

public class Livro {

    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private Integer estoque;
    private Boolean ativo;

    // Construtores
    public Livro() {
        this.ativo = true;
    }

    public Livro(String isbn, String titulo, String autor, Integer estoque) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.estoque = estoque;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setId(Long id) { this.id = id; }

    // Métodos utilitários
    public void decrementarEstoque() {
        if (estoque > 0) {
            estoque--;
        }
    }

    public void incrementarEstoque() {
        estoque++;
    }

    public void exclusaoLogica() {
        if (ativo = true) {
            ativo = false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", estoque=" + estoque +
                ", ativo=" + ativo +
                '}';
    }
}