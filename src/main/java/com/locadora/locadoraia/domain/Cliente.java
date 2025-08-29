package com.locadora.locadoraia.domain;


import java.util.Objects;

public class Cliente {

    private String cpf;
    private String nome;
    private String email;
    private Boolean bloqueado;


    public Cliente( String nome, String cpf, String email, Boolean bloqueado) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.bloqueado = bloqueado;
    }

    public Cliente(String cpf) {
        this.cpf = cpf;
    }

    public Cliente() {
    }

    public void bloquear() {
        this.bloqueado = true;
    }
    public void desbloquear() {
        this.bloqueado = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Cliente cliente = (Cliente) object;
        return Objects.equals(cpf, cliente.cpf) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, email);
    }

}
