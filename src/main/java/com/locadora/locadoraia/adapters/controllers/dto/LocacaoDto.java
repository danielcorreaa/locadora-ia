package com.locadora.locadoraia.adapters.controllers.dto;

import java.time.LocalDate;
import java.util.List;

public record LocacaoDto(
        String clienteId,
        List<String> filmeId, LocalDate dataLocacao, LocalDate dataPrevDevolucao) {
}
