package com.locadora.locadoraia.application.usecase.multa;

import com.locadora.locadoraia.adapters.controllers.dto.LocacaoDto;
import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.domain.Multa;
import com.locadora.locadoraia.domain.repository.ClienteRepository;
import com.locadora.locadoraia.domain.repository.LocacaoRepository;
import com.locadora.locadoraia.domain.repository.MultaRepository;
import com.locadora.locadoraia.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class GerarMultaUseCase {

    private final LocacaoRepository locacaoRepo;
    private final MultaRepository multaRepo;
    private final ClienteRepository clienteRepo;

    public GerarMultaUseCase(LocacaoRepository locacaoRepo, MultaRepository multaRepo, ClienteRepository clienteRepo) {
        this.locacaoRepo = locacaoRepo;
        this.multaRepo = multaRepo;
        this.clienteRepo = clienteRepo;
    }

    public void verificarAtrasosEAplicarMultas(Locacao locacao) {
        if (locacao.getDataPrevDevolucao().isBefore(LocalDate.now())) {
            BigDecimal valorMulta = calcularMulta(locacao);
            Multa multa = new Multa(UUID.randomUUID().toString(),
                    locacao.getCliente().getCpf(), locacao.getId(), valorMulta, Boolean.FALSE, LocalDate.now());
            multaRepo.salvar(multa);

            Cliente cliente = clienteRepo.buscarPorId(locacao.getCliente().getCpf()).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            cliente.bloquear();
            clienteRepo.atualizar(cliente);
        }
    }

    private BigDecimal calcularMulta(Locacao locacao) {
        long diasAtraso = ChronoUnit.DAYS.between(locacao.getDataPrevDevolucao(), LocalDate.now());
        return BigDecimal.valueOf(diasAtraso * 5); // R$5 por dia de atraso
    }
}

