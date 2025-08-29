package com.locadora.locadoraia.infrastructure.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "filmes")
public class FilmeDocument {

    @Id
    private String id;
    private String titulo;
    private String genero;
    private int anoLancamento;
    private boolean disponivel;

    public FilmeDocument() {}

    public FilmeDocument(String id, String titulo, String genero, int anoLancamento, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.disponivel = disponivel;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getAnoLancamento() { return anoLancamento; }
    public boolean isDisponivel() { return disponivel; }
}
