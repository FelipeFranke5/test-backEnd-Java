package br.com.frankefelipe.test_backend_java.api.external;

import java.util.ArrayList;
import java.util.List;

public class Vingadores {

    private List<Codinome> codinomes;

    public Vingadores() {
        this.codinomes = new ArrayList<>();
    }

    public List<Codinome> getCodinomes() {
        return this.codinomes;
    }

    public void setCodinomes(List<Codinome> lista) {
        this.codinomes = lista;
    }

}
