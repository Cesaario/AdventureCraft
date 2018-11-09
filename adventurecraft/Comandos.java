package me.soldado.adventurecraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.soldado.adventurecraft.core.Classe;
import me.soldado.adventurecraft.core.Jogador;
import me.soldado.adventurecraft.core.Povo;
import me.soldado.adventurecraft.core.Trabalho;

public class Comandos implements CommandExecutor {

	public Main plugin;
	public Comandos(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("advc")){
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("addnivel")){
					if(args.length == 3){
						if(plugin.corejogador.jogadorOnline(args[1]))
							plugin.coreniveis.addNivel(args[1], Integer.parseInt(args[2]));
						else sender.sendMessage("Jogador inexistente");
					}else sender.sendMessage("Sintaxe incorreta!");
				}else if(args[0].equalsIgnoreCase("addxp")){
					if(args.length == 3){
						if(plugin.corejogador.jogadorOnline(args[1]))
							plugin.coreniveis.addXp(args[1], Double.parseDouble(args[2]), false);
						else sender.sendMessage("Jogador inexistente");
					}else sender.sendMessage("Sintaxe incorreta!");
				}
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("status")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				Jogador jog = plugin.corejogador.getJogador(p.getName());
				String nome = jog.getNome();
				Classe cls = jog.getClasse();
				Trabalho trab = jog.getTrabalho();
				Povo povo = jog.getPovo();
				int nivel = jog.getNivel();
				double xp = jog.getXp();
				double xpt = Math.pow(xp, 2.5);
				double xpp = (xp / xpt) * 100;
				p.sendMessage(ChatColor.GRAY + "=-=-=-=-=-=-= " + ChatColor.RED + "Status " + ChatColor.GRAY + "=-=-=-=-=-=-=");
				p.sendMessage(ChatColor.RED + "Nick: " + ChatColor.GRAY + nome);
				p.sendMessage(ChatColor.RED + "Classe: " + ChatColor.GRAY + cls.toString());
				p.sendMessage(ChatColor.RED + "Trabalho: " + ChatColor.GRAY + trab.toString());
				p.sendMessage(ChatColor.RED + "Nivel: " + ChatColor.GRAY + nivel);
				p.sendMessage(ChatColor.RED + "Povo: " + ChatColor.GRAY + povo.toString());
				p.sendMessage(ChatColor.RED + "XP: " + ChatColor.GRAY + (int)xp + "/" + (int)xpt + " (" + (int)xpp + "%)");
			}else sender.sendMessage("Esse comando só pode ser executado por jogadores.");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("atributos")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				plugin.atributosgui.abrirAtributos(p);
			}
			return true;
		}
		return false;
	}
	
	public void iniciarComandos(){
		plugin.getCommand("advc").setExecutor(this);
		plugin.getCommand("status").setExecutor(this);
		plugin.getCommand("atributos").setExecutor(this);
	}

}
