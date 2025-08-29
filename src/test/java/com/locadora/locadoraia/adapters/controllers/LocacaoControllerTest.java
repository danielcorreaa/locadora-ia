package com.locadora.locadoraia.adapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.locadora.locadoraia.adapters.ClienteRepositoryAdapter;
import com.locadora.locadoraia.adapters.FilmeRepositoryAdapter;
import com.locadora.locadoraia.adapters.LocacaoRepositoryAdapter;
import com.locadora.locadoraia.adapters.MultaRepositoryAdapter;
import com.locadora.locadoraia.adapters.controllers.dto.DevolucaoDto;
import com.locadora.locadoraia.adapters.controllers.dto.LocacaoDto;
import com.locadora.locadoraia.domain.Cliente;
import com.locadora.locadoraia.domain.Filme;
import com.locadora.locadoraia.domain.Locacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LocacaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocacaoRepositoryAdapter locacaoRepositoryAdapter;

    @Mock
    private FilmeRepositoryAdapter filmeRepositoryAdapter;

    @Mock
    private ClienteRepositoryAdapter clienteRepositoryAdapter;

    @Mock
    private MultaRepositoryAdapter multaRepositoryAdapter;

    @InjectMocks
    private LocacaoController locacaoController;

    private ObjectMapper objectMapper;

    private Cliente cliente;
    private Filme filme;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locacaoController).build();
        objectMapper = new ObjectMapper();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        cliente = new Cliente("João Silva", "123.456.789-01", "joao@email.com", false);
        filme = new Filme("1", "Matrix", "Ficção", 1999, true);
    }

    @Test
    void deveCadastrarLocacao() throws Exception {
        LocacaoDto dto = new LocacaoDto(cliente.getCpf(), List.of(filme.getId()), LocalDate.now(),
                LocalDate.now().plusDays(3));

        Locacao locacao = new Locacao(
                "1",
                cliente,
                filme,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );
        filme.marcarDisponivel();
        when(locacaoRepositoryAdapter.salvar(locacao)).thenReturn(locacao);
        when(clienteRepositoryAdapter.buscarPorId(cliente.getCpf())).thenReturn(Optional.of(cliente));
        when(filmeRepositoryAdapter.buscarPorIds(List.of(filme.getId()))).thenReturn(List.of(filme));


        mockMvc.perform(post("/locacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveDevolverLocacao() throws Exception {
        DevolucaoDto dto = new DevolucaoDto("1", LocalDate.now());

        Locacao locacao = new Locacao(
                "1",
                cliente,
                filme,
                LocalDate.now().minusDays(5),
                LocalDate.now().minusDays(2)
        );

        when(locacaoRepositoryAdapter.buscarPorId("1")).thenReturn(Optional.of(locacao));
        when(locacaoRepositoryAdapter.salvar(locacao)).thenReturn(locacao);

        mockMvc.perform(post("/locacoes/devolver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveBuscarLocacaoPorId() throws Exception {
        Locacao locacao = new Locacao(
                "1",
                cliente,
                filme,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        when(locacaoRepositoryAdapter.buscarPorId("1")).thenReturn(Optional.of(locacao));


        mockMvc.perform(get("/locacoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.cliente.cpf").value("123.456.789-01"))
                .andExpect(jsonPath("$.filme.titulo").value("Matrix"));
    }

    @Test
    void deveListarLocacoes() throws Exception {
        Locacao locacao = new Locacao(
                "1",
                cliente,
                filme,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        Page<Locacao> page = new PageImpl<>(List.of(locacao));

        when(locacaoRepositoryAdapter.listarTodas(0, 5, null, null, null)).thenReturn(page);

        mockMvc.perform(get("/locacoes?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].cliente.cpf").value("123.456.789-01"))
                .andExpect(jsonPath("$.content[0].filme.titulo").value("Matrix"));
    }
}

