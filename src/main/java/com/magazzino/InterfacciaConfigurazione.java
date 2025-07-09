package com.magazzino;

import java.io.InputStream;
import java.util.Properties;

public class InterfacciaConfigurazione {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = InterfacciaConfigurazione.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(props.getProperty(key));
    }

    public static String[] getArray(String key) {
        String val = props.getProperty(key);
        if (val == null || val.isEmpty()) return new String[0];
        return val.split("\\s*,\\s*"); 
    }

}