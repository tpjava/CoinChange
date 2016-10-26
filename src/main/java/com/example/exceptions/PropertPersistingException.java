package com.example.exceptions;

import java.io.IOException;

/**
 * Created by tonyperrin.
 */
public class PropertPersistingException extends RuntimeException {
    public PropertPersistingException(String message) {
        super(message);
    }

    public PropertPersistingException(String message, IOException e) {
        super(message, e);
    }
}
