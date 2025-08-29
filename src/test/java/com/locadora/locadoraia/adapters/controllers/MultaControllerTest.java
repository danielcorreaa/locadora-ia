package com.locadora.locadoraia.adapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.locadora.locadoraia.adapters.ClienteRepositoryAdapter;
import com.locadora.locadoraia.adapters.MultaRepositoryAdapter;
import com.locadora.locadoraia.application.usecase.multa.PagarMultaUseCase;
import com.locadora.locadoraia.domain.Multa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MultaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MultaRepositoryAdapter multaRepositoryAdapter;

    @Mock
    private ClienteRepositoryAdapter clienteRepositoryAdapter;

    @InjectMocks
    private MultaController multaController;

    private ObjectMapper objectMapper;

    private Multa multa1;
    private Multa multa2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(multaController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        multa1 = new Multa("1", "123.456.789-01", "loc1", BigDecimal.valueOf(50), false, LocalDate.now().minusDays(5));
        multa2 = new Multa("2", "987.654.321-00", "loc2", BigDecimal.valueOf(30), true, LocalDate.now().minusDays(10));
    }

    @Test
    void deveCriarMulta() throws Exception {
        when(multaRepositoryAdapter.salvar(any(Multa.class))).thenReturn(multa1);

        mockMvc.perform(post("/multas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(multa1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.valor").value(50))
                .andExpect(jsonPath("$.paga").value(false));
    }

    @Test
    void deveBuscarMultaPorId() throws Exception {
        when(multaRepositoryAdapter.buscarPorId("1")).thenReturn(Optional.of(multa1));

        mockMvc.perform(get("/multas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.clienteId").value("123.456.789-01"))
                .andExpect(jsonPath("$.locacaoId").value("loc1"));
    }

    @Test
    void deveListarMultasPorCliente() throws Exception {
        when(multaRepositoryAdapter.buscarPorClienteId("123.456.789-01"))
                .thenReturn(List.of(multa1));

        mockMvc.perform(get("/multas/cliente/123.456.789-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].valor").value(50));
    }

    @Test
    void deveListarTodasMultas() throws Exception {
        when(multaRepositoryAdapter.buscarTodas()).thenReturn(List.of(multa1, multa2));

        mockMvc.perform(get("/multas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    /*@Test
    void devePagarMulta() throws Exception {
        // Simulando o uso do PagarMultaUseCase retornando true
        PagarMultaUseCase pagarMock = mock(PagarMultaUseCase.class);
        when(pagarMock.pagar("1")).thenReturn(true);

        mockMvc.perform(put("/multas/1/pagar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Multa paga com sucesso!"));
    }*/
}

