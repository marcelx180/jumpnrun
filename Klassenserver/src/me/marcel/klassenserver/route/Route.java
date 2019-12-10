package me.marcel.klassenserver.route;

import java.util.List;

import org.bukkit.Location;

public class Route {

	private String name;
	private Location start;
	private List<Location> checkpoints;
	
	public Route(String name, Location start, List<Location> checkpoints) {
		this.name = name;
		this.start = start;
		this.checkpoints = checkpoints;
	}
	
	public void managerAdd() {
		RouteManager.add(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Location getStart() {
		return this.start;
	}

	public List<Location> getCheckpoints() {
		return checkpoints;
	}

	
}