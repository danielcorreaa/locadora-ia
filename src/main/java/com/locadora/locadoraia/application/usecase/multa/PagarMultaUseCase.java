package com.locadora.locadoraia.application.usecase.multa;

import com.locadora.locadoraia.adapters.ClienteRepositoryAdapter;
import com.locadora.locadoraia.adapters.MultaRepositoryAdapter;
import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.domain.Multa;

import com.locadora.locadoraia.exception.NotFoundException;

public class PagarMultaUseCase {

    private final MultaRepositoryAdapter multaRepositoryAdapter;
    private final ClienteRepositoryAdapter clienteRepositoryAdapter;
    private final ApiPagamentoFakeUseCase apiPagamentoFakeUseCase;

    public PagarMultaUseCase( MultaRepositoryAdapter multaRepositoryAdapter,
                              ClienteRepositoryAdapter clienteRepositoryAdapter,
                              ApiPagamentoFakeUseCase apiPagamentoFakeUseCase) {
        this.multaRepositoryAdapter = multaRepositoryAdapter;
        this.clienteRepositoryAdapter = clienteRepositoryAdapter;
        this.apiPagamentoFakeUseCase = apiPagamentoFakeUseCase;
    }

    public boolean pagar(String multaId) {
        Multa multa = multaRepositoryAdapter.buscarPorId(multaId).orElseThrow(() -> new  NotFoundException("Multa não encontrada!"));

        if (multa.isPaga()) return false;

        boolean pago = apiPagamentoFakeUseCase.realizarPagamento(multa.getValor());

        if (pago) {
            multa.pagar();
            multaRepositoryAdapter.atualizar(multa);

            // Desbloquear cliente
            Locacao locacao = multaRepositoryAdapter.buscarLocacaoPorMultaId(multa.getLocacaoId()); // depende de implementação

            Cliente cliente = clienteRepositoryAdapter.buscarPorId(multa.getClienteId())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            cliente.desbloquear();
            clienteRepositoryAdapter.atualizar(cliente);

            return true;
        }

        return false;
    }
}
