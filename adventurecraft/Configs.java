package me.soldado.adventurecraft;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configs {
	
	public Main plugin;
	public Configs(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public File configFile;
	public FileConfiguration config;
	
	public void iniciarConfig(){
	    if (configFile == null) {
	    	configFile = new File(plugin.getDataFolder(), "config.yml");
	    }
	    if (!configFile.exists()) {
	      plugin.saveResource("config.yml", false);
	    }
	    config = YamlConfiguration.loadConfiguration(configFile);    
	}
	
}
