package org.example.console.exceptions;

public class UnknownOperationExeption extends RuntimeException{
    public UnknownOperationExeption(String message) {
        super(message);
    }
}
