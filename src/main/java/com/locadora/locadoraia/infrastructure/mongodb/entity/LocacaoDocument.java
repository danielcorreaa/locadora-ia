package com.locadora.locadoraia.infrastructure.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "locacoes")
public class LocacaoDocument {
    @Id
    private String id;
    private String clienteId;
    private String filmeId;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private LocalDate dataPrevDevolucao;
    private boolean finalizada;


    public LocacaoDocument() {}

    public LocacaoDocument(String id, String clienteId, String filmeId, LocalDate dataLocacao, LocalDate dataDevolucao, boolean finalizada,LocalDate dataPrevDevolucao) {
        this.id = id;
        this.clienteId = clienteId;
        this.filmeId = filmeId;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.finalizada = finalizada;
        this.dataPrevDevolucao = dataPrevDevolucao;
    }

    public String getId() { return id; }
    public String getClienteId() { return clienteId; }
    public String getFilmeId() { return filmeId; }
    public LocalDate getDataLocacao() { return dataLocacao; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }

    public LocalDate getDataPrevDevolucao() {
        return dataPrevDevolucao;
    }

    public boolean isFinalizada() {
        return finalizada;
    }
}

