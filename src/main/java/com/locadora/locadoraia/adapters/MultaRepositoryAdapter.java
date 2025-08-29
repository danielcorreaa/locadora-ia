package com.locadora.locadoraia.adapters;

import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.domain.Multa;
import com.locadora.locadoraia.domain.repository.MultaRepository;
import com.locadora.locadoraia.exception.NotFoundException;
import com.locadora.locadoraia.infrastructure.mongodb.entity.LocacaoDocument;
import com.locadora.locadoraia.infrastructure.mongodb.entity.MultaDocument;
import com.locadora.locadoraia.infrastructure.mongodb.repository.LocacaoRepositoryMongo;
import com.locadora.locadoraia.infrastructure.mongodb.repository.MultaRepositoryMongo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MultaRepositoryAdapter implements MultaRepository {

    private final MultaRepositoryMongo multaRepositoryMongo;
    private final LocacaoRepositoryAdapter locacaoRepositoryAdapter;

    public MultaRepositoryAdapter(MultaRepositoryMongo multaRepositoryMongo, LocacaoRepositoryAdapter locacaoRepositoryAdapter) {
        this.multaRepositoryMongo = multaRepositoryMongo;
        this.locacaoRepositoryAdapter = locacaoRepositoryAdapter;
    }


    @Override
    public Multa salvar(Multa multa) {
        MultaDocument document = toDocument(multa);
        MultaDocument save = multaRepositoryMongo.save(document);
        return toEntity(save);
    }

    @Override
    public Optional<Multa> buscarPorId(String id) {
        return multaRepositoryMongo.findById(id).map(this::toEntity);
    }

    @Override
    public List<Multa> buscarPorClienteId(String clienteId) {
        return multaRepositoryMongo.findByClienteId(clienteId)
                .stream()
                .map(this::toEntity) // converte Document → Entity
                .toList();
    }

    @Override
    public Multa atualizar(Multa multa) {
        return salvar(multa);
    }

    @Override
    public Locacao buscarLocacaoPorMultaId(String locacaoId) {
        return locacaoRepositoryAdapter.buscarPorId(locacaoId).orElseThrow(() -> new NotFoundException("Locação não encontrada!"));
    }

    @Override
    public List<Multa> buscarTodas() {
        return multaRepositoryMongo.findAll().stream().map(this::toEntity).toList();
    }

    public MultaDocument toDocument(Multa multa) {
        if (multa == null) return null;

        return new MultaDocument(
                multa.getId(),
                multa.getClienteId(),
                multa.getLocacaoId(),
                multa.getValor(),
                multa.isPaga(),
                multa.getDataGeracao()
        );
    }

    public Multa toEntity(MultaDocument document) {
        if (document == null) return null;

        return new Multa(
                document.getId(),
                document.getClienteId(),
                document.getLocacaoId(),
                document.getValor(),
                document.isPaga(),
                document.getDataGeracao()
        );
    }
}
