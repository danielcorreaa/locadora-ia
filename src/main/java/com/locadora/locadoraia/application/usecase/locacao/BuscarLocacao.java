package com.locadora.locadoraia.application.usecase.locacao;


import com.locadora.locadoraia.domain.Locacao;

import java.util.Optional;
import java.util.function.Function;

public class BuscarLocacao {
    private final Function<String, Optional<Locacao>> buscarLocacao;

    public BuscarLocacao(Function<String, Optional<Locacao>> buscarLocacao) {
        this.buscarLocacao = buscarLocacao;
    }

    public Optional<Locacao> executar(String id) {
        return buscarLocacao.apply(id);
    }
}
