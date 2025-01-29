package br.com.frankefelipe.test_backend_java.api.controller;

import br.com.frankefelipe.test_backend_java.api.dto.RequisicaoJogador;
import br.com.frankefelipe.test_backend_java.api.dto.RespostaJogador;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import br.com.frankefelipe.test_backend_java.api.service.JogadorService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/jogador")
@CrossOrigin
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @GetMapping
    public ResponseEntity<List<RespostaJogador>> relatorioJogadores() {
        List<RespostaJogador> jogadoresFinal = obterRespostaJogadoresFinal();
        return ResponseEntity.ok(jogadoresFinal);
    }

    private List<RespostaJogador> obterRespostaJogadoresFinal() {
        List<Jogador> jogadores = jogadorService.listarJogadores();
        List<RespostaJogador> jogadoresFinal = new ArrayList<>(jogadores.size());
        for (Jogador jogador : jogadores) {
            RespostaJogador respostaJogador = new RespostaJogador(
                jogador.getNome(),
                jogador.getEmail(),
                jogador.getTelefone(),
                jogador.getCodinome(),
                jogador.getGrupo().getNome()
            );
            jogadoresFinal.add(respostaJogador);
        }
        return jogadoresFinal;
    }

    @PostMapping
    public ResponseEntity<Void> cadastroJogador(@RequestBody RequisicaoJogador requisicaoJogador) {
        jogadorService.cadastrarJogador(requisicaoJogador);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
