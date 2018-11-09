package me.soldado.adventurecraft.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.soldado.adventurecraft.Main;

public class CoreDatabase {

	public Main plugin;
	public CoreDatabase(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public Connection conexao;
	
	public void fecharConexao(){
		try {
			if(conexao != null || !conexao.isClosed()){
				conexao.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void abrirConexao(){
		  
		String DB_URL = "jdbc:mysql://"+plugin.cfg.config.getString("host")+":"
		+plugin.cfg.config.getString("porta")+"/"+plugin.cfg.config.getString("nome");

		String USER = plugin.cfg.config.getString("usuario");
		String PASS = plugin.cfg.config.getString("senha");

		try{
			plugin.getLogger().info("Tentando conexao: " + DB_URL);
			conexao = DriverManager.getConnection(DB_URL,USER,PASS);
		}catch(Exception e){
			plugin.getLogger().info("Falha ao abrir a conexao");
			e.printStackTrace();
		}
	}
	
}
