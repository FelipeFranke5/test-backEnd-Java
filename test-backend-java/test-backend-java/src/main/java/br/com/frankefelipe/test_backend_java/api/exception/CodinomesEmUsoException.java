package br.com.frankefelipe.test_backend_java.api.exception;

public class CodinomesEmUsoException extends RuntimeException {
    public CodinomesEmUsoException(String mensagem) {
        super(mensagem);
    }
}
