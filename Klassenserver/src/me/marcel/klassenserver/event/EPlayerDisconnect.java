package me.marcel.klassenserver.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import me.marcel.klassenserver.Inventory.InventoryManager;;

public class EPlayerDisconnect implements Listener {

	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(InventoryManager.exists(player.getUniqueId())){
           Inventory invOfPlayer = player.getInventory();
           invOfPlayer = InventoryManager.getInventoryByUUID(player.getUniqueId());
           InventoryManager.remove(player.getUniqueId());
           player.updateInventory(); 
        }
	}
	
}