package com.locadora.locadoraia.infrastructure.mongodb.repository;

import com.locadora.locadoraia.infrastructure.mongodb.entity.ClienteDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ClienteRepositoryMongo extends MongoRepository<ClienteDocument, String> {

    Page<ClienteDocument> findAll(Pageable pageable);

}
