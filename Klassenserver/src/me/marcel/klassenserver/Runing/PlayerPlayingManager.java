package me.marcel.klassenserver.Runing;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;



public class PlayerPlayingManager {

    private static HashMap<UUID, List<Location>> playerPlayingCheckpoints = new HashMap<UUID, List<Location>>();
    private static HashMap<UUID, Location> playerInitialLocation = new HashMap<UUID, Location>();
    private static HashMap<UUID, ItemStack[]> playerInventory = new HashMap<UUID, ItemStack[]>();
    private static HashMap<UUID, Location> lastCheckpoint = new HashMap<UUID, Location>();
    private static HashMap<UUID, String> routeName = new HashMap<UUID, String>();

	public static void add(UUID playerID,Location start, List<Location> checkpoints, Location location, ItemStack[] inventory, String name) {
		if (!(exists(playerID))) {
			lastCheckpoint.put(playerID, start);
            playerPlayingCheckpoints.put(playerID, checkpoints);
            playerInitialLocation.put(playerID, location);
            playerInventory.put(playerID, inventory);
            routeName.put(playerID, name);
		}
    }
    
    public static boolean anyInRun(){
        boolean returnExist = false;

       for(List<Location> route : playerPlayingCheckpoints.values()){  
    	   if(route != null) {
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
            List<Location> checkpoints = playerPlayingCheckpoints.get(playerID);
            checkpoints.remove(checkpoints.get(0));
            playerPlayingCheckpoints.remove(playerID);
            playerPlayingCheckpoints.put(playerID, checkpoints);
		}
	}

    public static void remove(UUID playerID) {
		if (exists(playerID)) {
			lastCheckpoint.remove(playerID);
            playerPlayingCheckpoints.remove(playerID);
            playerInitialLocation.remove(playerID);
            playerInventory.remove(playerID);
            routeName.remove(playerID);
		}
	}
    
    public static String getRouteName (UUID playerID) {
    	String returnName = "";
    	if(exists(playerID)) {
    		returnName = routeName.get(playerID);
    	}
    	return returnName;
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
	
	public static List<Location> getCheckpointsByUUID(UUID playerId) {
        List<Location> returnCheckpoints = null;
        if(exists(playerId)){
            returnCheckpoints = playerPlayingCheckpoints.get(playerId);
        }
		
		return returnCheckpoints;
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
