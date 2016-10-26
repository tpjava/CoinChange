package com.example.exceptions;

/**
 * Created by tonyperrin
 */
public class InsufficientCoinageException extends RuntimeException {
    public InsufficientCoinageException(String message) {
        super(message);
    }
}
