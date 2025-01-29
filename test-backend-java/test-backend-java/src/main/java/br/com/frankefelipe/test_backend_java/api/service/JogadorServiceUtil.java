package br.com.frankefelipe.test_backend_java.api.service;

import br.com.frankefelipe.test_backend_java.api.dto.RequisicaoJogador;
import br.com.frankefelipe.test_backend_java.api.exception.CodinomesEmUsoException;
import br.com.frankefelipe.test_backend_java.api.exception.GrupoNaoEncontradoException;
import br.com.frankefelipe.test_backend_java.api.exception.RequisicaoInvalidaException;
import br.com.frankefelipe.test_backend_java.api.external.CodinomeLigaDaJusticaAPI;
import br.com.frankefelipe.test_backend_java.api.external.CodinomeVingadoresAPI;
import br.com.frankefelipe.test_backend_java.api.external.LigaDaJustica;
import br.com.frankefelipe.test_backend_java.api.external.Vingadores;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import br.com.frankefelipe.test_backend_java.api.repository.GrupoRepository;
import br.com.frankefelipe.test_backend_java.api.repository.JogadorRepository;
import org.springframework.dao.DataIntegrityViolationException;

public class JogadorServiceUtil {

    private final JogadorRepository jogadorRepository;
    private final GrupoRepository grupoRepository;

    public JogadorServiceUtil(JogadorRepository jogadorRepository, GrupoRepository grupoRepository) {
        this.jogadorRepository = jogadorRepository;
        this.grupoRepository = grupoRepository;
    }

    public void validarEmail(String email) {
        if (jogadorRepository.existsByEmail(email)) {
            throw new RequisicaoInvalidaException("Jogador com este E-mail já existe");
        }
    }

    public void salvarJogador(RequisicaoJogador requisicaoJogador) {
        String grupoSelecionado = requisicaoJogador.grupo() == 1 ? "Vingadores" : "Liga da Justiça";
        boolean cadastroFeito = false;
        int tentativaSalvarBanco = 0;
        while (!cadastroFeito) {
            validarTentativasSalvar(tentativaSalvarBanco);
            try {
                cadastroFeito = cadastroFeitoComSucesso(requisicaoJogador, grupoSelecionado);
                tentativaSalvarBanco++;
            } catch (DataIntegrityViolationException e) {
                tentativaSalvarBanco++;
            }
        }
    }

    boolean cadastroFeitoComSucesso(RequisicaoJogador requisicaoJogador, String grupoSelecionado) {
        String codinome = grupoSelecionado.equals("Liga da Justiça") ? obterCodinomeLigaDaJustica() : obterCodinomeVingadores();
        Jogador jogador = new Jogador(requisicaoJogador);
        jogador.setCodinome(codinome);
        jogador.setGrupo(grupoRepository.findByNome(grupoSelecionado).orElseThrow(() -> new GrupoNaoEncontradoException("Grupo não encontrado")));
        jogadorRepository.save(jogador);
        return true;
    }

    String obterCodinomeLigaDaJustica() {
        CodinomeLigaDaJusticaAPI codinomeLigaDaJusticaAPI = new CodinomeLigaDaJusticaAPI(jogadorRepository);
        LigaDaJustica ligaDaJustica = codinomeLigaDaJusticaAPI.gerarLigaDaJustica();
        ligaDaJustica = codinomeLigaDaJusticaAPI.removeCodinomesJaUtilizados(ligaDaJustica);
        if (!codinomeLigaDaJusticaAPI.codinomesDisponiveis(ligaDaJustica)) throw new CodinomesEmUsoException("Todos os codinomes da Liga da Justiça estão em uso");
        return codinomeLigaDaJusticaAPI.escolheCodinomeAleatorio(ligaDaJustica);
    }

    String obterCodinomeVingadores() {
        CodinomeVingadoresAPI codinomeVingadoresAPI = new CodinomeVingadoresAPI(jogadorRepository);
        Vingadores vingadores = codinomeVingadoresAPI.gerarVingadores();
        vingadores = codinomeVingadoresAPI.removeCodinomesJaUtilizados(vingadores);
        if (!codinomeVingadoresAPI.codinomesDisponiveis(vingadores)) throw new CodinomesEmUsoException("Todos os codinomes dos Vingadores estão em uso");
        return codinomeVingadoresAPI.escolheCodinomeAleatorio(vingadores);
    }

    void validarTentativasSalvar(int tentativaSalvarBanco) {
        if (tentativaSalvarBanco >= 3) {
            throw new RequisicaoInvalidaException("Erro ao salvar jogador");
        }
    }

}
