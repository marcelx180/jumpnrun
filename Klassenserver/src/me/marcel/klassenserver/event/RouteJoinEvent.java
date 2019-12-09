package me.marcel.klassenserver.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

import me.marcel.klassenserver.Inventory.InventoryManager;
import me.marcel.klassenserver.route.Route;

public class RouteJoinEvent extends Event {

	public static HandlerList handlers = new HandlerList();

	private Player player;
	private Route route;

	public RouteJoinEvent(Player player, Route route) {
		this.player = player;
		this.route = route;

		player.sendMessage("§8[§aKlassenserver§8] §c Du hast das Jump'n Run" + route.getName() + "betreten");
		Inventory inv = player.getInventory();
		InventoryManager.add(player.getUniqueId(), inv);
	}

	public Player getPlayer() {
		return this.player;
	}

	public Route getRoute() {
		return this.route;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}