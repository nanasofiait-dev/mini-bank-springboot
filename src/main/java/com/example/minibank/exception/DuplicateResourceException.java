package com.example.minibank.exception;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException( String mensagem ) {
        super(mensagem);
    }
}
