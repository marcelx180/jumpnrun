package me.marcel.klassenserver.event;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class EventManager {

	private static JavaPlugin plugin;
	
	public static void init(JavaPlugin pl) {
		plugin = pl;
	}
	
	public static void registerEvents() {
		register(new EPlayerJoin());
		register(new ESignChange());
		register(new EPlayerInteract());
		register(new EPlayerDisconnect());
		register(new EPlayerEnterCheckpoint());
	}
	
	private static void register(Listener listener) {
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}
	
}