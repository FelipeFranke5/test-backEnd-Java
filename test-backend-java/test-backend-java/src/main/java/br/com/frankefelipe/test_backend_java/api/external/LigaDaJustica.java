package br.com.frankefelipe.test_backend_java.api.external;

import java.util.ArrayList;
import java.util.List;

public class LigaDaJustica {

    private List<Codinome> codinomes;

    public LigaDaJustica() {
        this.codinomes = new ArrayList<>();
    }

    public List<Codinome> getCodinomes() {
        return this.codinomes;
    }

    public void setCodinomes(List<Codinome> lista) {
        this.codinomes = lista;
    }

}
