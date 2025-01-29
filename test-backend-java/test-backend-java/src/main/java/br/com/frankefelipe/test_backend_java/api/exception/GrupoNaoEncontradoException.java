package br.com.frankefelipe.test_backend_java.api.exception;

public class GrupoNaoEncontradoException extends RuntimeException {
    public GrupoNaoEncontradoException(String message) {
        super(message);
    }
}
