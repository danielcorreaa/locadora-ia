package com.locadora.locadoraia.application.usecase.filme;

import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;

import java.util.Optional;
import java.util.function.Function;

public class BuscarFilme {

    private final Function<String, Optional<Filme>> buscarFilme;

    public BuscarFilme(Function<String, Optional<Filme>> buscarFilme) {
        this.buscarFilme = buscarFilme;
    }

    public Optional<Filme> executar(String id) {
        return buscarFilme.apply(id);
    }
}
