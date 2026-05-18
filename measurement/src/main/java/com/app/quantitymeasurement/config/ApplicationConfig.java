package com.app.quantitymeasurement.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {
    private static ApplicationConfig instance;
    private final Properties props = new Properties();

    private ApplicationConfig() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) instance = new ApplicationConfig();
        return instance;
    }

    public String getProperty(String key, String defaultValue) {
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }
    
    

    public String getUrl() { return getProperty("db.url", null); }
    public String getUser() { return getProperty("db.user", "sa"); }
    public String getPassword() { return getProperty("db.password", ""); }
}