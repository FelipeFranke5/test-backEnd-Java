package br.com.frankefelipe.test_backend_java.api.external;

import br.com.frankefelipe.test_backend_java.api.exception.ChamadaCodinomeException;
import br.com.frankefelipe.test_backend_java.api.model.Jogador;
import br.com.frankefelipe.test_backend_java.api.repository.JogadorRepository;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class CodinomeLigaDaJusticaAPI {

    private static final String BASE_URL_LIGA_DA_JUSTICA = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";
    private static final RestClient restClientLigaDaJustica = RestClient.builder().baseUrl(BASE_URL_LIGA_DA_JUSTICA).build();

    private final JogadorRepository jogadorRepository;

    public CodinomeLigaDaJusticaAPI(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    public LigaDaJustica gerarLigaDaJustica() {
        String respostaXml = processarRespostaLigaDaJustica();
        CodificadorCodinomeXml codificadorCodinomeXml = new CodificadorCodinomeXml();
        return codificadorCodinomeXml.gerarLigaDaJusticaDeString(respostaXml);
    }

    public LigaDaJustica removeCodinomesJaUtilizados(LigaDaJustica ligaDaJustica) {
        List<Jogador> jogadores = jogadorRepository.findAll();
        List<String> codinomesUtilizados = jogadores.stream()
            .map(Jogador::getCodinome)
            .toList();
        ligaDaJustica.setCodinomes(
            ligaDaJustica.getCodinomes().stream()
                .filter(codinome -> !codinomesUtilizados.contains(codinome.valor()))
                .toList()
        );
        return ligaDaJustica;
    }

    public boolean codinomesDisponiveis(LigaDaJustica ligaDaJusticaFiltrado) {
        return ligaDaJusticaFiltrado.getCodinomes().size() > 0;
    }

    public String escolheCodinomeAleatorio(LigaDaJustica ligaDaJusticaFiltrado) {
        int indice = ThreadLocalRandom.current().nextInt(0, ligaDaJusticaFiltrado.getCodinomes().size());
        return ligaDaJusticaFiltrado.getCodinomes().get(indice).valor();
    }

    private String processarRespostaLigaDaJustica() {
        ResponseEntity<String> resposta = respostaAPI();
        validaRespostaHttp(resposta);
        return resposta.getBody();
    }

    private ResponseEntity<String> respostaAPI() {
        return restClientLigaDaJustica.get()
                .retrieve()
                .toEntity(String.class);
    }

    private void validaRespostaHttp(ResponseEntity<String> resposta) {
        if (resposta.getStatusCode().value() != 200) {
            throw new ChamadaCodinomeException("Erro ao chamar a API de codinomes");
        }
    }
}
