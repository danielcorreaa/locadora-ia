package com.locadora.locadoraia.application.usecase.locacao;


import com.locadora.locadoraia.domain.Locacao;

import java.util.function.Function;

public class AtualizarLocacao {
    private final Function<Locacao, Locacao> atualizarLocacao;

    public AtualizarLocacao(Function<Locacao, Locacao> atualizarLocacao) {
        this.atualizarLocacao = atualizarLocacao;
    }

    public Locacao executar(Locacao locacao) {
        return atualizarLocacao.apply(locacao);
    }
}

