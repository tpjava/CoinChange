package com.example;

import com.example.properties.PropertyManager;
import org.junit.After;
import org.junit.Before;

/**
 * Created by tonyperrin.
 */
public class TestBase {

    @Before
    public void setUp() {
        initProperties();
    }

    @After
    public void tearDown() {
        initProperties();
    }

    protected void initProperties() {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.setProperty(100, 2);
        propertyManager.setProperty(50, 3);
        propertyManager.setProperty(20, 3);
        propertyManager.setProperty(10, 4);
        propertyManager.setProperty(5, 5);
        propertyManager.setProperty(2, 3);
        propertyManager.setProperty(1, 2);
    }

}
