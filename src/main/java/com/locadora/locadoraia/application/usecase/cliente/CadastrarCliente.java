package com.locadora.locadoraia.application.usecase.cliente;


import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.repository.ClienteRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CadastrarCliente {

    private ClienteRepository clienteRepository;

    public CadastrarCliente(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente salvar(Cliente cliente){
        return clienteRepository.salvar(cliente);
    }
    public Optional<Cliente> buscarPorId(String id){
        return clienteRepository.buscarPorId(id);
    }
    public Optional<Cliente> buscarPorCpf(String cpf){
        return clienteRepository.buscarPorCpf(cpf);
    }
    public Page<Cliente> listarTodos(int page, int size){
        return clienteRepository.listarTodos(page,size);
    }

    public Cliente atualizar(Cliente clienteParaAtualizar) {
        return clienteRepository.atualizar(clienteParaAtualizar);
    }

    public void deletar(String cpf) {
        clienteRepository.deletar(cpf);
    }
}

