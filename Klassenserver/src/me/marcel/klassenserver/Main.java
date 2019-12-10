package me.marcel.klassenserver;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.marcel.klassenserver.command.CommandManager;
import me.marcel.klassenserver.config.ConfigManager;
import me.marcel.klassenserver.event.EventManager;
import me.marcel.klassenserver.route.RouteManager;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		
		// Konfigurationsdateien
		ConfigManager.editor("routes").create();
		
		// Events
		EventManager.init(this);
		EventManager.registerEvents();
		
		// Commands
		CommandManager.init(this);
		CommandManager.registerCommands();
		
		// RouteManager
		RouteManager.loadRoutes();
		
		// Logging
		Bukkit.getConsoleSender().sendMessage("§8[§aKlassenserver§8] §bDas Plugin wurde aktiviert");
		
	}
	
	@Override
	public void onDisable() {
	
		// Logging
		Bukkit.getConsoleSender().sendMessage("§8[§aKlassenserver§8] §bDas Plugin wurde deaktiviert");
		
	}
	
}