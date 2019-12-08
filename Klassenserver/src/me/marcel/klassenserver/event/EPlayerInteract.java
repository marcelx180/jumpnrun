package me.marcel.klassenserver.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.marcel.klassenserver.route.Route;
import me.marcel.klassenserver.route.RouteManager;

public class EPlayerInteract implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

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
							
						player.teleport(route.getStart());
							
						Bukkit.getPluginManager().callEvent(new RouteJoinEvent(player, route));
											
					}
				}
			}
		}
	}
}
