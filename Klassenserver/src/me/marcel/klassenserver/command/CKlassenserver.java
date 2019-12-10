package me.marcel.klassenserver.command;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.marcel.klassenserver.Runing.PlayerPlayingManager;
import me.marcel.klassenserver.config.ConfigManager;
import me.marcel.klassenserver.route.Route;
import me.marcel.klassenserver.route.RouteManager;

public class CKlassenserver implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			

			 if (args.length == 1) {

	
				
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
				else if (args[0].equalsIgnoreCase("getStartSign")) {
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
						Route route = new Route(name, null, new ArrayList<Location>());
						route.managerAdd();
						player.sendMessage("§8[§aKlassenserver§8] §bDas Jump'n Run wurde erstellt!");
						player.sendMessage("§8[§aKlassenserver§8] §bBitte Start location festlegen: /ks start " + name);
						

					} else {
						player.sendMessage("§8[§aKlassenserver§8] §cExistiert bereits!");
					}
				}else if(args[0].equalsIgnoreCase("checkpoint")) {
					player.sendMessage("§8[§aKlassenserver§8] §cFehlender Checkpoint Identifier!");
				}
				// /ks getStartSign <name>
				else if(args[0].equalsIgnoreCase("getStartSign")) {
					String name = args[1];
					if (RouteManager.exists(name)) {
						ItemStack signItem = new ItemStack(Material.OAK_SIGN, 1);
						ItemMeta meta = signItem.getItemMeta();
						meta.setDisplayName("Starter Schild: " + name );
						meta.setUnbreakable(true);
						List<String> lore = new ArrayList<String>();
						lore.add("§b[§5Klassenserver§b]");
						lore.add("Klicke hiermit auf ein Schild");
						lore.add("und es wird zum Start Schild für das Jump'n Run:");
						lore.add(name);
						meta.setLore(lore);
						signItem.setItemMeta(meta);
						player.getInventory().addItem(signItem);
						player.updateInventory();
						player.sendMessage("§8[§aKlassenserver§8] §bRechts-klicke mit dem Schild welches du gerade bekommen hast auf ein Schild, welches zum start Schild werden soll !");
					} else {

					}
				}
				// //ks start <name>
				else if (args[0].equalsIgnoreCase("start")) {
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

						RouteManager.update(new Route(name, location, RouteManager.getRouteByName(name).getCheckpoints()));
						
						player.sendMessage("§8[§aKlassenserver§8] §bDie Startlocation wurde gesetzt!");
						player.sendMessage("§8[§aKlassenserver§8] §bDie Checkpoints können jetzt gesetzt werden mit /ks checkpoint "+name+" <identfier>");
					} else {
						player.sendMessage("§8[§aKlassenserver§8] §cDas Jump'n Run existiert nicht!");
					
					}
				}else if(args[0].equalsIgnoreCase("delete")){
					String name = args[1];
					if (RouteManager.exists(name)) {
						if(PlayerPlayingManager.anyInRun(name)){
							player.sendMessage("§8[§aKlassenserver§8] §cDas Jump'n Run wird noch benutzt!");
						}else{
							RouteManager.remove(name);
							ConfigManager.editor("routes").remove(name);
							player.sendMessage("§8[§aKlassenserver§8] §b Das Jump'n Run " + name + " wurde gelöscht");
						}
					} else {
						player.sendMessage("§8[§aKlassenserver§8] §cDas Jump'n Run existiert nicht!");
					
					}
				}
						
			

			}else if(args.length==3) {
				if(args[0].equalsIgnoreCase("checkpoint")) {
					String name = args[1];
					if (RouteManager.exists(name)) {
						if(!(RouteManager.getRouteByName(name).getStart() == null)) {
								String checkpoint = args[2];
								Location location = player.getLocation();
						
								List<String> checkpoints = ConfigManager.editor("routes").getStringList(name + ".checkpoints");
								checkpoints.add(checkpoint);
								ConfigManager.editor("routes").update(name + ".checkpoints", checkpoints);

								String world = location.getWorld().getName();
								double x = location.getX();
								double y = location.getY();
								double z = location.getZ();
								float yaw = location.getYaw();
								float pitch = location.getPitch();
						
								ConfigManager.editor("routes").update(name + ".checkpoint."+checkpoint+".world", world);
								ConfigManager.editor("routes").update(name + ".checkpoint."+checkpoint+".x", x);
								ConfigManager.editor("routes").update(name + ".checkpoint."+checkpoint+".y", y);
								ConfigManager.editor("routes").update(name + ".checkpoint."+checkpoint+".z", z);
								ConfigManager.editor("routes").update(name + ".checkpoint."+checkpoint+".yaw", yaw);
								ConfigManager.editor("routes").update(name + ".checkpoint."+checkpoint+".pitch", pitch);
								
								List<Location> checkpointer = RouteManager.getRouteByName(name).getCheckpoints();
								checkpointer.add(location);
								
								RouteManager.update(new Route(name, RouteManager.getRouteByName(name).getStart(), checkpointer));
								
								player.sendMessage("§8[§aKlassenserver§8] §bDer Checkpoint "+checkpoint+" wurde hinzugefügt");
							}else {
								player.sendMessage("§8[§aKlassenserver§8] §cFür das Jump'n Run " + name + " wurde noch keine Startlocation gesetzt");
							}
						
					}else {
						player.sendMessage("§8[§aKlassenserver§8] §cDas Jump'n Run existiert nicht!");
					}
				}
			}else {						
				player.sendMessage("§8[§aKlassenserver§8] §6/ks create <name>");
				player.sendMessage("§8[§aKlassenserver§8] §6/ks delete <name>");
				player.sendMessage("§8[§aKlassenserver§8] §6/ks start <name>");
				player.sendMessage("§8[§aKlassenserver§8] §6/ks checkpoint <name> <identifier>");
				player.sendMessage("§8[§aKlassenserver§8] §6/ks getStartSign <name>");	

			}
		} else {
			sender.sendMessage("§8[§aKlassenserver§8] §cDer Befehl kann nur von Spielern genutzt werden!");
		}
		
		return true;
	}

}
