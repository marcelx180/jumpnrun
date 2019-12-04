package me.marcel.klassenserver.command;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.marcel.klassenserver.config.ConfigManager;
import me.marcel.klassenserver.route.RouteManager;

public class CKlassenserver implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 0) {
				player.sendMessage("§8[§aKlassenserver§8] §6/ks create <name>");
				player.sendMessage("§8[§aKlassenserver§8] §6/ks delete <name>");
				player.sendMessage("§8[§aKlassenserver§8] §6/ks start <name>");
			} else if (args.length == 1) {
				
				// /ks create (name is missing)
				if (args[0].equalsIgnoreCase("create")) {
					player.sendMessage("§8[§aKlassenserver§8] §cFehlender Name!");
				}
				
				// /ks delete (name is missing)
				if (args[0].equalsIgnoreCase("delete")) {
					player.sendMessage("§8[§aKlassenserver§8] §cFehlender Name!");
				}
				
			} else if (args.length == 2) {
				
				// /ks create <name>
				if (args[0].equalsIgnoreCase("create")) {
					String name = args[1];
					
					if (!(RouteManager.exists(name))) {
						List<String> routes = ConfigManager.editor("routes").getStringList("routes");
						routes.add(name);
						
						ConfigManager.editor("routes").update("routes", routes);
						
						player.sendMessage("§8[§aKlassenserver§8] §bDas JumpNRun wurde erstellt!");
						player.sendMessage("§8[§aKlassenserver§8] §bBitte Startlocation festlegen: /ks start <name>");
					} else {
						player.sendMessage("§8[§aKlassenserver§8] §cExistiert bereits!");
					}
				}
				
				// //ks start <name>
				if (args[0].equalsIgnoreCase("start")) {
					String name = args[1];
					
					if (RouteManager.exists(name)) {
						Location location = player.getLocation();
						
						String world = location.getWorld().getName();
						double x = location.getX();
						double y = location.getY();
						double z = location.getZ();
						float yaw = location.getYaw();
						float pitch = location.getPitch();
						
						ConfigManager.editor("routes").update(name + ".start.world", world);
						ConfigManager.editor("routes").update(name + ".start.x", x);
						ConfigManager.editor("routes").update(name + ".start.y", y);
						ConfigManager.editor("routes").update(name + ".start.z", z);
						ConfigManager.editor("routes").update(name + ".start.yaw", yaw);
						ConfigManager.editor("routes").update(name + ".start.pitch", pitch);
						
						player.sendMessage("§8[§aKlassenserver§8] §bDie Startlocation wurde gesetzt!");
					} else {
						player.sendMessage("§8[§aKlassenserver§8] §cExistiert nicht!");
					}
				}
				
			} else {
				player.sendMessage("§8[§aKlassenserver§8] §cFalsche Syntax!");
			}
		} else {
			sender.sendMessage("§8[§aKlassenserver§8] §cDer Befehl kann nur von Spielern genutzt werden!");
		}
		
		return true;
	}

}