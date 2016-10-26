package com.example.exceptions;

/**
 * Created by tonyperrin.
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
