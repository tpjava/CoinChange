package com.example.exceptions;

import java.io.IOException;

/**
 * Created by tonyperrin on 26/10/2016.
 */
public class PropertPersistingException extends RuntimeException {
    public PropertPersistingException(String message) {
        super(message);
    }

    public PropertPersistingException(String message, IOException e) {
        super(message, e);
    }
}
