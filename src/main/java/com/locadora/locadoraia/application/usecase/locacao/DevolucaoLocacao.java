package com.locadora.locadoraia.application.usecase.locacao;

import com.locadora.locadoraia.adapters.controllers.dto.DevolucaoDto;
import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.domain.Multa;
import com.locadora.locadoraia.domain.repository.LocacaoRepository;
import com.locadora.locadoraia.domain.repository.MultaRepository;
import com.locadora.locadoraia.exception.BusinessException;
import com.locadora.locadoraia.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class DevolucaoLocacao {

    private final LocacaoRepository locacaoRepository;
    private final MultaRepository multaRepository;

    public DevolucaoLocacao(LocacaoRepository locacaoRepository, MultaRepository multaRepository) {
        this.locacaoRepository = locacaoRepository;
        this.multaRepository = multaRepository;
    }

    public Locacao devolverFilme(DevolucaoDto devolucaoDto) {
        Locacao locacao = locacaoRepository.buscarPorId(devolucaoDto.locacaoId())
                .orElseThrow(() -> new NotFoundException("Locação não encontrada"));

        if (locacao.isFinalizada()) {
            throw new BusinessException("Filme já foi devolvido!");
        }
        locacao.devolverFilme(devolucaoDto.dataDevolucao());
        locacaoRepository.salvar(locacao);

        // verifica atraso
        long diasAtraso = ChronoUnit.DAYS.between(locacao.getDataPrevDevolucao(), locacao.getDataDevolucao());

        Multa multa = null;
        if (diasAtraso > 0) {
            BigDecimal valorMulta = BigDecimal.valueOf(diasAtraso * 2.50); // R$ 2,50 por dia
            multa = new Multa(UUID.randomUUID().toString(), locacao.getCliente().getCpf(), locacao.getId(), valorMulta, Boolean.FALSE, LocalDate.now());
            multaRepository.salvar(multa);
        }

        return locacao;
    }
}
