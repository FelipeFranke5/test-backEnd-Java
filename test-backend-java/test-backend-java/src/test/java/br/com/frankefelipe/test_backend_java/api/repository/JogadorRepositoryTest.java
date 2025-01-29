package br.com.frankefelipe.test_backend_java.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.frankefelipe.test_backend_java.api.dto.RequisicaoJogador;
import br.com.frankefelipe.test_backend_java.api.model.Grupo;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class JogadorRepositoryTest {

    @Autowired
    JogadorRepository jogadorRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve retornar falso quando não existir um jogador na base com o email informado")
    void testeExistsByEmailRetornaFalso() {
        // Given
        String emailNaoExistente = "emailtest@email.com";
        // When
        boolean resultado = jogadorRepository.existsByEmail(emailNaoExistente);
        // Then
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um jogador na base com o email informado")
    void testeExistsByEmailRetornaJogador() {
        // Given
        Jogador jogador = criarJogador();
        // When
        boolean resultado = jogadorRepository.existsByEmail(jogador.getEmail());
        // Then
        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um jogador na base com o codinome informado")
    void testeExistsByCodinomeRetornaFalso() {
        // Given
        String codinomeNaoExistente = "Teste";
        // When
        boolean resultado = jogadorRepository.existsByCodinome(codinomeNaoExistente);
        // Then
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um jogador na base com o codinome informado")
    void testeExistsByCodinomeRetornaJogador() {
        // Given
        Jogador jogador = criarJogador();
        // When
        boolean resultado = jogadorRepository.existsByCodinome(jogador.getCodinome());
        // Then
        assertThat(resultado).isTrue();
    }

    private Jogador criarJogador() {
        RequisicaoJogador requisicaoJogador = criarRequisicaoJogador();
        Jogador jogador = new Jogador(requisicaoJogador);
        jogador.setCodinome("Teste");
        Grupo grupo = criarGrupoVingadores();
        jogador.setGrupo(grupo);
        entityManager.persist(jogador);
        return jogador;
    }

    private RequisicaoJogador criarRequisicaoJogador() {
        return new RequisicaoJogador("Teste", "emailtest@email.com", "(11) 99999-9999", 1);
    }

    private Grupo criarGrupoVingadores() {
        Grupo grupo1 = new Grupo("Vingadores");
        entityManager.persist(grupo1);
        return grupo1;
    }
}