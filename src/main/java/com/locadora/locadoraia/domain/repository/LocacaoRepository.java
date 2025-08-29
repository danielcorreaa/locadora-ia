package com.locadora.locadoraia.domain.repository;

import com.locadora.locadoraia.domain.Locacao;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LocacaoRepository {
    Locacao salvar(Locacao locacao);
    Optional<Locacao> buscarPorId(String id);
    Page<Locacao> listarTodas(int page, int size,
                              Boolean finalizado,
                              LocalDate dataPrevDevolucao,
                              String cpfCliente);

    List<Locacao> buscarTodasNaoDevolvidas();
}
