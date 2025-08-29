package com.locadora.locadoraia.application.usecase.filme;

import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;

import java.util.function.Function;

public class AtualizarFilme {
    private final Function<Filme, Filme> atualizarFilme;

    public AtualizarFilme(Function<Filme, Filme> atualizarFilme) {
        this.atualizarFilme = atualizarFilme;
    }

    public Filme executar(Filme filme) {
        return atualizarFilme.apply(filme);
    }
}
