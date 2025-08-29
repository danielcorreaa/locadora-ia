package com.locadora.locadoraia.infrastructure.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
public class ClienteDocument {
    @Id
    private String id;
    private String nome;
    private String email;
    private Boolean bloqueado;

    public ClienteDocument(String nome, String id, String email, Boolean bloqueado) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.bloqueado = bloqueado;
    }

    public ClienteDocument() {}
    public String getNome() { return nome; }
    public String getCpf() { return id; }
    public String getEmail() { return email; }

    public Boolean getBloqueado() {
        return bloqueado;
    }
}
