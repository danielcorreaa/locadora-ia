package com.locadora.locadoraia.adapters.controllers;


import com.locadora.locadoraia.adapters.FilmeRepositoryAdapter;
import com.locadora.locadoraia.application.usecase.filme.CadastrarFilme;
import com.locadora.locadoraia.core.PageResponse;
import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Endpoints para gerenciamento de filmes")
public class FilmeController {

    private final FilmeRepositoryAdapter filmeRepositoryAdapter;
    private CadastrarFilme cadastrarFilme;

    public FilmeController(FilmeRepositoryAdapter filmeRepositoryAdapter) {
        this.filmeRepositoryAdapter = filmeRepositoryAdapter;
    }


    @Operation(summary = "Cadastrar filme", description = "Cria um novo filme")
    @PostMapping
    public Filme cadastrar(@RequestBody Filme filme) {
        cadastrarFilme  = new CadastrarFilme(filmeRepositoryAdapter);
        return cadastrarFilme.salvar(filme);
    }

    @Operation(summary = "Cadastrar filme", description = "Cria um novo filme em Lote")
    @PostMapping("bulk")
    public List<Filme> cadastrar(@RequestBody List<Filme> filmes) {
        cadastrarFilme  = new CadastrarFilme(filmeRepositoryAdapter);
        return filmes.stream().map(filme ->  cadastrarFilme.salvar(filme)).toList();
    }

    @Operation(summary = "Buscar filme", description = "Busca um filme pelo ID")
    @GetMapping("/{id}")
    public PageResponse<Filme> buscar(@PathVariable String id, @RequestParam(required = false) Boolean disponivel) {
        cadastrarFilme  = new CadastrarFilme(filmeRepositoryAdapter);
        if(null == disponivel){
            return PageResponse.from(cadastrarFilme.buscarPorId(id).orElseThrow(() -> new NotFoundException("Filme não encontrado")));
        }
        return PageResponse.from(cadastrarFilme.buscarPorIdDisponivel(id,disponivel).orElseThrow(() -> new NotFoundException("Filme não encontrado")));
    }

    @Operation(summary = "Listar filmes", description = "Lista todos os filmes")
    @GetMapping
    public PageResponse<Filme> listar(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size) {
        cadastrarFilme  = new CadastrarFilme(filmeRepositoryAdapter);
        return PageResponse.from(cadastrarFilme.listarTodos(page,size));
    }

    @Operation(summary = "Atualizar filme", description = "Atualiza os dados de um filme existente")
    @PutMapping("/{id}")
    public Filme atualizar(@PathVariable String id, @RequestBody Filme filmeRequest) {
        cadastrarFilme  = new CadastrarFilme(filmeRepositoryAdapter);
        Filme filme = cadastrarFilme.buscarPorId(id).orElseThrow(() -> new NotFoundException("Filme não encontrado!"));
        filme.setAnoLancamento(filmeRequest.getAnoLancamento());
        filme.setGenero(filmeRequest.getGenero());
        filme.setTitulo(filmeRequest.getTitulo());
        return cadastrarFilme.atualizar(filme);
    }

    @Operation(summary = "Deletar filme", description = "Remove um filme")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable String id) {
        cadastrarFilme  = new CadastrarFilme(filmeRepositoryAdapter);
        Filme filme = cadastrarFilme.buscarPorId(id).orElseThrow(() -> new NotFoundException("Filme não encontrado!"));
        cadastrarFilme.deletar(id);
        return ResponseEntity.ok("Excluído com sucesso!");
    }
}
