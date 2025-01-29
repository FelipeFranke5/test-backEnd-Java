package br.com.frankefelipe.test_backend_java.api.exception;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class CapturaExcecoes {

    private final static Logger logger = LoggerFactory.getLogger(CapturaExcecoes.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JogadorNaoEncontradoException.class)
    public RespostaErro capturaJogadorNaoEncontradoException(JogadorNaoEncontradoException erro) {
        return new RespostaErro(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), erro.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequisicaoInvalidaException.class)
    public RespostaErro capturaRequisicaoInvalidaException(RequisicaoInvalidaException erro) {
        String mensagem = getMensagem(erro);
        return new RespostaErro(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), mensagem);
    }

    private String getMensagem(Exception erro) {
        return erro.getMessage().contains("problem:") ? erro.getMessage().split("problem: ")[1] : erro.getMessage();
    }

    private String getMensagem2(Exception erro) {
        return erro.getMessage().contains("Required request body is missing") ? "O payload é obrigatório" : getMensagem(erro);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChamadaCodinomeException.class)
    public RespostaErro capturaChamadaCodinomeException(ChamadaCodinomeException erro) {
        return new RespostaErro(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), erro.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CodinomesEmUsoException.class)
    public RespostaErro capturaCodinomesEmUsoException(CodinomesEmUsoException erro) {
        return new RespostaErro(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), erro.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RespostaErro capturaRotaNaoMapeada(NoHandlerFoundException erro) {
        String mensagem = "Rota não mapeada";
        return new RespostaErro(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), mensagem);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RespostaErro capturaErroMensagemHttp(HttpMessageNotReadableException erro) {
        logger.error("Erro de leitura aqui: {}", erro.getMessage(), erro);
        String mensagem = getMensagem2(erro);
        return new RespostaErro(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), mensagem);
    }

}
