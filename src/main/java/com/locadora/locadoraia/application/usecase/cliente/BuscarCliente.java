package com.locadora.locadoraia.application.usecase.cliente;


import com.locadora.locadoraia.domain.Cliente;

import java.util.Optional;
import java.util.function.Function;

public class BuscarCliente {

    private final Function<String, Optional<    Cliente>> buscarCliente;

    public BuscarCliente(Function<String, Optional<Cliente>> buscarCliente) {
        this.buscarCliente = buscarCliente;
    }

    public Optional<Cliente> executar(String id) {
        return buscarCliente.apply(id);
    }
}
