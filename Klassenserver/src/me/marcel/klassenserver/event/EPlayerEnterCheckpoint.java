package me.marcel.klassenserver.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;
import me.marcel.klassenserver.route.Route;
import me.marcel.klassenserver.route.RouteManager;


public class EPlayerEnterCheckpoint implements Listener {

	@EventHandler
	public void onEnter(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if(PlayerPlayingManager.exists(player.getUniqueId())){
            Location checkpointLocation = PlayerPlayingManager.getRouteByUUID(player.getUniqueId()).getCheckpoints().get(0);
            if(player.getLocation().getBlockX() == checkpointLocation.getBlockX() && player.getLocation().getBlockY() == checkpointLocation.getBlockY() && player.getLocation().getBlockZ() == checkpointLocation.getBlockZ() ){
                Route route = PlayerPlayingManager.getRouteByUUID(player.getUniqueId());

                Integer maxCheckpoint = RouteManager.getRouteByName(route.getName()).getCheckpoints().size();
                Integer checkpoint = route.getCheckpoints().size();
            
                if(checkpoint == 0){
                    Inventory inv = player.getInventory();
                    inv = PlayerPlayingManager.getInventoryByUUID(player.getUniqueId());
                    player.updateInventory();
                    player.teleport(PlayerPlayingManager.getLocationByUUID(player.getUniqueId()));
                    PlayerPlayingManager.remove(player.getUniqueId());
                    player.sendMessage("§b[§5Klassenserver§b] Herzlichen Glückwunsch du hast das Jump'n Run " + route.getName() + "geschafft!");
                }else{
                    checkpoint = maxCheckpoint - checkpoint;          
                    player.sendMessage("§b[§5Klassenserver§b] Du hast Checkpoint §6" + checkpoint.toString() + "§b von §2" + maxCheckpoint.toString() + "§b erreicht");
                    PlayerPlayingManager.removeCheckpoint(player.getUniqueId());
                }            
            }
        }
	}	
}