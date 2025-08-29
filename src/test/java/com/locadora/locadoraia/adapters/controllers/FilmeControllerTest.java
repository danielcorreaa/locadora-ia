package com.locadora.locadoraia.adapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locadora.locadoraia.adapters.FilmeRepositoryAdapter;
import com.locadora.locadoraia.domain.Filme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FilmeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FilmeRepositoryAdapter filmeRepositoryAdapter;

    @InjectMocks
    private FilmeController filmeController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(filmeController).build();
    }

    @Test
    void deveCadastrarFilme() throws Exception {
        Filme filme = new Filme("1", "Matrix", "Ficção", 1999, true);

        when(filmeRepositoryAdapter.salvar(filme)).thenReturn(filme);

        mockMvc.perform(post("/filmes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filme)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Matrix"))
                .andExpect(jsonPath("$.genero").value("Ficção"));
    }

    @Test
    void deveBuscarFilmePorId() throws Exception {
        Filme filme = new Filme("1", "Matrix", "Ficção", 1999, true);

        when(filmeRepositoryAdapter.buscarPorId("1")).thenReturn(Optional.of(filme));

        mockMvc.perform(get("/filmes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].titulo").value("Matrix"))
                .andExpect(jsonPath("$.content[0].genero").value("Ficção"));
    }

    @Test
    void deveListarFilmes() throws Exception {
        Filme f1 = new Filme("1", "Matrix", "Ficção", 1999, true);
        Filme f2 = new Filme("2", "Titanic", "Romance", 1997, true);

        Page<Filme> page = new PageImpl<>(List.of(f1, f2), PageRequest.of(0, 2), 2);

        when(filmeRepositoryAdapter.listarTodos(0, 2)).thenReturn(page);

        mockMvc.perform(get("/filmes?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].titulo").value("Matrix"))
                .andExpect(jsonPath("$.content[1].titulo").value("Titanic"))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void deveAtualizarFilme() throws Exception {
        Filme existente = new Filme("1", "Matrix", "Ficção", 1999, true);
        Filme atualizado = new Filme("1", "Matrix Reloaded", "Ação", 2003, true);

        when(filmeRepositoryAdapter.buscarPorId("1")).thenReturn(Optional.of(existente));
        when(filmeRepositoryAdapter.atualizar(any(Filme.class))).thenReturn(atualizado);

        mockMvc.perform(put("/filmes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Matrix Reloaded"))
                .andExpect(jsonPath("$.anoLancamento").value(2003));
    }

    @Test
    void deveDeletarFilme() throws Exception {
        Filme filme = new Filme("1", "Matrix", "Ficção", 1999, true);

        when(filmeRepositoryAdapter.buscarPorId("1")).thenReturn(Optional.of(filme));
        doNothing().when(filmeRepositoryAdapter).deletar("1");

        mockMvc.perform(delete("/filmes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Excluído com sucesso!"));
    }

    @Test
    void deveCadastrarFilmesEmLote() throws Exception {
        Filme f1 = new Filme("1", "Matrix", "Ficção", 1999, true);
        Filme f2 = new Filme("2", "Titanic", "Romance", 1997, true);

        when(filmeRepositoryAdapter.salvar(f1)).thenReturn(f1);
        when(filmeRepositoryAdapter.salvar(f2)).thenReturn(f2);

        mockMvc.perform(post("/filmes/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(f1, f2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Matrix"))
                .andExpect(jsonPath("$[1].titulo").value("Titanic"));
    }
}
