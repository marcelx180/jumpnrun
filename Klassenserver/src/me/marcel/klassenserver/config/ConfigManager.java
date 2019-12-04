package me.marcel.klassenserver.config;

public class ConfigManager {

	public static ConfigEditor editor(String configName) {
		return new ConfigEditor(configName);
	}
	
}