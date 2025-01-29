package br.com.frankefelipe.test_backend_java.api.external;

import br.com.frankefelipe.test_backend_java.api.exception.ChamadaCodinomeException;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import br.com.frankefelipe.test_backend_java.api.repository.JogadorRepository;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class CodinomeVingadoresAPI {

    private static final String BASE_URL_VINGADORES = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json";
    private static final RestClient restClientVingadores = RestClient.builder().baseUrl(BASE_URL_VINGADORES).build();

    private final JogadorRepository jogadorRepository;

    public CodinomeVingadoresAPI(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    public Vingadores gerarVingadores() {
        String respostaJson = processarRespostaVingadores();
        CodificadorCodinomeJson codificadorCodinomeJson = new CodificadorCodinomeJson();
        return codificadorCodinomeJson.geraVingadoresDeString(respostaJson);
    }

    public Vingadores removeCodinomesJaUtilizados(Vingadores vingadores) {
        List<Jogador> jogadores = jogadorRepository.findAll();
        List<String> codinomesUtilizados = jogadores.stream()
            .map(Jogador::getCodinome)
            .toList();
        vingadores.setCodinomes(
            vingadores.getCodinomes().stream()
                .filter(codinome -> !codinomesUtilizados.contains(codinome.valor()))
                .toList()
        );
        return vingadores;
    }

    public boolean codinomesDisponiveis(Vingadores vingadoresFiltrado) {
        return vingadoresFiltrado.getCodinomes().size() > 0;
    }

    public String escolheCodinomeAleatorio(Vingadores vingadoresFiltrado) {
        int indice = ThreadLocalRandom.current().nextInt(0, vingadoresFiltrado.getCodinomes().size());
        return vingadoresFiltrado.getCodinomes().get(indice).valor();
    }

    private String processarRespostaVingadores() {
        ResponseEntity<String> resposta = respostaAPI();
        validaRespostaHttp(resposta);
        return resposta.getBody();
    }

    private ResponseEntity<String> respostaAPI() {
        return restClientVingadores.get()
                .retrieve()
                .toEntity(String.class);
    }

    private void validaRespostaHttp(ResponseEntity<String> resposta) {
        if (resposta.getStatusCode().value() != 200) {
            throw new ChamadaCodinomeException("Erro ao chamar a API de codinomes");
        }
    }

}
