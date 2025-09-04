package com.bibliotecalivrosemprestimos.adapter.input.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}