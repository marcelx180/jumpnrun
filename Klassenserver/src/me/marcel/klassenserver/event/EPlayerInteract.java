package me.marcel.klassenserver.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;
import me.marcel.klassenserver.route.Route;
import me.marcel.klassenserver.route.RouteManager;

public class EPlayerInteract implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {

			if (event.getClickedBlock().getType() == Material.ACACIA_SIGN || event.getClickedBlock().getType() == Material.ACACIA_WALL_SIGN || event.getClickedBlock().getType() == Material.BIRCH_SIGN || event.getClickedBlock().getType() == Material.BIRCH_WALL_SIGN || event.getClickedBlock().getType() == Material.DARK_OAK_SIGN || event.getClickedBlock().getType() == Material.DARK_OAK_WALL_SIGN || event.getClickedBlock().getType() == Material.JUNGLE_SIGN || event.getClickedBlock().getType() == Material.JUNGLE_WALL_SIGN || event.getClickedBlock().getType() == Material.OAK_SIGN || event.getClickedBlock().getType() == Material.OAK_WALL_SIGN || event.getClickedBlock().getType() == Material.OAK_WALL_SIGN || event.getClickedBlock().getType() == Material.SPRUCE_SIGN || event.getClickedBlock().getType() == Material.SPRUCE_WALL_SIGN ) {
				
				Sign sign = (Sign) event.getClickedBlock().getState();
				
				List<String> lore = new ArrayList<String>();
				
				try {
					lore = event.getItem().getItemMeta().getLore();
				}catch(Exception ex){
					
				} 
					if(lore.toArray().length > 0 && lore.get(0).equalsIgnoreCase("§b[§5Klassenserver§b]") && lore.get(1).equalsIgnoreCase("Klicke hiermit auf ein Schild") && lore.get(2).equalsIgnoreCase("und es wird zum Start Schild für das Jump'n Run:")) {				
						
						String name = lore.get(3);
						sign.setLine(0, "§b[§5Klassenserver§b]");
						sign.setLine(1, "Jump'n Run:");
						sign.setLine(2, name);
						sign.setLine(3, "Klicke zum spielen");
						
						sign.update();
						
					}				
					else if (sign.getLine(0).equalsIgnoreCase("§b[§5Klassenserver§b]")) {
					String routeName = sign.getLine(2);
					if (RouteManager.exists(routeName)) {
						Route route = RouteManager.getRouteByName(routeName);
						
						player.sendMessage("§8[§aKlassenserver§8] §c Du hast das Jump'n Run " + route.getName() + " betreten!");
						Inventory inv = player.getInventory();
						PlayerPlayingManager.add(player.getUniqueId(), route, player.getLocation(), inv);
						inv.clear();
						ItemStack stop = new ItemStack(Material.REDSTONE, 1);
						ItemMeta stopMeta = stop.getItemMeta();
						stopMeta.setDisplayName("§l§4Stop");
						stopMeta.setLocalizedName("Stop");
						List<String> stopLore = new ArrayList<String>();
						stopLore.add("Rechts-klicke dieses Item");
						stopLore.add("um das Jump'n Run zu stoppen");
						stopMeta.setLore(stopLore);
						stop.setItemMeta(stopMeta);
						inv.setItem(8, stop);
						ItemStack backToCheckpoint = new ItemStack(Material.BLAZE_ROD, 1);
						ItemMeta checkpointMeta = backToCheckpoint.getItemMeta();
						checkpointMeta.setDisplayName("§l§6Zurück zum letzen Checkpoint");
						checkpointMeta.setLocalizedName("Checkpoint");
						List<String> checkpointLore = new ArrayList<String>();
						checkpointLore.add("Rechts-klicke dieses Item");
						checkpointLore.add("um zum letzen Checkpoint zu gelangen");
						checkpointMeta.setLore(checkpointLore);
						backToCheckpoint.setItemMeta(checkpointMeta);
						inv.setItem(0, backToCheckpoint);
						player.getInventory().setContents(inv.getContents());
						player.updateInventory();
						player.teleport(route.getStart());
					}
				}
			}else if(event.getItem().getItemMeta().getLocalizedName() == "Stop" ){
					
				player.getInventory().setContents(PlayerPlayingManager.getInventoryByUUID(player.getUniqueId()).getContents());
                Location signClickedLocation = PlayerPlayingManager.getLocationByUUID(player.getUniqueId());
                player.teleport(signClickedLocation);
                PlayerPlayingManager.remove(player.getUniqueId());
				player.updateInventory(); 
			
			}else if(event.getItem().getItemMeta().getLocalizedName() == "Checkpoint"){
				Location lastCheckpoint = PlayerPlayingManager.getRouteByUUID(player.getUniqueId()).getCheckpoints().get(0);
				player.teleport(lastCheckpoint);
			}
		}
	}
}
