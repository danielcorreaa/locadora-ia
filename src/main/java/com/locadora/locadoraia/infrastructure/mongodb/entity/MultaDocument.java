package com.locadora.locadoraia.infrastructure.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "multas")
public class MultaDocument {

    @Id
    private String id;
    private String clienteId;
    private String locacaoId;
    private BigDecimal valor;
    private boolean paga;
    private LocalDate dataGeracao;

    public MultaDocument() {}

    public MultaDocument(String id, String clienteId, String locacaoId,
                         BigDecimal valor, boolean paga, LocalDate dataGeracao) {
        this.id = id;
        this.clienteId = clienteId;
        this.locacaoId = locacaoId;
        this.valor = valor;
        this.paga = paga;
        this.dataGeracao = dataGeracao;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public String getLocacaoId() { return locacaoId; }
    public void setLocacaoId(String locacaoId) { this.locacaoId = locacaoId; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public boolean isPaga() { return paga; }
    public void setPaga(boolean paga) { this.paga = paga; }

    public LocalDate getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDate dataGeracao) { this.dataGeracao = dataGeracao; }
}

