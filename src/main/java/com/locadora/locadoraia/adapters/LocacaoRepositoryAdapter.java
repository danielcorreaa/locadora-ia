package com.locadora.locadoraia.adapters;

import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.domain.Locacao;
import com.locadora.locadoraia.domain.repository.LocacaoRepository;
import com.locadora.locadoraia.infrastructure.mongodb.entity.LocacaoDocument;
import com.locadora.locadoraia.infrastructure.mongodb.repository.LocacaoRepositoryMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class LocacaoRepositoryAdapter implements LocacaoRepository {

    private final LocacaoRepositoryMongo locacaoRepositoryMongo;
    private final ClienteRepositoryAdapter clienteRepositoryAdapter;
    private final FilmeRepositoryAdapter filmeRepositoryAdapter;

    public LocacaoRepositoryAdapter(LocacaoRepositoryMongo locacaoRepositoryMongo, ClienteRepositoryAdapter clienteRepositoryAdapter, FilmeRepositoryAdapter filmeRepositoryAdapter) {
        this.locacaoRepositoryMongo = locacaoRepositoryMongo;
        this.clienteRepositoryAdapter = clienteRepositoryAdapter;
        this.filmeRepositoryAdapter = filmeRepositoryAdapter;
    }


    public LocacaoDocument toDocument(Locacao locacao) {
        return new LocacaoDocument(locacao.getId(),
                locacao.getCliente().getCpf(),
                locacao.getFilme().getId(),
                locacao.getDataLocacao(),
                locacao.getDataDevolucao(), locacao.isFinalizada(), locacao.getDataPrevDevolucao());
    }

    public Locacao toEntity(LocacaoDocument doc) {
        Optional<Cliente> talvezCliente = clienteRepositoryAdapter.buscarPorId(doc.getClienteId());
        Optional<Filme> talvezFilme = filmeRepositoryAdapter.buscarPorId(doc.getFilmeId());
        Cliente cliente = talvezCliente.map(cli -> cli).orElse(null);
        Filme filme = talvezFilme.map(fil -> fil).orElse(null);
        return Locacao.copy(doc.getId(),cliente,filme, doc.getDataLocacao(), doc.getDataDevolucao(), doc.isFinalizada(), doc.getDataPrevDevolucao());
    }

    @Override
    public Locacao salvar(Locacao filme) {
        LocacaoDocument doc = toDocument(filme);
        locacaoRepositoryMongo.save(doc);
        return filme;
    }

    @Override
    public Optional<Locacao> buscarPorId(String id) {
        return locacaoRepositoryMongo.findById(id).map(this::toEntity);
    }


    @Override
    public Page<Locacao> listarTodas(int page, int size,
                                     Boolean finalizado,
                                     LocalDate dataPrevDevolucao,
                                     String cpfCliente) {
        Pageable pageable = PageRequest.of(page, size);

        // Aqui depende de como seu repository está implementado.
        // Se for Spring Data MongoDB, dá pra criar métodos derivados do nome:
        if (finalizado != null && dataPrevDevolucao != null && cpfCliente != null) {
            return locacaoRepositoryMongo.findByFinalizadaAndDataPrevDevolucaoAndClienteId(
                    finalizado, dataPrevDevolucao,cpfCliente,pageable).map(this::toEntity);
        } else if (finalizado != null && cpfCliente != null) {
            return locacaoRepositoryMongo.findByFinalizadaAndClienteId(finalizado ,cpfCliente,pageable).map(this::toEntity);
        } else if (finalizado != null) {
            return locacaoRepositoryMongo.findByFinalizada(finalizado, pageable).map(this::toEntity);
        } else if (dataPrevDevolucao != null) {
            return locacaoRepositoryMongo.findByDataPrevDevolucao(dataPrevDevolucao, pageable).map(this::toEntity);
        } else if (cpfCliente != null) {
            return locacaoRepositoryMongo.findByClienteId(cpfCliente, pageable).map(this::toEntity);
        }

        return locacaoRepositoryMongo.findAll(pageable).map(this::toEntity);
    }


    @Override
    public List<Locacao> buscarTodasNaoDevolvidas() {
        return List.of();
    }

}
