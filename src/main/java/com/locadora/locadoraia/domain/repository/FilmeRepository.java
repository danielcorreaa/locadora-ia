package com.locadora.locadoraia.domain.repository;

import com.locadora.locadoraia.domain.Filme;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository {
    Filme salvar(Filme filme);
    Optional<Filme> buscarPorId(String id);
    Optional<Filme> buscarPorIdDisponivel(String id,Boolean disponivel);

    List<Filme> buscarPorIds(List<String> ids);
    Page<Filme> listarTodos(int page, int size);
    Filme atualizar(Filme filme);

    void deletar(String id);
}
