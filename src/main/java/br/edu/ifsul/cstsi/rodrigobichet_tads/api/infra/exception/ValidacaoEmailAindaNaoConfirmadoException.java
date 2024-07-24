package br.edu.ifsul.cstsi.rodrigobichet_tads.api.infra.exception;

public class ValidacaoEmailAindaNaoConfirmadoException extends RuntimeException {
    public ValidacaoEmailAindaNaoConfirmadoException(String message) {
        super(message);
    }
}