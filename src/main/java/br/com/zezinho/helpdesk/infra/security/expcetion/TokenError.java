package br.com.zezinho.helpdesk.infra.security.expcetion;

public class TokenError extends RuntimeException{
    public TokenError(String message) {
        super(message);
    }

    public TokenError(String message, Throwable cause) {
        super(message, cause);
    }
}
