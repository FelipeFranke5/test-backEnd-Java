package br.com.frankefelipe.test_backend_java.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.frankefelipe.test_backend_java.api.model.Grupo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class GrupoRepositoryTest {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve retornar um Optional vazio quando não existir um grupo na base com o nome informado")
    void testFindByNomeRetornaOptionalVazio() {
        // Given
        String nomeNaoExistente = "Grupo Teste";
        // When
        var resultado = grupoRepository.findByNome(nomeNaoExistente);
        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar um Optional com o grupo quando existir um grupo na base com o nome informado")
    void testFindByNomeRetornaGrupo() {
        // Given
        Grupo grupo = new Grupo("Grupo Teste");
        entityManager.persist(grupo);
        // When
        var resultado = grupoRepository.findByNome(grupo.getNome());
        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(grupo);
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um grupo na base com o nome informado")
    void testExistsByNomeRetornaFalso() {
        // Given
        String nomeNaoExistente = "Grupo Teste";
        // When
        boolean resultado = grupoRepository.existsByNome(nomeNaoExistente);
        // Then
        assertThat(resultado).isFalse();

    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um grupo na base com o nome informado")
    void testExistsByNomeRetornaGrupo() {
        // Given
        Grupo grupo = new Grupo("Grupo Teste");
        entityManager.persist(grupo);
        // When
        boolean resultado = grupoRepository.existsByNome(grupo.getNome());
        // Then
        assertThat(resultado).isTrue();
    }
}
