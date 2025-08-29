package com.locadora.locadoraia.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Multa {
    private String id;
    private String clienteId;      // referência ao cliente
    private String locacaoId;      // referência à locação que gerou a multa
    private BigDecimal valor;
    private boolean paga;
    private LocalDate dataGeracao;

    public Multa() {}

    public Multa(String id, String clienteId, String locacaoId, BigDecimal valor, boolean paga, LocalDate dataGeracao) {
        this.id = id;
        this.clienteId = clienteId;
        this.locacaoId = locacaoId;
        this.valor = valor;
        this.paga = paga;
        this.dataGeracao = dataGeracao;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getLocacaoId() {
        return locacaoId;
    }
    public void setLocacaoId(String locacaoId) {
        this.locacaoId = locacaoId;
    }

    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isPaga() {
        return paga;
    }
    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }
    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public void pagar() {
        this.paga = true;
    }
}


