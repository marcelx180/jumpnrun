package me.marcel.klassenserver.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;

public class EPlayerRespawn implements Listener{
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if(PlayerPlayingManager.exists(player.getUniqueId())) {
			Location lastCheckpoint = PlayerPlayingManager.getLastCheckpoint(player.getUniqueId());
			event.setRespawnLocation(lastCheckpoint);
		}
	}
}
