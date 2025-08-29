package com.locadora.locadoraia.application.usecase.locacao;


import com.locadora.locadoraia.domain.Locacao;

import java.util.List;
import java.util.function.Supplier;

public class ListarLocacoes {
    private final Supplier<List<    Locacao>> listarLocacoes;

    public ListarLocacoes(Supplier<List<Locacao>> listarLocacoes) {
        this.listarLocacoes = listarLocacoes;
    }

    public List<Locacao> executar() {
        return listarLocacoes.get();
    }
}
