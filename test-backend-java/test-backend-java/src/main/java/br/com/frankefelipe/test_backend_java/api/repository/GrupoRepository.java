package br.com.frankefelipe.test_backend_java.api.repository;

import br.com.frankefelipe.test_backend_java.api.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    Optional<Grupo> findByNome(String nome);
    boolean existsByNome(String nome);
}
