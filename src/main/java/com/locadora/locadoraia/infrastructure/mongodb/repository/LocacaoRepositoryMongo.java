package com.locadora.locadoraia.infrastructure.mongodb.repository;


import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.infrastructure.mongodb.entity.LocacaoDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Optional;


public interface LocacaoRepositoryMongo extends MongoRepository<LocacaoDocument, String> {

    Page<LocacaoDocument> findAll(Pageable pageable);

    Page<LocacaoDocument> findByFinalizada(boolean finalizada, Pageable pageable);
    Page<LocacaoDocument> findByDataPrevDevolucao(LocalDate dataPrevDevolucao, Pageable pageable);
    Page<LocacaoDocument> findByClienteId(String cpf, Pageable pageable);
    Page<LocacaoDocument> findByFinalizadaAndDataPrevDevolucaoAndClienteId(boolean finalizada, LocalDate dataPrevDevolucao, String cpf, Pageable pageable);

    Page<LocacaoDocument> findByFinalizadaAndClienteId(Boolean finalizado, String cpfCliente, Pageable pageable);
}
