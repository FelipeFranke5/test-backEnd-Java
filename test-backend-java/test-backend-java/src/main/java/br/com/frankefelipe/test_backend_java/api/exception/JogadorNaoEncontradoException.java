package br.com.frankefelipe.test_backend_java.api.exception;

public class JogadorNaoEncontradoException extends RuntimeException {
    public JogadorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
