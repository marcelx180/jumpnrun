package me.marcel.klassenserver.Inventory;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.inventory.Inventory;


public class InventoryManager {

	private static HashMap<UUID, Inventory> playerInventory = new HashMap<UUID, Inventory>();
	
	public static void add(UUID playerID, Inventory inventory) {
		if (!(exists(playerID))) {
			playerInventory.put(playerID, inventory);
		}
    }
    
    public static void remove(UUID playerID) {
		if (!(exists(playerID))) {
			playerInventory.remove(playerID);
		}
	}
	
	public static boolean exists(UUID id) {
		boolean exists = false;
		
        for (UUID playerID : playerInventory.keySet()) {
			if (id == playerID) {
				exists = true;
			}
		}
		
		return exists;
	}
	
	public static Inventory getInventoryByUUID(UUID playerId) {
        Inventory returnInventory = null;
        if(exists(playerId)){
            returnInventory= playerInventory.get(playerId);
        }
		
		return returnInventory;
	}
	
}
