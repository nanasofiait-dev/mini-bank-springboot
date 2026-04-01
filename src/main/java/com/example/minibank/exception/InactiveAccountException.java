package com.example.minibank.exception;

public class InactiveAccountException extends RuntimeException{

    public InactiveAccountException(String mensagem) {
        super(mensagem);
    }
}
