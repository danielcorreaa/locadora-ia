package com.locadora.locadoraia.application.usecase.filme;

import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

public class ListarFilmes {

    private final Supplier<List<Filme>> listarFilmes;

    public ListarFilmes(Supplier<List<Filme>> listarFilmes) {
        this.listarFilmes = listarFilmes;
    }

    public List<Filme> executar() {
        return listarFilmes.get();
    }
}
