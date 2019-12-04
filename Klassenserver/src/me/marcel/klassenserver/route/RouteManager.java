package me.marcel.klassenserver.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
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
			if (!(ConfigManager.editor("routes").getString(route + ".world") == null)) {
				
				String world = ConfigManager.editor("routes").getString(route + ".start.world");
				double x = ConfigManager.editor("routes").getDouble(route + ".start.x");
				double y = ConfigManager.editor("routes").getDouble(route + ".start.y");
				double z = ConfigManager.editor("routes").getDouble(route + ".start.z");
				float pitch = (float) ConfigManager.editor("routes").getDouble(route + ".start.pitch");
				float yaw = (float) ConfigManager.editor("routes").getDouble(route + ".start.yaw");
				
				Location start = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
				
				new Route(route, start);
				
			} else {
				// Location nicht vollständig
			}
		}
	}
	
}