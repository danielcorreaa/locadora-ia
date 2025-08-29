package com.locadora.locadoraia.adapters.controllers.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MultaDto(
        String id,
        String clienteId,
        String locacaoId,
        BigDecimal valor,
        boolean paga,
        LocalDate dataGeracao
) {}
