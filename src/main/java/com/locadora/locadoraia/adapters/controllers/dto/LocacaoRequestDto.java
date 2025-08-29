package com.locadora.locadoraia.adapters.controllers.dto;

import java.time.LocalDate;

public record LocacaoRequestDto(
        String id,
        String clienteId,
        String filmeId,
        LocalDate dataLocacao,
        LocalDate dataPrevDevolucao,
        LocalDate dataDevolucao,
        boolean finalizada
) {}


