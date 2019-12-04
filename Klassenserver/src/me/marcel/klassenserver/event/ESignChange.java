package me.marcel.klassenserver.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.marcel.klassenserver.route.RouteManager;

public class ESignChange implements Listener {

	@EventHandler
	public void onChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		
		if (event.getLine(0).equalsIgnoreCase("klassenserver") || event.getLine(0).equalsIgnoreCase("ks")) {
			if (!(event.getLine(2) == null || event.getLine(2).equalsIgnoreCase(""))) {
				if (RouteManager.exists(event.getLine(2))) {
					event.setLine(0, "§b[§5Klassenserver§b]");
					event.setLine(1, "");
					event.setLine(2, event.getLine(2));
					event.setLine(3, "");
				} else {
					event.setLine(0, "§cNicht gefunden!");
					player.sendMessage("§8[§aKlassenserver§8] §cDas angegebene JumpNRun existiert nicht!");
				}
			} else {
				event.setLine(0, "§cFehlender Name!");
				player.sendMessage("§8[§aKlassenserver§8] §cFehlender Name!");
			}
		}
		
	}
	
}