package br.com.frankefelipe.test_backend_java.api.repository;

import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    boolean existsByEmail(String email);
    boolean existsByCodinome(String codinome);
}
