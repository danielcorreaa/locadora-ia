package com.locadora.locadoraia.application.usecase.locacao;


import com.locadora.locadoraia.adapters.controllers.dto.LocacaoDto;
import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.domain.repository.ClienteRepository;
import com.locadora.locadoraia.domain.repository.FilmeRepository;
import com.locadora.locadoraia.domain.repository.LocacaoRepository;
import com.locadora.locadoraia.exception.BusinessException;
import com.locadora.locadoraia.exception.NotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CadastrarLocacao {

    private final LocacaoRepository locacaoRepository;
    private final ClienteRepository clienteRepository;
    private final FilmeRepository filmeRepository;

    public CadastrarLocacao(LocacaoRepository locacaoRepository, ClienteRepository clienteRepository, FilmeRepository filmeRepository) {
        this.locacaoRepository = locacaoRepository;
        this.clienteRepository = clienteRepository;
        this.filmeRepository = filmeRepository;
    }

    public List<Locacao> executarLocacao(LocacaoDto locacaoDto){
        Cliente cliente = clienteRepository
                .buscarPorId(locacaoDto.clienteId()).orElseThrow(()->
                        new NotFoundException("Cliente não encontrado"));

        List<Filme> filmes = filmeRepository.buscarPorIds(locacaoDto.filmeId());
        if(filmes.isEmpty()){
            throw new BusinessException("Nenhum Filme encontrado");
        }
        List<Locacao> locacoes = filmes.stream().map(filme -> new Locacao(new ObjectId().toString(),
                cliente, filme, locacaoDto.dataLocacao(), locacaoDto.dataPrevDevolucao())).toList();

        filmes.forEach(filmeRepository::salvar);
        return locacoes.stream().map(locacaoRepository::salvar).toList();
    }
    public Optional<Locacao> buscarPorId(String id){
        return locacaoRepository.buscarPorId(id);
    }

    public Page<Locacao> listarTodas(int page, int size,
                                     Boolean finalizado,
                                     LocalDate dataPrevDevolucao,
                                     String cpfCliente){
        return locacaoRepository.listarTodas(page, size, finalizado, dataPrevDevolucao, cpfCliente);
    }

    public List<Locacao> bulk(List<Locacao> locacoes) {
        return locacoes.stream().map(locacaoRepository::salvar).toList();
    }
}
