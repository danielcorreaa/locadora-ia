package com.locadora.locadoraia.adapters.controllers;


import com.locadora.locadoraia.adapters.controllers.dto.DevolucaoDto;
import com.locadora.locadoraia.adapters.ClienteRepositoryAdapter;
import com.locadora.locadoraia.adapters.FilmeRepositoryAdapter;
import com.locadora.locadoraia.adapters.LocacaoRepositoryAdapter;
import com.locadora.locadoraia.adapters.MultaRepositoryAdapter;
import com.locadora.locadoraia.adapters.controllers.dto.LocacaoDto;
import com.locadora.locadoraia.adapters.controllers.dto.LocacaoRequestDto;
import com.locadora.locadoraia.adapters.controllers.mapper.LocacaoMapper;
import com.locadora.locadoraia.application.usecase.locacao.*;
import com.locadora.locadoraia.core.PageResponse;
import com.locadora.locadoraia.domain.Locacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locacoes")
@Tag(name = "Locações", description = "Endpoints para gerenciamento de locações")
public class LocacaoController {

    private final LocacaoRepositoryAdapter locacaoRepositoryAdapter;
    private final FilmeRepositoryAdapter filmeRepositoryAdapter;
    private final ClienteRepositoryAdapter clienteRepositoryAdapter;
    private final MultaRepositoryAdapter multaRepositoryAdapter;
    private CadastrarLocacao cadastrarLocacao;

    public LocacaoController(LocacaoRepositoryAdapter locacaoRepositoryAdapter, FilmeRepositoryAdapter filmeRepositoryAdapter, ClienteRepositoryAdapter clienteRepositoryAdapter, MultaRepositoryAdapter multaRepositoryAdapter) {
        this.locacaoRepositoryAdapter = locacaoRepositoryAdapter;
        this.filmeRepositoryAdapter = filmeRepositoryAdapter;
        this.clienteRepositoryAdapter = clienteRepositoryAdapter;
        this.multaRepositoryAdapter = multaRepositoryAdapter;
    }


    @Operation(summary = "Cadastrar locação", description = "Cria uma nova locação")
    @PostMapping
    public List<Locacao> cadastrar(@RequestBody LocacaoDto locacaoDto) {
        cadastrarLocacao = new CadastrarLocacao(locacaoRepositoryAdapter,
                clienteRepositoryAdapter, filmeRepositoryAdapter);
        return cadastrarLocacao.executarLocacao(locacaoDto);
    }

    @Operation(summary = "Cadastrar locação", description = "Cria uma nova locação")
    @PostMapping("/bulk")
    public List<Locacao> cadastrar(@RequestBody List<LocacaoRequestDto> locacoes) {
        cadastrarLocacao = new CadastrarLocacao(locacaoRepositoryAdapter,
                clienteRepositoryAdapter, filmeRepositoryAdapter);
        var loc = locacoes.stream().map(LocacaoMapper::toEntity).toList();
        return cadastrarLocacao.bulk(loc);
    }


    @Operation(summary = "Devolver locação", description = "Terminar uma nova locação")
    @PostMapping("devolver")
    public Locacao devolver(@RequestBody DevolucaoDto devolucaoDto) {
        DevolucaoLocacao devolucaoLocacao = new DevolucaoLocacao(locacaoRepositoryAdapter, multaRepositoryAdapter);
        return devolucaoLocacao.devolverFilme(devolucaoDto);
    }

    @Operation(summary = "Buscar locação", description = "Busca uma locação pelo ID")
    @GetMapping("/{id}")
    public Optional<Locacao> buscar(@PathVariable String id) {
        cadastrarLocacao = new CadastrarLocacao(locacaoRepositoryAdapter,
                clienteRepositoryAdapter, filmeRepositoryAdapter);
        return cadastrarLocacao.buscarPorId(id);
    }

    @GetMapping
    public PageResponse<Locacao> listar(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size,
                                        @RequestParam(required = false) Boolean finalizado,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPrevDevolucao,
                                        @RequestParam(required = false) String cpfCliente) {
        cadastrarLocacao = new CadastrarLocacao(
                locacaoRepositoryAdapter,
                clienteRepositoryAdapter,
                filmeRepositoryAdapter
        );

        return PageResponse.from(
                cadastrarLocacao.listarTodas(page, size, finalizado, dataPrevDevolucao, cpfCliente)
        );
    }


    @Operation(summary = "Atualizar locação", description = "Atualiza os dados de uma locação existente")
    @PutMapping("/{id}")
    public Locacao atualizar(@PathVariable String id, @RequestBody Locacao locacao) {
        //locacao.setId(id);
        //return atualizarLocacao.executar(locacao);
        return null;
    }

    @Operation(summary = "Deletar locação", description = "Remove uma locação")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) { }
}
