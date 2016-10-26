package com.example.properties;

import com.example.TestBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tonyperrin.
 */
public class TestPropertyManager extends TestBase {
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testProperties() {
        PropertyManager propertyManager = new PropertyManager();
        assertEquals(propertyManager.getProperty(100), 2);
    }
}
