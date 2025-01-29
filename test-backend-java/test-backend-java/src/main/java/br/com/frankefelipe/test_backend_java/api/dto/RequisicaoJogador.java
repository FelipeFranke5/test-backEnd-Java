package br.com.frankefelipe.test_backend_java.api.dto;

import br.com.frankefelipe.test_backend_java.api.exception.RequisicaoInvalidaException;
import java.util.regex.Pattern;

public record RequisicaoJogador(String nome, String email, String telefone, Integer grupo) {

    private static final Pattern PADRAO_EMAIL = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PADRAO_TELEFONE = Pattern.compile("^\\(\\d{2}\\) \\d{5}-\\d{4}$");

    public RequisicaoJogador {
        validarCamposObrigatorios(nome, email);
        validarEmail(email == null ? "" : email.trim());
        validarTelefone(telefone);
        validarGrupo(grupo);
    }

    private void validarCamposObrigatorios(String nome, String email) {
        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new RequisicaoInvalidaException("O nome e e-mail são campos obrigatórios");
        }
    }

    private void validarEmail(String email) {
        if (!PADRAO_EMAIL.matcher(email).matches()) {
            throw new RequisicaoInvalidaException("E-mail inválido");
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone != null && !PADRAO_TELEFONE.matcher(telefone).matches()) {
            throw new RequisicaoInvalidaException("Telefone inválido: use o formato (XX) 99999-9999");
        }
    }

    private void validarGrupo(Integer grupo) {
        if (grupo == null || (grupo != 1 && grupo != 2)) {
            throw new RequisicaoInvalidaException("O grupo deve ser 1 (Liga da Justiça) ou 2 (Vingadores)");
        }
    }

}
