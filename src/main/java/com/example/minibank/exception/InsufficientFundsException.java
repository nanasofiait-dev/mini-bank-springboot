package com.example.minibank.exception;

public class InsufficientFundsException extends RuntimeException{

    public InsufficientFundsException( String mensagem) {
        super( mensagem);
    }
}
