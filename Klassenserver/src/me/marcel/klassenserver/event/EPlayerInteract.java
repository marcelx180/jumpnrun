package me.marcel.klassenserver.event;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.marcel.klassenserver.route.Route;
import me.marcel.klassenserver.route.RouteManager;

public class EPlayerInteract implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getHand() == EquipmentSlot.HAND) {
				if (event.getClickedBlock() instanceof Sign) {
					Sign sign = (Sign) event.getClickedBlock();
					
					if (sign.getLine(0).equalsIgnoreCase("§b[§5Klassenserver§b]")) {
						String routeName = sign.getLine(2);
						
						if (RouteManager.exists(routeName)) {
							Route route = RouteManager.getRouteByName(routeName);
							
							player.teleport(route.getStart());
						}
					}
				}
			}
		}
	}
	
}