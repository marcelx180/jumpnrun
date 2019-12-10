package me.marcel.klassenserver.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;



public class EPlayerDisconnect implements Listener {

	@EventHandler
        public void onDisconnect(PlayerQuitEvent event) {
                Player player = event.getPlayer();
                if(PlayerPlayingManager.exists(player.getUniqueId())){
                        player.getInventory().clear();
                        ItemStack[] inv = PlayerPlayingManager.getInventoryByUUID(player.getUniqueId());
	                player.getInventory().setContents(inv);		
                        player.updateInventory();
                        Location signClickedLocation = PlayerPlayingManager.getLocationByUUID(player.getUniqueId());
                        player.teleport(signClickedLocation);
                        PlayerPlayingManager.remove(player.getUniqueId());
                        player.updateInventory(); 
                }
	}
	
}