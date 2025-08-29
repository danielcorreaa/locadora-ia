package com.locadora.locadoraia.application.usecase.cliente;

import com.locadora.locadoraia.domain.Cliente;

import java.util.function.Function;

public class AtualizarCliente {

    private final Function<Cliente, Cliente> atualizarCliente;

    public AtualizarCliente(Function<Cliente, Cliente> atualizarCliente) {
        this.atualizarCliente = atualizarCliente;
    }

    public Cliente executar(Cliente cliente) {
        return atualizarCliente.apply(cliente);
    }
}
