package com.locadora.locadoraia.application.usecase.multa;

import java.math.BigDecimal;

public class ApiPagamentoFakeUseCase {

    public boolean realizarPagamento(BigDecimal valor) {
        return valor.compareTo(BigDecimal.ZERO) > 0;
    }
}
