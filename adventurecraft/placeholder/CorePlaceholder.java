package me.soldado.adventurecraft.placeholder;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.actionannouncer.ActionAPI;
import me.soldado.adventurecraft.Main;
import me.soldado.adventurecraft.core.Classe;
import me.soldado.adventurecraft.core.Jogador;

public class CorePlaceholder {

	public Main plugin;
	public CorePlaceholder(Main plugin)
	{
		this.plugin = plugin;
	}
	@SuppressWarnings("deprecation")
	public void update(Player p) {
		
		Jogador jog = plugin.corejogador.getJogador(p.getName());
		double vidamax = p.getMaxHealth();
		double vidaatual = p.getHealth();
		double ml = 2.5;
		int vm = (int) vidamax;
		int va = (int) vidaatual;
		int nivel = jog.getNivel();
		Double xp = jog.getXp();
		double xptotal = Math.pow(nivel, ml);
		
		Double pxpaux = (xp / xptotal) * 100;
		int pxp = pxpaux.intValue();
		
		switch(tipo(p.getName())){
			case 2: //Mana
				Double man = jog.getMana(); 
				int m = man.intValue();
				Double manam = (double) plugin.mana.getMaxMana(jog);
				int mm = manam.intValue();
				
				ActionAPI.sendPlayerAnnouncement(p, ChatColor.RED + "" + va + "/" + vm + " ♥" + "    " +
						ChatColor.GREEN + "EXP: " + ChatColor.WHITE + pxp + ChatColor.GREEN + "%    "
						+ ChatColor.AQUA + "" + m + "/" + mm + " ⋇");
				return;
			case 1: //Stamina
				Double st = jog.getStamina();
				
				if(st < 0) st = (double) 0;
				
				ActionAPI.sendPlayerAnnouncement(p, ChatColor.RED + "" + va + "/" + vm + " ♥" + "    " +
						ChatColor.GREEN + "EXP: " + ChatColor.WHITE + pxp + ChatColor.GREEN + "%    "
						+ ChatColor.YELLOW + "" + st.intValue() + "/100 ϟ");
			case 0: //Sem classe
				ActionAPI.sendPlayerAnnouncement(p, ChatColor.YELLOW + "Você está sem classe!");
		}
	}

	int tipo(String p){
		Jogador jog = plugin.corejogador.getJogador(p);
		if(jog.getClasse().equals(Classe.ARQUEIRO) || jog.getClasse().equals(Classe.GUERREIRO) || 
				jog.getClasse().equals(Classe.ASSASSINO) || jog.getClasse().equals(Classe.VIKING)) return 1;
		if(jog.getClasse().equals(Classe.MAGO) || jog.getClasse().equals(Classe.SACERDOTE)) return 2;
		return 0;
	}
	
}
