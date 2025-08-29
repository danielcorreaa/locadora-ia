package com.locadora.locadoraia.application.usecase.locacao;

import java.util.function.Consumer;

public class DeletarLocacao {
    private final Consumer<String> deletarLocacao;

    public DeletarLocacao(Consumer<String> deletarLocacao) {
        this.deletarLocacao = deletarLocacao;
    }

    public void executar(String id) {
        deletarLocacao.accept(id);
    }
}
