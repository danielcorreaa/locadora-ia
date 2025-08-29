package com.locadora.locadoraia.adapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locadora.locadoraia.adapters.ClienteRepositoryAdapter;
import com.locadora.locadoraia.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteRepositoryAdapter clienteRepositoryAdapter;

    @InjectMocks
    private ClienteController clienteController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void deveCadastrarCliente() throws Exception {
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@email.com", false);

        when(clienteRepositoryAdapter.salvar(cliente)).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }

    @Test
    void deveBuscarClientePorId() throws Exception {
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@email.com", false);

        when(clienteRepositoryAdapter.buscarPorId("12345678901")).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/12345678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }

    @Test
    void deveListarClientesPaginados() throws Exception {
        Cliente c1 = new Cliente("João Silva", "12345678901", "joao@email.com", false);
        Cliente c2 = new Cliente("Maria Oliveira", "98765432100", "maria@email.com", false);

        Page<Cliente> page = new PageImpl<>(List.of(c1, c2), PageRequest.of(0, 2), 2);

        when(clienteRepositoryAdapter.listarTodos(0, 2)).thenReturn(page);

        mockMvc.perform(get("/clientes?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("João Silva"))
                .andExpect(jsonPath("$.content[1].nome").value("Maria Oliveira"))
                .andExpect(jsonPath("$.totalElements").value(2));
    }
}


