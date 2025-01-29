package br.com.frankefelipe.test_backend_java.api.service;

import br.com.frankefelipe.test_backend_java.api.dto.RequisicaoJogador;
import br.com.frankefelipe.test_backend_java.api.exception.JogadorNaoEncontradoException;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import br.com.frankefelipe.test_backend_java.api.repository.GrupoRepository;
import br.com.frankefelipe.test_backend_java.api.repository.JogadorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final GrupoRepository grupoRepository;

    public JogadorService(JogadorRepository jogadorRepository, GrupoRepository grupoRepository) {

        this.jogadorRepository = jogadorRepository;
        this.grupoRepository = grupoRepository;

    }

    public Jogador encontrarJogadorPorId(Long id) {
        return jogadorRepository.findById(id).orElseThrow(() -> new JogadorNaoEncontradoException(
                "Jogador não localizado com base no ID " + id
        ));

    }

    public List<Jogador> listarJogadores() {
        return jogadorRepository.findAll();
    }

    public void cadastrarJogador(RequisicaoJogador requisicaoJogador) {
        JogadorServiceUtil jogadorServiceUtil = new JogadorServiceUtil(jogadorRepository, grupoRepository);
        jogadorServiceUtil.validarEmail(requisicaoJogador.email().trim());
        jogadorServiceUtil.salvarJogador(requisicaoJogador);
    }
}
