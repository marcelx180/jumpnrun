package me.marcel.klassenserver.route;

import org.bukkit.Location;

public class Route {

	private String name;
	private Location start;
	
	public Route(String name, Location start) {
		this.name = name;
		this.start = start;
		
		RouteManager.add(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Location getStart() {
		return this.start;
	}
	
}