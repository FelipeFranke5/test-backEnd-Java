package br.com.frankefelipe.test_backend_java.api.exception;

public class ChamadaCodinomeException extends RuntimeException {
    public ChamadaCodinomeException(String mensagem) {
        super(mensagem);
    }
}
