package br.com.frankefelipe.test_backend_java.api.config;

import br.com.frankefelipe.test_backend_java.api.model.Grupo;
import br.com.frankefelipe.test_backend_java.api.repository.GrupoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Aqui vou criar os grupos que serão disponibilizados durante a inicialização do projeto
@Component
public class GrupoConfig implements CommandLineRunner {

    private final GrupoRepository grupoRepository;

    public GrupoConfig(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @Override
    public void run(String... args) {
        if (!grupoRepository.existsByNome("Liga da Justiça")) {
            grupoRepository.save(new Grupo("Liga da Justiça"));
        }
        if (!grupoRepository.existsByNome("Vingadores")) {
            grupoRepository.save(new Grupo("Vingadores"));
        }
    }
}
