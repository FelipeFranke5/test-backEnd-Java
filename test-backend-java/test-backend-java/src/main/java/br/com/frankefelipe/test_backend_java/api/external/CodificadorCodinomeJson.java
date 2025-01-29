package br.com.frankefelipe.test_backend_java.api.external;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CodificadorCodinomeJson {

    Vingadores geraVingadoresDeString(String json) {
        Vingadores vingadores = new Vingadores();
        travessarJson(json, vingadores);
        return vingadores;
    }

    private void travessarJson(String json, Vingadores vingadores) {
        Gson gson = new Gson();
        JsonObject objetoJson = gson.fromJson(json, JsonObject.class);
        JsonArray vingadoresArray = objetoJson.getAsJsonArray("vingadores");
        vingadoresArray.forEach(codinome -> vingadores.getCodinomes().add(new Codinome(codinome.getAsJsonObject().get("codinome").getAsString())));
    }

}
