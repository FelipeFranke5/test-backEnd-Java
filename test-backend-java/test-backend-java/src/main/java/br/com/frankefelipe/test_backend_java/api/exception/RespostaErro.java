package br.com.frankefelipe.test_backend_java.api.exception;

import java.time.LocalDateTime;

public record RespostaErro(int status, LocalDateTime data, String mensagem) {
}
