package com.locadora.locadoraia.adapters.controllers.dto;

import java.time.LocalDate;

public record DevolucaoDto(String locacaoId, LocalDate dataDevolucao) {
}
