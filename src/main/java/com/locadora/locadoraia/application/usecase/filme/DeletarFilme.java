package com.locadora.locadoraia.application.usecase.filme;

import java.util.function.Consumer;

public class DeletarFilme {

    private final Consumer<String> deletarFilme;

    public DeletarFilme(Consumer<String> deletarFilme) {
        this.deletarFilme = deletarFilme;
    }

    public void executar(String id) {
        deletarFilme.accept(id);
    }
}
