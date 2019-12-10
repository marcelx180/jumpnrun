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
		register(new EPlayerItemDrop());
		register(new EPlayerDie());
		register(new EPlayerRespawn());
		
	}
	
	private static void register(Listener listener) {
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}
	
}