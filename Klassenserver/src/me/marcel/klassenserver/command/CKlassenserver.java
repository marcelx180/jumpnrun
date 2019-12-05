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
				player.sendMessage("§8[§aKlassenserver§8] §6/ks checkpoint <name> <identifier>");
			} else if (args.length == 1) {
				
				// /ks create (name is missing)
				if (args[0].equalsIgnoreCase("create")) {
					player.sendMessage("§8[§aKlassenserver§8] §cFehlender Name!");
				}
				else if (args[0].equalsIgnoreCase("checkpoint")) {
					player.sendMessage("§8[§aKlassenserver§8] §cFehlender Name und Checkpoint Identifier!");
				}
				
				// /ks delete (name is missing)
				else if (args[0].equalsIgnoreCase("delete")) {
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
						
						player.sendMessage("§8[§aKlassenserver§8] §bDas Jump'n Run wurde erstellt!");
						player.sendMessage("§8[§aKlassenserver§8] §bBitte Start location festlegen: /ks start" + name);
					} else {
						player.sendMessage("§8[§aKlassenserver§8] §cExistiert bereits!");
					}
				}else if(args[0].equalsIgnoreCase("checkpoint")) {
					player.sendMessage("§8[§aKlassenserver§8] §cFehlender Checkpoint Identifier!");
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
						player.sendMessage("§8[§aKlassenserver§8] §bDie Checkpoints können jetzt gesetzt werden mit /ks checkpoint "+name+" <identfier>");
					} else {
						player.sendMessage("§8[§aKlassenserver§8] §cDas Jump'n Run existiert nicht!");
					}
				}
				
			}else if(args.length==3) {
				if(args[0].equalsIgnoreCase("checkpoint")) {
					String name = args[1];
					if (RouteManager.exists(name)) {
						if(RouteManager.exists(name + ".start")) {
								String checkpoint = args[2];
								Location location = player.getLocation();
						
								String world = location.getWorld().getName();
								double x = location.getX();
								double y = location.getY();
								double z = location.getZ();
								float yaw = location.getYaw();
								float pitch = location.getPitch();
						
								ConfigManager.editor("routes").update(name + ".checkpoints."+checkpoint+".world", world);
								ConfigManager.editor("routes").update(name + ".checkpoints."+checkpoint+".x", x);
								ConfigManager.editor("routes").update(name + ".checkpoints."+checkpoint+".y", y);
								ConfigManager.editor("routes").update(name + ".checkpoints."+checkpoint+".z", z);
								ConfigManager.editor("routes").update(name + ".checkpoints."+checkpoint+".yaw", yaw);
								ConfigManager.editor("routes").update(name + ".checkpoints."+checkpoint+".pitch", pitch);
						
								player.sendMessage("§8[§aKlassenserver§8] §bDer Checkpoint "+checkpoint+" wurde hinzugefügt");
							}else {
								player.sendMessage("§8[§aKlassenserver§8] §cFür das Jump'n Run " + name + " wurde noch keine Startlocation gesetzt");
							}
						
					}else {
						player.sendMessage("§8[§aKlassenserver§8] §cDas Jump'n Run existiert nicht!");
					}
				}
			}else {			
				player.sendMessage("§8[§aKlassenserver§8] §cFalsche Syntax!");
			}
		} else {
			sender.sendMessage("§8[§aKlassenserver§8] §cDer Befehl kann nur von Spielern genutzt werden!");
		}
		
		return true;
	}

}