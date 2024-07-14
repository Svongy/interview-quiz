package org.iq.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final String PROPERTIES_FILE_NAME = "iq_config.properties";

    private static final Properties properties = new Properties();

    public static String readProperty(String key) {
        loadPropertiesFile();

        String property = properties.getProperty(key);

        if (property != null) {
            String indent = "$";
            for (String propertyKey : properties.stringPropertyNames()) {
                if (property.contains(indent + propertyKey)) {
                    property = property.replace(indent + propertyKey, properties.getProperty(propertyKey));
                }
            }
        }

        return property;
    }

    private static void loadPropertiesFile() {
        try {
            properties.load(ConfigReader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        }
        catch (IOException e) {
            System.out.printf("The properties file %s cannot be found. Please check it. Original error is: %s",
                    PROPERTIES_FILE_NAME, e.getMessage());
        }
    }

}
