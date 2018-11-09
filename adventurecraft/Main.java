package me.soldado.adventurecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.soldado.adventurecraft.atributos.AtributosGUI;
import me.soldado.adventurecraft.atributos.CoreAtributos;
import me.soldado.adventurecraft.classes.CoreClasses;
import me.soldado.adventurecraft.classes.HabilidadesGuerreiro;
import me.soldado.adventurecraft.classes.NPCClasses;
import me.soldado.adventurecraft.core.CoreJogador;
import me.soldado.adventurecraft.database.CoreDatabase;
import me.soldado.adventurecraft.hms.Mana;
import me.soldado.adventurecraft.niveis.BlockExpVanilla;
import me.soldado.adventurecraft.niveis.CoreNiveis;
import me.soldado.adventurecraft.placeholder.CorePlaceholder;
import me.soldado.adventurecraft.terrenos.CoreTerrenos;

public class Main extends JavaPlugin {

	public CoreNiveis coreniveis;
	public BlockExpVanilla blockexpvanilla;
	public Comandos cmd;
	public CoreDatabase coredb;
	public Configs cfg;
	public CoreJogador corejogador;
	public CorePlaceholder coreplaceholder;
	public NPCClasses npcclasses;
	public CoreClasses coreclasses;
	public CoreAtributos coreatributos;
	public AtributosGUI atributosgui;
	public HabilidadesGuerreiro habguerreiro;
	public Mana mana;
	public CoreTerrenos terrenos;
	
	public void onEnable(){
		coreniveis = new CoreNiveis(this);
		cmd = new Comandos(this);
		blockexpvanilla = new BlockExpVanilla(this);
		coredb = new CoreDatabase(this);
		cfg = new Configs(this);
		corejogador = new CoreJogador(this);
		coreplaceholder = new CorePlaceholder(this);
		npcclasses = new NPCClasses(this);
		coreclasses = new CoreClasses(this);
		coreatributos = new CoreAtributos(this);
		atributosgui = new AtributosGUI(this);
		habguerreiro = new HabilidadesGuerreiro(this);
		mana = new Mana(this);
		terrenos = new CoreTerrenos(this);
		
		cmd.iniciarComandos();
		cfg.iniciarConfig();
		
		Bukkit.getServer().getPluginManager().registerEvents(corejogador, this);
		Bukkit.getServer().getPluginManager().registerEvents(blockexpvanilla, this);
		Bukkit.getServer().getPluginManager().registerEvents(npcclasses, this);
		Bukkit.getServer().getPluginManager().registerEvents(atributosgui, this);
	}
	
	public void onDisable(){
		corejogador.servidorParando();
	}
	
	public void clock(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
	 
				for(Player p : Bukkit.getServer().getOnlinePlayers()){
					coreplaceholder.update(p);
				}
	
			}
	    }, 2L, 2L);
	}

}
