package com.locadora.locadoraia.adapters.controllers;

import com.locadora.locadoraia.adapters.ClienteRepositoryAdapter;
import com.locadora.locadoraia.adapters.MultaRepositoryAdapter;
import com.locadora.locadoraia.application.usecase.multa.ApiPagamentoFakeUseCase;
import com.locadora.locadoraia.application.usecase.multa.GerarMultaUseCase;
import com.locadora.locadoraia.application.usecase.multa.PagarMultaUseCase;
import com.locadora.locadoraia.domain.Multa;
import com.locadora.locadoraia.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("multas")
@Tag(name = "Multas", description = "Operações relacionadas a multas de locação")
public class MultaController {

    private final MultaRepositoryAdapter multaRepositoryAdapter;
    private final ClienteRepositoryAdapter clienteRepositoryAdapter;

    public MultaController(MultaRepositoryAdapter multaRepositoryAdapter, ClienteRepositoryAdapter clienteRepositoryAdapter) {
        this.multaRepositoryAdapter = multaRepositoryAdapter;
        this.clienteRepositoryAdapter = clienteRepositoryAdapter;
    }


    @Operation(summary = "Pagar uma multa", description = "Efetua o pagamento de uma multa por atraso e desbloqueia o cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Multa paga com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha ao pagar a multa")
    })
    @PutMapping("/{multaId}/pagar")
    public ResponseEntity<String> pagarMulta(@PathVariable String multaId) {
        PagarMultaUseCase pagarMultaUseCase =
                new PagarMultaUseCase(multaRepositoryAdapter, clienteRepositoryAdapter,
                        new ApiPagamentoFakeUseCase());
        boolean sucesso = pagarMultaUseCase.pagar(multaId);
        return sucesso ? ResponseEntity.ok("Multa paga com sucesso!") :
                ResponseEntity.badRequest().body("Falha ao pagar a multa.");
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar multas de um cliente")
    public ResponseEntity<List<Multa>> listarMultasPorCliente(@PathVariable String clienteId) {
        var multas = multaRepositoryAdapter.buscarPorClienteId(clienteId);
        if(multas.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Multa não encontrada");
        }
        return ResponseEntity.ok(multas);
    }

    @GetMapping
    @Operation(summary = "Listar todas as multas")
    public List<Multa> listarTodas() {
        return multaRepositoryAdapter.buscarTodas();
    }


    @PostMapping
    @Operation(summary = "Criar nova multa")
    public Multa criarMulta(@RequestBody Multa multa) {
        return multaRepositoryAdapter.salvar(multa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar multa por ID")
    public Multa buscarPorId(@PathVariable String id) {
        return multaRepositoryAdapter.buscarPorId(id).orElseThrow(() -> new NotFoundException("Multa nao encontrada"));
    }

}
