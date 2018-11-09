package me.soldado.adventurecraft.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

public class Monstro implements Listener {

	Entity entidade;
	String nome;
	int nivel;
	double dano;
	double vida;
	Main plugin;
	
	public Monstro(Entity entity)
    {
        this.entidade = entity;
        entity.setMetadata(dano, new FixedMetadataValue(plugin, 1));
    }

	

}
