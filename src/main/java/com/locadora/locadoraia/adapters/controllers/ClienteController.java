package com.locadora.locadoraia.adapters.controllers;

import com.locadora.locadoraia.adapters.ClienteRepositoryAdapter;
import com.locadora.locadoraia.application.usecase.cliente.*;
import com.locadora.locadoraia.core.PageResponse;
import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.exception.NotFoundException;
import com.locadora.locadoraia.infrastructure.mongodb.entity.ClienteDocument;
import com.locadora.locadoraia.infrastructure.mongodb.repository.ClienteRepositoryMongo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes da locadora")
public class ClienteController {

    private final ClienteRepositoryAdapter clienteRepositoryAdapter;
    private CadastrarCliente cadastrarCliente;

    public ClienteController(ClienteRepositoryAdapter clienteRepositoryAdapter) {
        this.clienteRepositoryAdapter = clienteRepositoryAdapter;
    }

    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        this.cadastrarCliente = new CadastrarCliente(clienteRepositoryAdapter);
        return cadastrarCliente.salvar(cliente);
    }
    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente no sistema em Lote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping("bulk")
    public List<Cliente> cadastrar(@RequestBody List<Cliente> clientes) {
        this.cadastrarCliente = new CadastrarCliente(clienteRepositoryAdapter);
        return clientes.stream().map(cliente -> cadastrarCliente.salvar(cliente)).toList();
    }

    @Operation(summary = "Buscar cliente", description = "Busca um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public Optional<Cliente> buscar(@PathVariable String id) {
        this.cadastrarCliente = new CadastrarCliente(clienteRepositoryAdapter);
        return cadastrarCliente.buscarPorId(id);
    }

    @Operation(summary = "Listar clientes", description = "Lista todos os clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })

    @GetMapping
    public PageResponse<Cliente> listar(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        this.cadastrarCliente = new CadastrarCliente(clienteRepositoryAdapter);
        return PageResponse.from(cadastrarCliente.listarTodos(page, size));
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{cpf}")
    public Cliente atualizar(@PathVariable String cpf, @RequestBody Cliente cliente) {
        this.cadastrarCliente = new CadastrarCliente(clienteRepositoryAdapter);
        var clienteParaAtualizar = cadastrarCliente.buscarPorId(cpf).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        clienteParaAtualizar.setEmail(cliente.getEmail());
        clienteParaAtualizar.setNome(cliente.getNome());
        return cadastrarCliente.atualizar(clienteParaAtualizar);
    }

    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletar(@PathVariable String cpf) {
        this.cadastrarCliente = new CadastrarCliente(clienteRepositoryAdapter);
        var clienteParaAtualizar = cadastrarCliente.buscarPorId(cpf).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        cadastrarCliente.deletar(cpf);
        return ResponseEntity.ok("Excluído com sucesso!");
    }
}

