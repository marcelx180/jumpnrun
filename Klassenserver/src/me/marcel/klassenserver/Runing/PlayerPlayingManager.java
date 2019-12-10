package me.marcel.klassenserver.Runing;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import me.marcel.klassenserver.route.Route;


public class PlayerPlayingManager {

    private static HashMap<UUID, Route> playerPlayingRoute = new HashMap<UUID, Route>();
    private static HashMap<UUID, Location> playerInitialLocation = new HashMap<UUID, Location>();
    private static HashMap<UUID, ItemStack[]> playerInventory = new HashMap<UUID, ItemStack[]>();
    private static HashMap<UUID, Location> lastCheckpoint = new HashMap<UUID, Location>();

	public static void add(UUID playerID, Route route, Location location, ItemStack[] inventory) {
		if (!(exists(playerID))) {
			lastCheckpoint.put(playerID, route.getStart());
            playerPlayingRoute.put(playerID, route);
            playerInitialLocation.put(playerID, location);
            playerInventory.put(playerID, inventory);
		}
    }
    
    public static boolean anyInRun(String name){
        boolean returnExist = false;

       for(Route route : playerPlayingRoute.values()){
            if(route.getName().equalsIgnoreCase(name)){
                returnExist = true;
            }
       }
       return returnExist;
    }
    
    public static void setLastCheckpoint(UUID playerID, Location checkpoint) {
    	if(exists(playerID)) {
    		lastCheckpoint.remove(playerID);
    		lastCheckpoint.put(playerID, checkpoint);
    	}
    }
    
    public static Location getLastCheckpoint(UUID playerID) {
    	Location returnCheckpoint = null;
    	if(exists(playerID)) {
    		returnCheckpoint = lastCheckpoint.get(playerID);
    	}
    	return returnCheckpoint;
    }

    public static void removeCheckpoint(UUID playerID) {
		if (exists(playerID)) {
            Route route = playerPlayingRoute.get(playerID);
            List<Location> checkpoints = route.getCheckpoints();
            checkpoints.remove(checkpoints.get(0));
            Route newRoute = new Route(route.getName(), route.getStart(), checkpoints);
            playerPlayingRoute.remove(playerID);
            playerPlayingRoute.put(playerID, newRoute);
		}
	}

    public static void remove(UUID playerID) {
		if (exists(playerID)) {
			lastCheckpoint.remove(playerID);
            playerPlayingRoute.remove(playerID);
            playerInitialLocation.remove(playerID);
            playerInventory.remove(playerID);
		}
	}
	
	public static boolean exists(UUID id) {
		boolean exists = false;
		
        for (UUID playerID : playerInitialLocation.keySet()) {
			if (id == playerID) {
				exists = true;
			}
		}
		
		return exists;
	}
	
	public static Route getRouteByUUID(UUID playerId) {
        Route returnRoute = null;
        if(exists(playerId)){
            returnRoute = playerPlayingRoute.get(playerId);
        }
		
		return returnRoute;
    }
    
    public static Location getLocationByUUID(UUID playerId) {
        Location returnLocation = null;
        if(exists(playerId)){
            returnLocation = playerInitialLocation.get(playerId);
        }
		
		return returnLocation;
    }
    
    public static ItemStack[] getInventoryByUUID(UUID playerId) {
        ItemStack[] returnInventory = null;
        if(exists(playerId)){
            returnInventory= playerInventory.get(playerId);
        }
		
		return returnInventory;
	}
	
}
