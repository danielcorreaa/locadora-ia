package com.locadora.locadoraia.adapters;

import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.repository.ClienteRepository;
import com.locadora.locadoraia.infrastructure.mongodb.entity.ClienteDocument;
import com.locadora.locadoraia.infrastructure.mongodb.repository.ClienteRepositoryMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final ClienteRepositoryMongo clienteRepositoryMongo;

    public ClienteRepositoryAdapter(ClienteRepositoryMongo clienteRepositoryMongo) {
        this.clienteRepositoryMongo = clienteRepositoryMongo;
    }

    private ClienteDocument toDocument(Cliente cliente) {
        return new ClienteDocument(
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),cliente.getBloqueado()
        );
    }

    private Cliente toEntity(ClienteDocument doc) {
        return new Cliente(
                doc.getNome(),
                doc.getCpf(),
                doc.getEmail(),doc.getBloqueado()
        );
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteDocument doc = toDocument(cliente);
        clienteRepositoryMongo.save(doc);
        return cliente;
    }

    @Override
    public Optional<Cliente> buscarPorId(String id) {
        return clienteRepositoryMongo.findById(id).map(this::toEntity);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepositoryMongo.findById(cpf).map(this::toEntity);
    }

    @Override
    public Page<Cliente> listarTodos(int page, int size) {
        Page<ClienteDocument> result = clienteRepositoryMongo.findAll(PageRequest.of(page, size));
        return result.map( this::toEntity);
    }

    @Override
    public Cliente atualizar(Cliente clienteParaAtualizar) {
        var cliente = clienteRepositoryMongo.save(toDocument(clienteParaAtualizar));
        return toEntity(cliente);
    }

    @Override
    public void deletar(String cpf) {
        clienteRepositoryMongo.deleteById(cpf);
    }
}
