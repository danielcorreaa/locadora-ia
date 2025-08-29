package com.locadora.locadoraia.adapters.controllers.mapper;

import com.locadora.locadoraia.adapters.controllers.dto.LocacaoRequestDto;
import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.domain.Locacao;

public class LocacaoMapper {

    public static Locacao toEntity(LocacaoRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return new Locacao(
                dto.id(),
                new Cliente(dto.clienteId()),
                new Filme(dto.filmeId()),
                dto.dataLocacao(),
                dto.dataDevolucao(),
                dto.finalizada(),
                dto.dataPrevDevolucao()
        );
    }

    public static LocacaoRequestDto toDTO(Locacao locacao) {
        if (locacao == null) {
            return null;
        }

        return new LocacaoRequestDto(
                locacao.getId(),
                locacao.getCliente().getCpf(),
                locacao.getFilme().getId(),
                locacao.getDataLocacao(),
                locacao.getDataPrevDevolucao(),
                locacao.getDataDevolucao(),
                locacao.isFinalizada()
        );
    }
}
