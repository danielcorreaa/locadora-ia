package com.locadora.locadoraia.application.usecase.cliente;

import java.util.function.Consumer;

public class DeletarCliente {

    private final Consumer<String> deletarCliente;

    public DeletarCliente(Consumer<String> deletarCliente) {
        this.deletarCliente = deletarCliente;
    }

    public void executar(String id) {
        deletarCliente.accept(id);
    }
}
