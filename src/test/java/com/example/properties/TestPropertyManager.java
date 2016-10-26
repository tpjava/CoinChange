package com.example.properties;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tonyperrin.
 */
public class TestPropertyManager {
    @Test
    public void testProperties() {
        PropertyManager propertyManager = new PropertyManager();
        assertEquals(propertyManager.getProperty(100), 1);
    }
}
