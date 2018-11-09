package me.soldado.adventurecraft.niveis;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.soldado.adventurecraft.Main;


public class BlockExpVanilla implements Listener {

	  public Main plugin;
	  
	  public BlockExpVanilla(Main plugin)
	  {
	    this.plugin = plugin;
	  }
	
	@EventHandler
	public void onBlockExp(BlockExpEvent e){
		e.setExpToDrop(0);
	}
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		e.setDroppedExp(0);
	}
	@EventHandler
	public void onFurnace(FurnaceExtractEvent e){
		e.setExpToDrop(0);
	}
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onEntitySpawn (ItemSpawnEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof ExperienceOrb) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e){
		if(e.getItem() instanceof ExperienceOrb){
			e.getItem().remove();
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onFish(PlayerFishEvent e){
		e.setExpToDrop(0);
	}
}
