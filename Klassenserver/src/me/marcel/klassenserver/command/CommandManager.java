package me.marcel.klassenserver.command;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {

	private static JavaPlugin plugin;
	
	public static void init(JavaPlugin pl) {
		plugin = pl;
	}
	
	public static void registerCommands() {
		plugin.getCommand("klassenserver").setExecutor(new CKlassenserver());
	}
	
}