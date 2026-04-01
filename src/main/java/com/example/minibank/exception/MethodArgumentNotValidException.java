package com.example.minibank.exception;

public class MethodArgumentNotValidException extends RuntimeException{

    public MethodArgumentNotValidException(String mensagem) {
        super(mensagem);
    }
}
