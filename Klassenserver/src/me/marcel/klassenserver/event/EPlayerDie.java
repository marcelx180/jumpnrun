package me.marcel.klassenserver.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;

public class EPlayerDie implements Listener {
	@EventHandler
	public void onDie(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(PlayerPlayingManager.exists(player.getUniqueId())) {
			Location lastCheckpoint = PlayerPlayingManager.getLastCheckpoint(player.getUniqueId());
			player.teleport(lastCheckpoint);
			event.setKeepInventory(true);
			event.setKeepLevel(true);
		}else {
			event.setKeepInventory(false);
			event.setKeepLevel(false);
		}
	}
}
