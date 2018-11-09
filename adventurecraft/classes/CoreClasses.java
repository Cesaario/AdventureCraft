package me.soldado.adventurecraft.classes;

import org.bukkit.entity.Player;

import me.soldado.adventurecraft.Main;
import me.soldado.adventurecraft.core.Classe;
import me.soldado.adventurecraft.core.Jogador;
import me.soldado.adventurecraft.core.Trabalho;

public class CoreClasses {
	public Main plugin;

	public CoreClasses(Main plugin) {
		this.plugin = plugin;
	}

	public void setClasse(Jogador jog, Classe cls, Trabalho trab){
		jog.setClasse(cls);
		jog.setTrabalho(trab);
	}

	@SuppressWarnings("deprecation")
	public void setarVidaMax(Player p){

		Jogador j = plugin.corejogador.getJogador(p.getName());
		int nivel = j.getAtributos().getAgilidade();
		Classe c = j.getClasse();

		double m = 0.0;
		if(c.equals(Classe.GUERREIRO)){
			m = 7.0;
		}else if(c.equals(Classe.ARQUEIRO)){
			m = 5.0;
		}else if(c.equals(Classe.MAGO)){
			m = 5.0;
		}else if(c.equals(Classe.SACERDOTE)){
			m = 6.0;
		}else if(c.equals(Classe.ASSASSINO)){
			m = 5.0;
		}else if(c.equals(Classe.VIKING)){
			m = 7.0;
		}

		double hpp = 50.0;
		double newhp = nivel * m;
		double ve = 0;

//		if(me.soldado.adventurecraft.runas.RunaVida.getVida(p) > 0){
//			int vidaextra = me.soldado.adventurecraft.runas.RunaVida.getVida(p);
//			ve = vidaextra;
//		} FAZER AS RUNAS

		p.setMaxHealth(hpp + newhp + ve);
		p.setHealthScale(20.0);

	}

}
