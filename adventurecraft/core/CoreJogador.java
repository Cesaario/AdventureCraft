package me.soldado.adventurecraft.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.soldado.adventurecraft.Main;

public class CoreJogador implements Listener {

	public Main plugin;

	public CoreJogador(Main plugin) {
		this.plugin = plugin;
	}

	public HashMap<String, Jogador> jogadores = new HashMap<String, Jogador>();

	@EventHandler
	public void login(PlayerLoginEvent event) {
		Player p = event.getPlayer();
		if (primeiroLogin(p.getName())) {
			plugin.coredb.abrirConexao();
			jogadores.put(p.getName(), new Jogador(p.getName(), Classe.NORMAL, Trabalho.NORMAL, Povo.NORMAL, 100.0, 0,
					new Atributos(0, 0, 0, 0, 0, 0), 1, 0));

			try {
				PreparedStatement sql = plugin.coredb.conexao
						.prepareStatement("INSERT INTO `jogador` values(?, ?, ?, ?, 0, 1, 0, 0, 0, 0, 0, 0);");
				sql.setString(1, p.getName());
				sql.setString(2, Classe.NORMAL.name());
				sql.setString(3, Trabalho.NORMAL.name());
				sql.setString(4, Povo.NORMAL.name());
				sql.execute();
				sql.close();
				plugin.getLogger().info("Dados de " + p.getName() + " inseridos com sucesso!");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				plugin.coredb.fecharConexao();
			}

		} else {
			plugin.coredb.abrirConexao();
			String nome = p.getName();
			Classe classe = null;
			Trabalho trabalho = null;
			Povo povo = null;
			Atributos atributos = new Atributos(0, 0, 0, 0, 0, 0);
			int nivel = 0;
			double xp = 0;

			try {

				PreparedStatement sql = plugin.coredb.conexao
						.prepareStatement("SELECT Classe FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				ResultSet result = sql.executeQuery();
				result.next();
				classe = Classe.valueOf(result.getString("Classe"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Trabalho FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				trabalho = Trabalho.valueOf(result.getString("Trabalho"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Povo FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				povo = Povo.valueOf(result.getString("Povo"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Agilidade FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				atributos.setAgilidade(result.getInt("Agilidade"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Força FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				atributos.setAgilidade(result.getInt("Força"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Destreza FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				atributos.setAgilidade(result.getInt("Destreza"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Inteligencia FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				atributos.setAgilidade(result.getInt("Inteligencia"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Vitalidade FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				atributos.setAgilidade(result.getInt("Vitalidade"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT AtributosLivres FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				atributos.setRestante(result.getInt("AtributosLivres"));

				sql = plugin.coredb.conexao.prepareStatement("SELECT Nivel FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				nivel = result.getInt("Nivel");

				sql = plugin.coredb.conexao.prepareStatement("SELECT Exp FROM `jogador` WHERE Nome=?;");
				sql.setString(1, nome);
				result = sql.executeQuery();
				result.next();
				xp = result.getDouble("Exp");

				jogadores.put(p.getName(), new Jogador(nome, classe, trabalho, povo, 100, 0, atributos, nivel, xp));

				sql.close();
				result.close();

				plugin.getLogger().info("Dados de " + p.getName() + " lidos com sucesso!");

			} catch (Exception e) {
				plugin.getLogger().info("ERRO AO CARREGAR INFORMAÇõES DO BANCO DE DADOS!!!!! (" + nome + ")");
				e.printStackTrace();
			} finally {
				plugin.coredb.fecharConexao();
			}
		}
	}

	@EventHandler
	public void disconnect(PlayerQuitEvent event){
		desconectar(event.getPlayer());
	}
	
	public void servidorParando(){
		for(Player p : Bukkit.getServer().getOnlinePlayers()) desconectar(p);
	}
	
	public void desconectar(Player p){
		
		Jogador jog = jogadores.get(p.getName());
		String nome = jog.getNome();
		Classe classe = jog.getClasse();
		Trabalho trabalho = jog.getTrabalho();
		Povo povo = jog.getPovo();
		int nivel = jog.getNivel();
		double xp = jog.getXp();
		int força = jog.getAtributos().getForça();
		int agilidade = jog.getAtributos().getAgilidade();
		int destreza = jog.getAtributos().getDestreza();
		int inteligencia = jog.getAtributos().getInteligencia();
		int vitalidade = jog.getAtributos().getVitalidade();
		int restante = jog.getAtributos().getRestante();
		
		plugin.coredb.abrirConexao();
		try{
			PreparedStatement sql = plugin.coredb.conexao.prepareStatement("UPDATE `jogador` SET Classe=?, "
					+ "Trabalho=?, Povo=?, Nivel=?, Exp=?, Força=?, Agilidade=?, Destreza=?, Vitalidade=?, Inteligencia=?, "
					+ "AtributosLivres=? WHERE Nome=?;");
			sql.setString(1, classe.name());
			sql.setString(2, trabalho.name());
			sql.setString(3, povo.name());
			sql.setInt(4, nivel);
			sql.setDouble(5, xp);
			sql.setInt(6, força);
			sql.setInt(7, agilidade);
			sql.setInt(8, destreza);
			sql.setInt(9, vitalidade);
			sql.setInt(10, inteligencia);
			sql.setInt(11, restante);
			sql.setString(12, nome);
			sql.executeUpdate();
			sql.close();
			plugin.getLogger().info("Dados do jogador salvos com sucesso!!! ("+nome+")");
		}catch(Exception e){
			plugin.getLogger().info("Erro ao salvar dados do jogador!!! ("+nome+")");
			e.printStackTrace();
		}finally{
			plugin.coredb.fecharConexao();
		}
	}

	public synchronized boolean primeiroLogin(String s) {
		plugin.coredb.abrirConexao();
		try {
			PreparedStatement sql = plugin.coredb.conexao.prepareStatement("SELECT * FROM `jogador` WHERE Nome=?;");
			sql.setString(1, s);
			ResultSet resultado = sql.executeQuery();
			boolean aux = resultado.next();
			sql.close();
			resultado.close();
			return !aux;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			plugin.coredb.fecharConexao();
		}
	}

	public Jogador getJogador(String s){
		if(jogadores.get(s) != null) return jogadores.get(s);
		else plugin.getLogger().info("JOGADOR " + s + "NÃO ENCONTRADO NA ARRAY!!! ERRO #1");
		return null;
	}
	
	public boolean jogadorOnline(String s){
		return jogadores.containsKey(s);
	}
	
}
