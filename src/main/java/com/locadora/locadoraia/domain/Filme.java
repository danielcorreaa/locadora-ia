package com.locadora.locadoraia.domain;

import java.util.Objects;

public class Filme {
    private String id;
    private String titulo;
    private String genero;
    private int anoLancamento;
    private Boolean disponivel;

    public Filme(String id, String titulo, String genero, int anoLancamento, Boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.disponivel = disponivel;
    }

    public Filme() {
    }

    public Filme(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void marcarIndisponivel() {
        this.disponivel = false;
    }

    public void marcarDisponivel() {
        this.disponivel = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filme)) return false;
        Filme filme = (Filme) o;
        return Objects.equals(id, filme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
