package me.marcel.klassenserver.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;

import me.marcel.klassenserver.config.ConfigManager;

public class RouteManager {

	private static List<Route> routes = new ArrayList<Route>();
	private static Map<String, Route> routesWithName = new HashMap<String, Route>();
	
	public static void add(Route route) {
		if (!(exists(route.getName()))) {
			routes.add(route);
			routesWithName.put(route.getName(), route);
		}
	}
	
	public static boolean exists(String name) {
		boolean exists = false;
		
		for (Route route : routes) {
			if (route.getName().equalsIgnoreCase(name)) {
				exists = true;
			}
		}
		
		return exists;
	}
	
	public static Route getRouteByName(String name) {
		return routesWithName.get(name);
	}
	
	public static void loadRoutes() {
		for (String route : ConfigManager.editor("routes").getStringList("routes")) {
			if (!(ConfigManager.editor("routes").getString(route + ".start.world") == null)) {
				
				List<Location> checkpoints = ConfigManager.editor("routes").getCheckpoints(route+".checkpoints"); 
				Location start = ConfigManager.editor("routes").getLocation(route + ".start");
				
				new Route(route, start, checkpoints);
				
			} else {
				// Location nicht vollständig
			}
		}
	}
	
}
