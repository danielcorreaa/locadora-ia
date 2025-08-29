package com.locadora.locadoraia.infrastructure.mongodb.repository;

import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.infrastructure.mongodb.entity.FilmeDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FilmeRepositoryMongo extends MongoRepository<FilmeDocument, String> {

    Page<FilmeDocument> findAll(Pageable pageable);

    Optional<Filme> findByIdAndDisponivel(String id, Boolean disponivel);
}
