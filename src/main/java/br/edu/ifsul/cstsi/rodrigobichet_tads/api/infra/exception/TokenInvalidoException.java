package br.edu.ifsul.cstsi.rodrigobichet_tads.api.infra.exception;

public class TokenInvalidoException extends RuntimeException{
    public TokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}
