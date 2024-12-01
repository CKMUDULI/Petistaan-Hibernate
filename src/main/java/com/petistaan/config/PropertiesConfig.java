package com.petistaan.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.petistaan.exception.InternalServiceException;

public class PropertiesConfig {
	private static final PropertiesConfig INSTANCE = new PropertiesConfig();
	private final Properties properties = new Properties();

	private PropertiesConfig() {
		loadProperties(List.of("messages.properties"));
	}

	private void loadProperties(List<String> filesToLoad) {
		for (String fileToLoad : filesToLoad) {
			try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileToLoad)) {
				if (inputStream != null) {
					properties.load(inputStream);
				} else {
					throw new InternalServiceException("Failed to load file: " + fileToLoad);
				}
			} catch (IOException e) {
				throw new InternalServiceException("Error reading file: " + fileToLoad);
			}
		}
	}

	public static PropertiesConfig getInstance() {
		return INSTANCE;
	}

	public String getProperty(String key) {
		return properties.getProperty(key, "Message not found for key: " + key);
	}
}
