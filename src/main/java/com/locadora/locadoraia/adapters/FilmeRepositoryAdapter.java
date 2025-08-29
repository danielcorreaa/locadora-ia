package com.locadora.locadoraia.adapters;

import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.domain.repository.ClienteRepository;
import com.locadora.locadoraia.domain.repository.FilmeRepository;
import com.locadora.locadoraia.infrastructure.mongodb.entity.ClienteDocument;
import com.locadora.locadoraia.infrastructure.mongodb.entity.FilmeDocument;
import com.locadora.locadoraia.infrastructure.mongodb.repository.ClienteRepositoryMongo;
import com.locadora.locadoraia.infrastructure.mongodb.repository.FilmeRepositoryMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FilmeRepositoryAdapter implements FilmeRepository {

    private final FilmeRepositoryMongo filmeRepositoryMongo;

    public FilmeRepositoryAdapter(FilmeRepositoryMongo filmeRepositoryMongo) {
        this.filmeRepositoryMongo = filmeRepositoryMongo;
    }

    private FilmeDocument toDocument(Filme filme) {
        return new FilmeDocument(
                filme.getId(),
                filme.getTitulo(),
                filme.getGenero(),
                filme.getAnoLancamento(),
                filme.isDisponivel()
        );
    }

    private Filme toEntity(FilmeDocument doc) {
        return new Filme(
                doc.getId(),
                doc.getTitulo(),
                doc.getGenero(),
                doc.getAnoLancamento(), doc.isDisponivel()
        );
    }

    @Override
    public Filme salvar(Filme filme) {
        FilmeDocument doc = toDocument(filme);
        filmeRepositoryMongo.save(doc);
        return filme;
    }

    @Override
    public Optional<Filme> buscarPorId(String id) {
        return filmeRepositoryMongo.findById(id).map(this::toEntity);
    }

    @Override
    public Optional<Filme> buscarPorIdDisponivel(String id, Boolean disponivel) {
        return filmeRepositoryMongo.findByIdAndDisponivel(id, disponivel);
    }

    @Override
    public List<Filme> buscarPorIds(List<String> ids) {
        return filmeRepositoryMongo.findAllById(ids).stream().map(this::toEntity).toList();
    }

    @Override
    public Page<Filme> listarTodos(int page, int size) {
        Page<FilmeDocument> result = filmeRepositoryMongo.findAll(PageRequest.of(page, size));
        return result.map(this::toEntity);
    }

    @Override
    public Filme atualizar(Filme filme) {
        return toEntity(filmeRepositoryMongo.save(toDocument(filme)));
    }

    @Override
    public void deletar(String id) {
        filmeRepositoryMongo.deleteById(id);
    }
}
