package com.locadora.locadoraia.application.usecase.filme;

import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.domain.repository.FilmeRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public class CadastrarFilme {
    private final FilmeRepository filmeRepository;

    public CadastrarFilme(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public Filme salvar(Filme filme){
        return filmeRepository.salvar(filme);
    }
    public Optional<Filme> buscarPorId(String id){
        return filmeRepository.buscarPorId(id);
    }

    public Optional<Filme> buscarPorIdDisponivel(String id, Boolean disponivel){
        return filmeRepository.buscarPorIdDisponivel(id, disponivel);
    }
    public Page<Filme> listarTodos(int page, int size){
        return filmeRepository.listarTodos(page,size);
    }

    public Filme atualizar(Filme filme) {
        return filmeRepository.atualizar(filme);
    }
    public void deletar(String id) {
         filmeRepository.deletar(id);
    }
}

