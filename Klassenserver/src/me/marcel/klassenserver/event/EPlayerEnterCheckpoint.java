package me.marcel.klassenserver.event;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;
import me.marcel.klassenserver.route.RouteManager;


public class EPlayerEnterCheckpoint implements Listener {

	@EventHandler
	public void onEnter(PlayerMoveEvent event) {
		
        Player player = event.getPlayer();
        if(PlayerPlayingManager.exists(player.getUniqueId())){
            List<Location> checkpoints = PlayerPlayingManager.getCheckpointsByUUID(player.getUniqueId());
            
            if(checkpoints.size() > 0){
            	Location checkpointLocation = checkpoints.get(0);
                if(player.getLocation().getBlockX() == checkpointLocation.getBlockX() && player.getLocation().getBlockY() == checkpointLocation.getBlockY() && player.getLocation().getBlockZ() == checkpointLocation.getBlockZ() ){
                    
    
                    Integer maxCheckpoint = RouteManager.getRouteByName(PlayerPlayingManager.getRouteName(player.getUniqueId())).getCheckpoints().size();
                    Integer checkpoint = checkpoints.size();
                
                    if(checkpoint == 1){
                        player.getInventory().clear();
                        ItemStack[] inv = PlayerPlayingManager.getInventoryByUUID(player.getUniqueId());
					    player.getInventory().setContents(inv);		
                        player.updateInventory();
                        player.teleport(PlayerPlayingManager.getLocationByUUID(player.getUniqueId()));
                        PlayerPlayingManager.remove(player.getUniqueId());
                        player.sendMessage("§b[§5Klassenserver§b] Herzlichen Glückwunsch du hast das Jump'n Run " + PlayerPlayingManager.getRouteName(player.getUniqueId()) + "geschafft!");
                    }else{
                        checkpoint = (maxCheckpoint - checkpoint) + 1;          
                        player.sendMessage("§b[§5Klassenserver§b] Du hast Checkpoint §6" + checkpoint.toString() + "§b von §2" + maxCheckpoint.toString() + "§b erreicht");
                        PlayerPlayingManager.setLastCheckpoint(player.getUniqueId(), checkpointLocation);
                        PlayerPlayingManager.removeCheckpoint(player.getUniqueId());
                    }            
                }
            }
        }
	}	
}