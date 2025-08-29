package com.locadora.locadoraia.domain.repository;

import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.domain.Multa;

import java.util.List;
import java.util.Optional;

public interface MultaRepository {
    Multa salvar(Multa multa);
    Optional<Multa> buscarPorId(String id);
    List<Multa> buscarPorClienteId(String clienteId);

    Multa atualizar(Multa multa);
    Locacao buscarLocacaoPorMultaId(String multaId);

    List<Multa> buscarTodas();
}
