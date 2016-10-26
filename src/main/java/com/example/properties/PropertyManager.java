package com.example.properties;

import com.example.exceptions.PropertPersistingException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

/**
 * Created by tonyperrin.
 */
@Component
public class PropertyManager {
    private static final String PROPERTIES_PATH = "coin-inventory.properties";
    private boolean storeUpdates = true;
    private Properties properies;

    public PropertyManager() {
        this(false);
    }

    public PropertyManager(boolean storeUpdates) {
        this.storeUpdates = storeUpdates;
        init();
    }

    private void init() {
        if(properies == null) {
            try {
                properies = new Properties();
                properies.load(PropertyManager.class.getResourceAsStream(File.separator + PROPERTIES_PATH));
            } catch (IOException e) {
                throw new RuntimeException("Could not find property file " + PROPERTIES_PATH + " + on path.", e);
            }
        }
    }

    public int getProperty(int denomination) {
        return properies.getProperty(String.valueOf(denomination)) == null ? 0 : Integer.parseInt(properies.getProperty(String.valueOf(denomination)));
    }

    public void setProperty(int denomination, int value) {
        if(properies.getProperty(String.valueOf(denomination)) != null) {
            properies.setProperty(String.valueOf(denomination), String.valueOf(value));
        }
        try {
            OutputStream outputStream = new FileOutputStream(new File(PropertyManager.class.getClassLoader().getResource(PROPERTIES_PATH).getPath()));
            properies.store(outputStream, null);
        } catch (FileNotFoundException e) {
            throw new PropertPersistingException("Could not find properties file in able to store new property values.");
        } catch (IOException e) {
            throw new PropertPersistingException("Could not store property", e);
        }
    }
}
