package com.example.model.impl;

/**
 * Created by tonyperrin.
 */
public class Denomination {
    private final String name;
    private int value;

    public Denomination(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
