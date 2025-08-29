package com.locadora.locadoraia.infrastructure.mongodb.repository;

import com.locadora.locadoraia.infrastructure.mongodb.entity.MultaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MultaRepositoryMongo extends MongoRepository<MultaDocument,String> {

    List<MultaDocument> findByClienteId(String clienteId);

}
