package me.marcel.klassenserver.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigEditor {

	private File file;
	private FileConfiguration config;
	
	public ConfigEditor(String configName) {
		this.file = new File("plugins/Klassenserver/" + configName + ".yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}
	
	public boolean create() {
		boolean success = true;
		
		if (!(this.getFile().exists())) {
			this.getConfig().options().copyDefaults(true);
			try {
				this.getConfig().save(this.getFile());
			} catch (IOException e) {
				success = false;
			}
		}
		
		return success;
	}
	
	public boolean delete() {

		if (this.getFile().exists()) {
			this.getFile().deleteOnExit();
		}
		
		return true;


	}
	
	public boolean update(String path, Object value) {
		boolean success = true;
		
		this.getConfig().set(path, value);
		try {
			this.getConfig().save(this.getFile());
		} catch (IOException e) {
			success = false;
		}
		
		return success;
	}

	public boolean remove(String path){
		boolean success = true;

		this.getConfig().set(path, "");
		try{
			this.getConfig().save(this.getFile());
		}catch(IOException e){
			success=false;
		}
		
		return success;
	}
	
	public String getString(String path) {
		return this.getConfig().getString(path);
	}
	
	public int getInt(String path) {
		return this.getConfig().getInt(path);
	}
	
	public double getDouble(String path) {
		return this.getConfig().getDouble(path);
	}
	
	public boolean getBoolean(String path) {
		return this.getConfig().getBoolean(path);
	}
	
	public List<String> getStringList(String path) {
		return this.getConfig().getStringList(path);
	}
	public Location getLocation(String path) {
		String world = ConfigManager.editor("routes").getString(path+".world");
		double x = ConfigManager.editor("routes").getDouble(path+".x");
		double y = ConfigManager.editor("routes").getDouble(path+".y");
		double z = ConfigManager.editor("routes").getDouble(path+".z");
		float pitch = (float) ConfigManager.editor("routes").getDouble(path+".pitch");
		float yaw = (float) ConfigManager.editor("routes").getDouble(path+".yaw");
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}
	public File getFile() {
		return this.file;
	}
	
	public FileConfiguration getConfig() {
		return this.config;
	}

	@SuppressWarnings("null")
	public List<Location> getCheckpoints(String pathList, String path) {
		List<Location> checkpoints = new ArrayList<Location>();
		for(String checkpoint : this.getConfig().getStringList(pathList)){
			checkpoints.add(ConfigManager.editor("routes").getLocation(path + "." + checkpoint));
		}
		return checkpoints;
	}
	
}
