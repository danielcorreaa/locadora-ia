package com.locadora.locadoraia.application.usecase.cliente;

import com.locadora.locadoraia.domain.Cliente;

import java.util.List;
import java.util.function.Supplier;

public class ListarClientes {

    private final Supplier<List<Cliente>> listarClientes;

    public ListarClientes(Supplier<List<Cliente>> listarClientes) {
        this.listarClientes = listarClientes;
    }

    public List<Cliente> executar() {
        return listarClientes.get();
    }
}
