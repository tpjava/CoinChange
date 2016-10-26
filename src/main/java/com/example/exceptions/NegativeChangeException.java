package com.example.exceptions;

/**
 * Created by tonyperrin.
 */
public class NegativeChangeException extends RuntimeException {
    public NegativeChangeException(String message) {
        super(message);
    }
}
