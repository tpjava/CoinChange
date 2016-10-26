package com.example.persistence;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

/**
 * Created by tonyperrin.
 */
@Component
public class PropertyPersister {

    private static final String DEFAULT_PROPERTIES_FILE = "coin-inventory.properties";
    private Properties properties;
    private String filepath;

    public PropertyPersister() {
        initPropertiesFile();
    }

    public PropertyPersister(String filepath) {
        this.filepath = filepath;
        initPropertiesFileFromStream(filepath);
    }

    private void initPropertiesFileFromStream(String filepath) {
        if(properties == null) {
            properties = new Properties();
            try {
                InputStream inputStream = new FileInputStream(filepath);
                properties.load(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initPropertiesFile() {
        if(properties == null) {
            properties = new Properties();
            try {
                InputStream inputStream = PropertyPersister.class.getClassLoader().getResource(DEFAULT_PROPERTIES_FILE).openStream();
                properties.load(inputStream);
                inputStream.close();
                filepath = PropertyPersister.class.getClassLoader().getResource(DEFAULT_PROPERTIES_FILE).getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getProperty(int amount) {
        return Integer.valueOf(properties.getProperty(String.valueOf(amount)));
    }

    public void setProperty(int denomination, int count) {
        properties.setProperty(String.valueOf(denomination), String.valueOf(count));
        storeProperties();
    }

    private void storeProperties() {
        try {
            OutputStream outputStream = new FileOutputStream(filepath);
            properties.store(outputStream, null);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
