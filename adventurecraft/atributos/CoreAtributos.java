package me.soldado.adventurecraft.atributos;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.soldado.adventurecraft.Main;
import me.soldado.adventurecraft.core.Atributos;
import me.soldado.adventurecraft.core.Jogador;

public class CoreAtributos {

	public Main plugin;
	public CoreAtributos(Main plugin){
		this.plugin = plugin;
	}
	
	int pontospornivel = 3;
	
	public void resetarAtributos(Player p, boolean hard){
		Jogador jog = plugin.corejogador.getJogador(p.getName());
		Atributos atr = jog.getAtributos(); //Isso é uma cópia ou uma referência?
		if(!hard){
			int pontos = atr.getAgilidade() + atr.getDestreza() + atr.getForça()
			+ atr.getInteligencia() + atr.getVitalidade();
			atr.setRestante(pontos);
		}else{
			atr.setRestante(0);
		}
		atr.setAgilidade(0);
		atr.setDestreza(0);
		atr.setForça(0);
		atr.setInteligencia(0);
		atr.setVitalidade(0);
		jog.setAtributos(atr);
		p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
	}
	
	public void upar(Player p){

		Jogador j = plugin.corejogador.getJogador(p.getName());
		int atual = j.getAtributos().getRestante();
		j.getAtributos().setRestante(atual + pontospornivel);

		p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + ">>> " + ChatColor.DARK_AQUA + 
				"Você tem atualmente " + ChatColor.RESET + "" + ChatColor.UNDERLINE + j.getAtributos().getRestante() 
				+ ChatColor.DARK_AQUA + " pontos de atributos não distribuídos. Digite " + ChatColor.RESET +
				"/atributos " + ChatColor.DARK_AQUA + "para aplicá-los.");


	}
	
}
