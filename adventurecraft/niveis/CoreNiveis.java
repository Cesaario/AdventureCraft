package me.soldado.adventurecraft.niveis;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import me.soldado.adventurecraft.Main;
import me.soldado.adventurecraft.core.Jogador;

public class CoreNiveis implements Listener {

	public Main plugin;
	public CoreNiveis(Main plugin)
	{
		this.plugin = plugin;
	}
	
	double m = 2.5;
	int nivelmax = 100;

	public File niveisfile;
	public FileConfiguration niveis;
	
	public void addXp(String p, double exp, boolean bonus){
		
		Jogador jog = plugin.corejogador.getJogador(p);
		double xpatual = jog.getXp();
		int nivelatual = jog.getNivel();
		double xplimiar = Math.pow(nivelatual, m);
		double xpfinal = xpatual + exp;
		Player pl = Bukkit.getServer().getPlayer(p);
		if(nivelatual >= nivelmax) return;
		
		int in = (int) exp;
		pl.sendMessage(ChatColor.GREEN + "    +" + in + ChatColor.BOLD + " EXP");
		
		if(xpfinal >= xplimiar){
			
			double restoxp = xpfinal - xplimiar;
			int novonivel = nivelatual + 1;
			
			pl.sendMessage(ChatColor.YELLOW +""+ ChatColor.BOLD + "");
			pl.sendMessage(ChatColor.YELLOW +""+ ChatColor.BOLD + "     Parabéns!!");
			pl.sendMessage(ChatColor.YELLOW + "  Você está agora no nível " + novonivel);
			pl.sendMessage(ChatColor.YELLOW +""+ ChatColor.BOLD + "");
			
			jog.setNivel(novonivel);
			jog.setXp(0);
			addXp(p, restoxp, false);
			setBarraXP(pl, restoxp, novonivel);
			plugin.coreatributos.upar(pl);

			Firework fw = (Firework)pl.getWorld().spawn(pl.getLocation(), 
					Firework.class);
			FireworkMeta data = fw.getFireworkMeta();
			data.addEffects(new FireworkEffect[] {FireworkEffect.builder()
					.withColor(Color.YELLOW).withFade(Color.SILVER).with(FireworkEffect.Type.BALL_LARGE)
					.build() });
			data.setPower(1);
			fw.setFireworkMeta(data);
			
			pl.getWorld().playSound(pl.getLocation(), Sound.LEVEL_UP, 10, 1);
			for(int i = 0; i < 3; i++){
				//ParticleEffects.VILLAGER_HAPPY.display(2F, 2F, 2F, 2F, 15, pl.getLocation(), 10);
			}
			
		}else{
			jog.setXp(xpfinal);
			setBarraXP(pl, xpfinal, nivelatual);
		}
		
		
	}
	
	public void setBarraXP(Player p, double xp, int nivel){
		
		double max = Math.pow(nivel, m);
		p.setLevel(nivel);
		p.setExp((float) (xp / max));
		
	}
	
	public void addNivel(String p, int niv){
		Jogador jog = plugin.corejogador.getJogador(p);
		int novonivel = jog.getNivel() + niv;
		jog.setNivel(novonivel);
		jog.setXp(0);
	}
	
}
