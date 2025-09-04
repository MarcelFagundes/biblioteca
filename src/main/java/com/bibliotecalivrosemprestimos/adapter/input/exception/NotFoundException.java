package com.bibliotecalivrosemprestimos.adapter.input.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}