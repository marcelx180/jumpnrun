package me.marcel.klassenserver.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;

public class EPlayerItemDrop implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(PlayerPlayingManager.exists(player.getUniqueId())){
            event.setCancelled(true);
        }
	}
}