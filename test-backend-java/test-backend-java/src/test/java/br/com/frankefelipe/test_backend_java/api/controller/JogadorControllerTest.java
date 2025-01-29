package br.com.frankefelipe.test_backend_java.api.controller;

import br.com.frankefelipe.test_backend_java.api.dto.RequisicaoJogador;
import br.com.frankefelipe.test_backend_java.api.model.Grupo;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import br.com.frankefelipe.test_backend_java.api.service.JogadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(JogadorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class JogadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JogadorService jogadorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar status 201 ao cadastrar um jogador")
    void testeCadastroJogadorRetornaStatus201() throws Exception {
        // Given
        RequisicaoJogador requisicaoJogador = criarRequisicaoJogador();
        // When
        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/api/jogador")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(requisicaoJogador))
        );
        // Then
        resposta.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar status 200 e uma lista vazia ao obter relatório de jogadores")
    void testeRelatorioJogadoresRetornaStatus200ComListaVazia() throws Exception {
        // Given + When
        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.get("/api/jogador"));
        // Then
        resposta.andExpect(MockMvcResultMatchers.status().isOk());
        resposta.andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DisplayName("Deve retornar status 200 e uma lista de jogadores ao obter relatório de jogadores")
    void testeRelatorioJogadoresRetornaStatus200ComListaPreenchida() throws Exception {
        // Given
        RequisicaoJogador requisicaoJogador = criarRequisicaoJogador();
        Jogador jogador = new Jogador(requisicaoJogador);
        Grupo grupo = criarGrupoVingadores();
        jogador.setGrupo(grupo);
        jogador.setCodinome("Homem de Ferro");
        Mockito.when(jogadorService.listarJogadores()).thenReturn(List.of(jogador));
        String jsonEsperado = "[{\"nome\":\"%s\",\"email\":\"%s\",\"telefone\":\"%s\",\"codinome\":\"%s\",\"grupo\":\"%s\"}]";
        jsonEsperado = jsonEsperado.formatted(jogador.getNome(), jogador.getEmail(), jogador.getTelefone(), jogador.getCodinome(), grupo.getNome());
        // When
        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.get("/api/jogador"));
        // Then
        resposta.andExpect(MockMvcResultMatchers.status().isOk());
        resposta.andExpect(MockMvcResultMatchers.content().json(jsonEsperado));
    }

    private RequisicaoJogador criarRequisicaoJogador() {
        return new RequisicaoJogador("Teste", "emailtest@email.com", "(11) 99999-9999", 1);
    }

    private Grupo criarGrupoVingadores() {
        Grupo grupo1 = new Grupo("Vingadores");
        return grupo1;
    }
}
