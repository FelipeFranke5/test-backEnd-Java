package br.com.frankefelipe.test_backend_java.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import br.com.frankefelipe.test_backend_java.api.dto.RequisicaoJogador;
import br.com.frankefelipe.test_backend_java.api.exception.JogadorNaoEncontradoException;
import br.com.frankefelipe.test_backend_java.api.exception.RequisicaoInvalidaException;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import br.com.frankefelipe.test_backend_java.api.repository.GrupoRepository;
import br.com.frankefelipe.test_backend_java.api.repository.JogadorRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
public class JogadorServiceTest {

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private GrupoRepository grupoRepository;

    @InjectMocks
    private JogadorService jogadorService;

    @Test
    @DisplayName("Deve lançar RequisicaoInvalidaException após exceder 3 tentativas de salvar")
    void testeCadastrarJogadorLancaRequisicaoInvalidaException() {
        // Given
        RequisicaoJogador requisicaoJogador = criarRequisicaoJogador();
        JogadorServiceUtil jogadorServiceUtil = Mockito.spy(new JogadorServiceUtil(jogadorRepository, grupoRepository));
        Mockito.doReturn(false).when(jogadorServiceUtil).cadastroFeitoComSucesso(any(), anyString());
        // When
        RequisicaoInvalidaException excecao = Assertions.assertThrows(RequisicaoInvalidaException.class, () -> {
            jogadorServiceUtil.salvarJogador(requisicaoJogador);
        });
        // Then
        String mensagemEsperada = "Erro ao salvar jogador";
        Assertions.assertEquals(mensagemEsperada, excecao.getMessage());
        Mockito.verify(jogadorServiceUtil, Mockito.times(3)).cadastroFeitoComSucesso(any(), anyString());
    }

    @Test
    @DisplayName("Deve capturar DataIntegrityViolationException durante o salvamento do jogador")
    void testeCadastrarJogadorCapturaDataIntegrityViolationException() {
        // Given
        RequisicaoJogador requisicaoJogador = criarRequisicaoJogador();
        JogadorServiceUtil jogadorServiceUtil = Mockito.spy(new JogadorServiceUtil(jogadorRepository, grupoRepository));
        Mockito.doThrow(new DataIntegrityViolationException("Erro ao salvar jogador")).when(jogadorServiceUtil).cadastroFeitoComSucesso(any(), anyString());
        // When
        RequisicaoInvalidaException excecao = Assertions.assertThrows(RequisicaoInvalidaException.class, () -> {
            jogadorServiceUtil.salvarJogador(requisicaoJogador);
        });
        // Then
        String mensagemEsperada = "Erro ao salvar jogador";
        Assertions.assertEquals(mensagemEsperada, excecao.getMessage());
        Mockito.verify(jogadorServiceUtil, Mockito.atLeast(3)).cadastroFeitoComSucesso(any(), anyString());
    }

    @Test
    @DisplayName("Deve retornar um Jogador ao encontrar um jogador por ID")
    void testeEncontrarJogadorPorIdRetornaJogador() {
        // Given
        RequisicaoJogador requisicaoJogadorSalvo = criarRequisicaoJogador();
        Jogador jogadorSalvo = new Jogador(requisicaoJogadorSalvo);
        Mockito.when(jogadorRepository.findById(anyLong())).thenReturn(Optional.of(jogadorSalvo));
        // When
        Jogador jogadorEncontrado = jogadorService.encontrarJogadorPorId(1L);
        // Then
        Assertions.assertEquals(jogadorSalvo, jogadorEncontrado);
    }

    @Test
    @DisplayName("Deve lançar JogadorNaoEncontradoException ao não encontrar um jogador por ID")
    void testeEncontrarJogadorPorIdLancaJogadorNaoEncontradoException() {
        // Given
        Mockito.when(jogadorRepository.findById(anyLong())).thenReturn(Optional.empty());
        // When
        JogadorNaoEncontradoException excecao = Assertions.assertThrows(JogadorNaoEncontradoException.class, () -> {
            jogadorService.encontrarJogadorPorId(1L);
        });
        // Then
        String mensagemEsperada = "Jogador não localizado com base no ID 1";
        Assertions.assertEquals(mensagemEsperada, excecao.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia ao listar jogadores")
    void testeListarJogadoresRetornaListaVazia() {
        // Given
        Mockito.when(jogadorRepository.findAll()).thenReturn(List.of());
        // When
        List<Jogador> jogadores = jogadorService.listarJogadores();
        // Then
        Assertions.assertTrue(jogadores.isEmpty());

    }

    @Test
    @DisplayName("Deve retornar uma lista de alguns jogadores ao listar jogadores")
    void testeListarJogadoresRetornaListaComJogadoresCriados() {
        // Given
        RequisicaoJogador requisicaoJogador1 = criarRequisicaoJogador("Pedro", "pedro@email.com", null, 1);
        RequisicaoJogador requisicaoJogador2 = criarRequisicaoJogador("Felipe", "felipe@email.com", null, 1);
        RequisicaoJogador requisicaoJogador3 = criarRequisicaoJogador("Luan", "luan@email.com", null, 1);
        Jogador jogador1 = new Jogador(requisicaoJogador1);
        Jogador jogador2 = new Jogador(requisicaoJogador2);
        Jogador jogador3 = new Jogador(requisicaoJogador3);
        Mockito.when(jogadorRepository.findAll()).thenReturn(List.of(jogador1, jogador2, jogador3));
        // When
        List<Jogador> jogadores = jogadorService.listarJogadores();
        // Then
        Assertions.assertEquals(3, jogadores.size());

    }

    private RequisicaoJogador criarRequisicaoJogador() {
        return new RequisicaoJogador("Teste", "emailtest@email.com", "(11) 99999-9999", 1);
    }

    private RequisicaoJogador criarRequisicaoJogador(String nome, String email, String telefone, int idGrupo) {
        return new RequisicaoJogador(nome, email, telefone, idGrupo);
    }
}
