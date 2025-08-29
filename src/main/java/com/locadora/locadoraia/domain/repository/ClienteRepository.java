package com.locadora.locadoraia.domain.repository;

import com.locadora.locadoraia.domain.Cliente;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(String id);
    Optional<Cliente> buscarPorCpf(String cpf);
    Page<Cliente> listarTodos(int page, int size);
    Cliente atualizar(Cliente clienteParaAtualizar);

    void deletar(String cpf);
}
