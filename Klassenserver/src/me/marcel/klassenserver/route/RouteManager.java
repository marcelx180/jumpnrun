package me.marcel.klassenserver.route;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

import me.marcel.klassenserver.config.ConfigManager;

public class RouteManager {

	private static List<Route> routes = new ArrayList<Route>();
	
	public static void add(Route route) {
		if (!(exists(route.getName()))) {
			routes.add(route);
		}
	}
	public static void remove(String name){
		if(exists(name)){
			for(Route route : routes){
				if(route.getName().equalsIgnoreCase(name)){
					routes.remove(route);
				}
			}
		}
	}
	public static void update(Route route) {
		for(int i = 0; i < routes.toArray().length ; i++) {
			if(routes.get(i).getName().equalsIgnoreCase(route.getName())) {
				routes.set(i, route);
			}
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
		Route returnRoute = null;
		for(Route route : routes) {
			if(route.getName().equalsIgnoreCase(name)) {
				returnRoute = route;
			}
		}
		return returnRoute;
	}
	
	public static void loadRoutes() {
		for (String route : ConfigManager.editor("routes").getStringList("routes")) {
			if (!(ConfigManager.editor("routes").getString(route + ".start.world") == null)) {
				
				List<Location> checkpoints = ConfigManager.editor("routes").getCheckpoints(route + ".checkpoints", route + ".checkpoint"); 
				Location start = ConfigManager.editor("routes").getLocation(route +".start");			
				
				new Route(route, start, checkpoints);
				
			} else {
				// Location nicht vollständig
			}
		}
	}
	
}
