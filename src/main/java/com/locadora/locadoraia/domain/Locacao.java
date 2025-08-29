package com.locadora.locadoraia.domain;
import com.locadora.locadoraia.exception.BusinessException;

import java.time.LocalDate;
import java.util.Objects;

public class Locacao {
    private String id;
    private Cliente cliente;
    private Filme filme;
    private LocalDate dataLocacao;
    private LocalDate dataPrevDevolucao;
    private LocalDate dataDevolucao;
    private boolean finalizada;



    public Locacao(String id, Cliente cliente, Filme filme, LocalDate dataLocacao,LocalDate dataPrevDevolucao) {
        if (!filme.isDisponivel()) {
            throw new BusinessException("Filme não está disponível para locação.");
        }
        this.id = id;
        this.cliente = cliente;
        this.filme = filme;
        this.dataLocacao = dataLocacao;
        this.dataPrevDevolucao = dataPrevDevolucao;
        this.finalizada = false;

        filme.marcarIndisponivel(); // regra de negócio: quando locar, o filme fica indisponível
    }

    public Locacao(String id, Cliente cliente, Filme filme, LocalDate dataLocacao, LocalDate dataDevolucao, boolean finalizada,LocalDate dataPrevDevolucao) {
        this.id = id;
        this.cliente = cliente;
        this.filme = filme;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.finalizada = finalizada;
        this.dataPrevDevolucao = dataPrevDevolucao;
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Filme getFilme() {
        return filme;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public LocalDate getDataPrevDevolucao() {
        return dataPrevDevolucao;
    }

    public static Locacao copy(String id, Cliente cliente, Filme filme, LocalDate dataLocacao, LocalDate dataDevolucao, boolean finalizada, LocalDate dataPrevDevolucao){
        return new Locacao(id, cliente,filme,dataLocacao,dataDevolucao,finalizada,dataPrevDevolucao);
    }

    public void devolverFilme(LocalDate dataDevolucao) {
        if (finalizada) {
            throw new IllegalStateException("Locação já foi finalizada.");
        }
        this.dataDevolucao = dataDevolucao;
        this.finalizada = true;
        filme.marcarDisponivel(); // filme volta a estar disponível
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Locacao)) return false;
        Locacao locacao = (Locacao) o;
        return Objects.equals(id, locacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
