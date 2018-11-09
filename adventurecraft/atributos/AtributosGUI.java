package me.soldado.adventurecraft.atributos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.soldado.adventurecraft.Main;
import me.soldado.adventurecraft.core.Classe;
import me.soldado.adventurecraft.core.Jogador;

public class AtributosGUI implements Listener {

	public Main plugin;
	public AtributosGUI(Main plugin){
		this.plugin = plugin;
	}

	public String nv0 = "§7✪✪✪";
	public String nv1 = "§5✪§7✪✪";
	public String nv2 = "§5✪✪§7✪";
	public String nv3 = "§5✪✪✪";
	int atrpack = 10;

	public static HashMap<String, Integer> addfor = new HashMap<String, Integer>();
	public static HashMap<String, Integer> addint = new HashMap<String, Integer>();
	public static HashMap<String, Integer> adddes = new HashMap<String, Integer>();
	public static HashMap<String, Integer> addvit = new HashMap<String, Integer>();
	public static HashMap<String, Integer> addagi = new HashMap<String, Integer>();
	public static HashMap<String, Integer> retpt = new HashMap<String, Integer>();

	@EventHandler
	public void clicarAtributos(InventoryClickEvent event){

		if(event.getInventory().getTitle().contains("§aAtributos de "+ event.getWhoClicked().getName())){

			Player p = (Player) event.getWhoClicked();
			Jogador j = plugin.corejogador.getJogador(p.getName());
			ItemStack clicked = event.getCurrentItem();
			int pts = j.getAtributos().getRestante();
			int ret = 0;
			if(retpt.containsKey(p.getName())){
				ret = retpt.get(p.getName());
			}

			if(clicked != null && clicked.getType() != Material.AIR){
				if(clicked.getType().equals(Material.NETHER_STAR)){
					if(event.getClick().equals(ClickType.LEFT) || event.getClick().equals(ClickType.SHIFT_LEFT)){
						boolean aux = false;
						int pt = 1;
						if(event.getClick().equals(ClickType.SHIFT_LEFT)){
							pt = atrpack;
							aux = true;
						}
						if(pts > ret + (pt-1)){

							atualizarHash(p, clicked.getItemMeta().getDisplayName(), true, aux); 
							retpt.put(p.getName(), ret + pt);
							atualizar(event.getInventory(), p, event.getSlot() - 9, true);
							atualizarResto(event.getInventory(), j);

						}else{
							p.sendMessage(ChatColor.RED + "Você não tem pontos de atributos restantes.");
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
						}
					}else if(event.getClick().equals(ClickType.RIGHT) || event.getClick().equals(ClickType.SHIFT_RIGHT)){

						boolean aux = false;
						int pt = 1;
						if(event.getClick().equals(ClickType.SHIFT_RIGHT)){
							pt = atrpack;
							aux = true;
						}

						if(ret - (pt-1) > 0 && checkNegativo(event.getSlot() - 9, event.getInventory(), p)){

							atualizarHash(p, clicked.getItemMeta().getDisplayName(), false, aux);
							retpt.put(p.getName(), ret - pt);
							atualizar(event.getInventory(), p, event.getSlot() - 9, false);
							atualizarResto(event.getInventory(), j);

						}
					}
				}else if(clicked.getType().equals(Material.INK_SACK)){
					if(clicked.getItemMeta().getDisplayName().contains("CONFIRMAR")){
						aplicarAtributos(j);
						p.sendMessage(ChatColor.GREEN 
								+ "Você distribuiu seus pontos de atributos com sucesso");
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1, 1);
						plugin.coreclasses.setarVidaMax(p);
						//me.soldado.adventurecraft.hms.Mana.setupMaxMana(p);
						//me.soldado.adventurecraft.classes.VelocidadeClasses.darVelocidade(p);

					}else{
						p.closeInventory();
						p.sendMessage(ChatColor.RED 
								+ "Você cancelou a distribuição de pontos de atributos.");
					}
				}
			}
			event.setCancelled(true);
		}
	}

	public boolean checkNegativo(Integer slot, Inventory inv, Player p){

		boolean aux = false;
		String nome = inv.getItem(slot).getItemMeta().getDisplayName();

		if(nome.contains("For") && addfor.containsKey(p.getName())){

			if(addfor.get(p.getName()) > 0) aux = true;

		}else if(nome.contains("Des") && adddes.containsKey(p.getName())){

			if(adddes.get(p.getName()) > 0) aux = true;

		}else if(nome.contains("Int") && addint.containsKey(p.getName())){

			if(addint.get(p.getName()) > 0) aux = true;

		}else if(nome.contains("Agi") && addagi.containsKey(p.getName())){

			if(addagi.get(p.getName()) > 0) aux = true;

		}else if(nome.contains("Vit") && addvit.containsKey(p.getName())){

			if(addvit.get(p.getName()) > 0) aux = true;

		}
		return aux;
	}

	public void atualizar(Inventory inv, Player p, Integer slot, Boolean aumentar){

		ItemStack item = inv.getItem(slot);
		Jogador j = plugin.corejogador.getJogador(p.getName());
		if(item.getType().equals(Material.EMPTY_MAP)){
			if(item.getItemMeta().getDisplayName().contains("For")){

				ItemStack slot3 = new ItemStack(Material.EMPTY_MAP);
				ItemMeta slot3m = slot3.getItemMeta();
				slot3m.setDisplayName(ChatColor.DARK_AQUA + "Força");
				List<String> lore3 = new ArrayList<String>();
				lore3.add(ChatColor.GRAY + "Aumenta o dano corpo a corpo,");
				lore3.add(ChatColor.GRAY + "a chance de bloquear ataques e");
				lore3.add(ChatColor.GRAY + "a resistencia a dano.");
				lore3.add(ChatColor.GREEN + "Pontos alocados: " + j.getAtributos().getForça());
				lore3.add(ChatColor.RED + "Pontos a serem alocados: " + addfor.get(j.getNome()));
				lore3.add(getEstrelasUtilidades(j, "força"));
				slot3m.setLore(lore3);
				slot3.setItemMeta(slot3m);
				inv.setItem(slot, slot3);

			}else if(item.getItemMeta().getDisplayName().contains("Des")){

				ItemStack slot4 = new ItemStack(Material.EMPTY_MAP);
				ItemMeta slot4m = slot4.getItemMeta();
				slot4m.setDisplayName(ChatColor.DARK_AQUA + "Destreza");
				List<String> lore4 = new ArrayList<String>();
				lore4.add(ChatColor.GRAY + "Aumenta a chance de aplicar golpes criticos");
				lore4.add(ChatColor.GRAY + "e o dano com arcos.");
				lore4.add(ChatColor.GREEN + "Pontos alocados: " + j.getAtributos().getDestreza());
				lore4.add(ChatColor.RED + "Pontos a serem alocados: " + adddes.get(j.getNome()));
				lore4.add(getEstrelasUtilidades(j, "destreza"));
				slot4m.setLore(lore4);
				slot4.setItemMeta(slot4m);
				inv.setItem(slot, slot4);

			}else if(item.getItemMeta().getDisplayName().contains("Int")){

				ItemStack slot6 = new ItemStack(Material.EMPTY_MAP);
				ItemMeta slot6m = slot6.getItemMeta();
				slot6m.setDisplayName(ChatColor.DARK_AQUA + "Inteligencia");
				List<String> lore6 = new ArrayList<String>();
				lore6.add(ChatColor.GRAY + "Aumenta os danos mágicos e");
				lore6.add(ChatColor.GRAY + "os pontos de mana.");
				lore6.add(ChatColor.GREEN + "Pontos alocados: " + j.getAtributos().getInteligencia());
				lore6.add(ChatColor.RED + "Pontos a serem alocados: " + addint.get(j.getNome()));
				lore6.add(getEstrelasUtilidades(j, "inteligencia"));
				slot6m.setLore(lore6);
				slot6.setItemMeta(slot6m);
				inv.setItem(slot, slot6);

			}else if(item.getItemMeta().getDisplayName().contains("Agi")){

				ItemStack slot7 = new ItemStack(Material.EMPTY_MAP);
				ItemMeta slot7m = slot7.getItemMeta();
				slot7m.setDisplayName(ChatColor.DARK_AQUA + "Agilidade");
				List<String> lore7 = new ArrayList<String>();
				lore7.add(ChatColor.GRAY + "Aumenta a velocidade de movimento e");
				lore7.add(ChatColor.GRAY + "a chance de esquiva.");
				lore7.add(ChatColor.GREEN + "Pontos alocados: " + j.getAtributos().getAgilidade());
				lore7.add(ChatColor.RED + "Pontos a serem alocados: " + addagi.get(j.getNome()));
				lore7.add(getEstrelasUtilidades(j, "agilidade"));
				slot7m.setLore(lore7);
				slot7.setItemMeta(slot7m);
				inv.setItem(slot, slot7);

			}else if(item.getItemMeta().getDisplayName().contains("Vit")){

				ItemStack slot5 = new ItemStack(Material.EMPTY_MAP);
				ItemMeta slot5m = slot5.getItemMeta();
				slot5m.setDisplayName(ChatColor.DARK_AQUA + "Vitalidade");
				List<String> lore5 = new ArrayList<String>();
				lore5.add(ChatColor.GRAY + "Aumenta os pontos de vida e");
				lore5.add(ChatColor.GRAY + "a regeneração de energia.");
				lore5.add(ChatColor.GREEN + "Pontos alocados: " + j.getAtributos().getVitalidade());
				lore5.add(ChatColor.RED + "Pontos a serem alocados: " + addvit.get(j.getNome()));
				lore5.add(getEstrelasUtilidades(j, "vitalidade"));
				slot5m.setLore(lore5);
				slot5.setItemMeta(slot5m);
				inv.setItem(slot, slot5);

			}
			p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
		}
	}

	public void atualizarHash(Player p, String nome, boolean aumentar, boolean shift){

		int pt = 1;
		if(shift) pt = atrpack;

		if(aumentar){
			if(nome.contains("FOR")){

				int atual = 0;
				if(addfor.containsKey(p.getName())){
					atual = addfor.get(p.getName());
				}
				addfor.put(p.getName(), atual + pt);

			}else if(nome.contains("DES")){

				int atual = 0;
				if(adddes.containsKey(p.getName())){
					atual = adddes.get(p.getName());
				}
				adddes.put(p.getName(), atual + pt);

			}else if(nome.contains("VIT")){

				int atual = 0;
				if(addvit.containsKey(p.getName())){
					atual = addvit.get(p.getName());
				}
				addvit.put(p.getName(), atual + pt);

			}else if(nome.contains("INT")){

				int atual = 0;
				if(addint.containsKey(p.getName())){
					atual = addint.get(p.getName());
				}
				addint.put(p.getName(), atual + pt);

			}else if(nome.contains("AGI")){

				int atual = 0;
				if(addagi.containsKey(p.getName())){
					atual = addagi.get(p.getName());
				}
				addagi.put(p.getName(), atual + pt);

			}

		}else{

			if(nome.contains("FOR")){

				int atual = 0;
				if(addfor.containsKey(p.getName())){
					atual = addfor.get(p.getName());
				}
				addfor.put(p.getName(), atual - pt);

			}else if(nome.contains("DES")){

				int atual = 0;
				if(adddes.containsKey(p.getName())){
					atual = adddes.get(p.getName());
				}
				adddes.put(p.getName(), atual - pt);

			}else if(nome.contains("VIT")){

				int atual = 0;
				if(addvit.containsKey(p.getName())){
					atual = addvit.get(p.getName());
				}
				addvit.put(p.getName(), atual - pt);

			}else if(nome.contains("INT")){

				int atual = 0;
				if(addint.containsKey(p.getName())){
					atual = addint.get(p.getName());
				}
				addint.put(p.getName(), atual - pt);

			}else if(nome.contains("AGI")){

				int atual = 0;
				if(addagi.containsKey(p.getName())){
					atual = addagi.get(p.getName());
				}
				addagi.put(p.getName(), atual - pt);
			}
		}
	}

	public void aplicarAtributos(Jogador j){

		if(addfor.containsKey(j.getNome())){
			int fatual = j.getAtributos().getForça();
			int fadd = addfor.get(j.getNome());
			j.getAtributos().setForça(fatual + fadd);
		}

		if(adddes.containsKey(j.getNome())){
			int datual = j.getAtributos().getDestreza();
			int dadd = adddes.get(j.getNome());
			j.getAtributos().setDestreza(datual + dadd);
		}

		if(addagi.containsKey(j.getNome())){
			int aatual = j.getAtributos().getAgilidade();
			int aadd = addagi.get(j.getNome());
			j.getAtributos().setAgilidade(aatual + aadd);
		}

		if(addvit.containsKey(j.getNome())){
			int vatual = j.getAtributos().getVitalidade();
			int vadd = addvit.get(j.getNome());
			j.getAtributos().setVitalidade(vatual + vadd);
		}

		if(addint.containsKey(j.getNome())){
			int iatual = j.getAtributos().getInteligencia();
			int iadd = addint.get(j.getNome());
			j.getAtributos().setInteligencia(iatual + iadd);
		}

		if(retpt.containsKey(j.getNome())){
			int patual = j.getAtributos().getRestante();
			int pret = retpt.get(j.getNome());
			j.getAtributos().setRestante(patual + pret);
		}

		resetarHashs(j.getNome());

	}

	public void abrirAtributos(Player p){

		Jogador j = plugin.corejogador.getJogador(p.getName());

		if(plugin.corejogador.getJogador(p.getName()).getClasse() != Classe.NORMAL){

			resetarHashs(p.getName());

			int pts = j.getAtributos().getRestante();
			int fo = j.getAtributos().getForça();
			int agi = j.getAtributos().getAgilidade();
			int vit = j.getAtributos().getVitalidade();
			int des = j.getAtributos().getDestreza();
			int in = j.getAtributos().getInteligencia();

			Inventory atributos = Bukkit.getServer().createInventory(null, 18, "§aAtributos de " + p.getName());

			ItemStack slot1 = new ItemStack(Material.PAPER);
			ItemMeta slot1m = slot1.getItemMeta();
			slot1m.setDisplayName(ChatColor.DARK_AQUA + "Pontos disponíveis:");
			List<String> lore1 = new ArrayList<String>();
			lore1.add(ChatColor.AQUA +""+ pts + " PONTOS");
			slot1m.setLore(lore1);
			slot1.setItemMeta(slot1m);
			atributos.setItem(0, slot1);

			ItemStack slot3 = new ItemStack(Material.EMPTY_MAP);
			ItemMeta slot3m = slot3.getItemMeta();
			slot3m.setDisplayName(ChatColor.DARK_AQUA + "Força");
			List<String> lore3 = new ArrayList<String>();
			lore3.add(ChatColor.GRAY + "Aumenta o dano corpo a corpo,");
			lore3.add(ChatColor.GRAY + "a chance de bloquear ataques e");
			lore3.add(ChatColor.GRAY + "a resistencia a dano.");
			lore3.add(ChatColor.GREEN + "Pontos alocados: " + fo);
			lore3.add(ChatColor.RED + "Pontos a serem alocados: 0");
			lore3.add(getEstrelasUtilidades(j, "força"));
			slot3m.setLore(lore3);
			slot3.setItemMeta(slot3m);
			atributos.setItem(2, slot3);

			ItemStack slot4 = new ItemStack(Material.EMPTY_MAP);
			ItemMeta slot4m = slot4.getItemMeta();
			slot4m.setDisplayName(ChatColor.DARK_AQUA + "Destreza");
			List<String> lore4 = new ArrayList<String>();
			lore4.add(ChatColor.GRAY + "Aumenta a chance de aplicar golpes criticos");
			lore4.add(ChatColor.GRAY + "e o dano com arcos.");
			lore4.add(ChatColor.GREEN + "Pontos alocados: " + des);
			lore4.add(ChatColor.RED + "Pontos a serem alocados: 0");
			lore4.add(getEstrelasUtilidades(j, "destreza"));
			slot4m.setLore(lore4);
			slot4.setItemMeta(slot4m);
			atributos.setItem(3, slot4);

			ItemStack slot5 = new ItemStack(Material.EMPTY_MAP);
			ItemMeta slot5m = slot5.getItemMeta();
			slot5m.setDisplayName(ChatColor.DARK_AQUA + "Vitalidade");
			List<String> lore5 = new ArrayList<String>();
			lore5.add(ChatColor.GRAY + "Aumenta os pontos de vida e");
			lore5.add(ChatColor.GRAY + "a regeneração de energia.");
			lore5.add(ChatColor.GREEN + "Pontos alocados: " + vit);
			lore5.add(ChatColor.RED + "Pontos a serem alocados: 0");
			lore5.add(getEstrelasUtilidades(j, "vitalidade"));
			slot5m.setLore(lore5);
			slot5.setItemMeta(slot5m);
			atributos.setItem(4, slot5);

			ItemStack slot6 = new ItemStack(Material.EMPTY_MAP);
			ItemMeta slot6m = slot6.getItemMeta();
			slot6m.setDisplayName(ChatColor.DARK_AQUA + "Inteligencia");
			List<String> lore6 = new ArrayList<String>();
			lore6.add(ChatColor.GRAY + "Aumenta os danos mágicos e");
			lore6.add(ChatColor.GRAY + "os pontos de mana.");
			lore6.add(ChatColor.GREEN + "Pontos alocados: " + in);
			lore6.add(ChatColor.RED + "Pontos a serem alocados: 0");
			lore6.add(getEstrelasUtilidades(j, "inteligencia"));
			slot6m.setLore(lore6);
			slot6.setItemMeta(slot6m);
			atributos.setItem(5, slot6);

			ItemStack slot7 = new ItemStack(Material.EMPTY_MAP);
			ItemMeta slot7m = slot7.getItemMeta();
			slot7m.setDisplayName(ChatColor.DARK_AQUA + "Agilidade");
			List<String> lore7 = new ArrayList<String>();
			lore7.add(ChatColor.GRAY + "Aumenta a velocidade de movimento e");
			lore7.add(ChatColor.GRAY + "a chance de esquiva.");
			lore7.add(ChatColor.GREEN + "Pontos alocados: " + agi);
			lore7.add(ChatColor.RED + "Pontos a serem alocados: 0");
			lore7.add(getEstrelasUtilidades(j, "agilidade"));
			slot7m.setLore(lore7);
			slot7.setItemMeta(slot7m);
			atributos.setItem(6, slot7);

			ItemStack slot9 = new ItemStack(Material.INK_SACK, 1, (byte) 10);
			ItemMeta slot9m = slot9.getItemMeta();
			slot9m.setDisplayName(ChatColor.GREEN + "CONFIRMAR");
			List<String> lore9 = new ArrayList<String>();
			lore9.add(ChatColor.GRAY + "Clique para confirmar a distribuição");
			lore9.add(ChatColor.GRAY + "de seus pontos.");
			slot9m.setLore(lore9);
			slot9.setItemMeta(slot9m);
			atributos.setItem(8, slot9);

			ItemStack slot12 = new ItemStack(Material.NETHER_STAR);
			ItemMeta slot12m = slot12.getItemMeta();
			slot12m.setDisplayName(ChatColor.AQUA + "Clique para adicionar/retirar um ponto em "
					+ ChatColor.DARK_AQUA + "FORÇA");
			List<String> lore12 = new ArrayList<String>();
			lore12.add(ChatColor.GRAY + "Botão esquerdo p/ adicionar.");
			lore12.add(ChatColor.GRAY + "Botão direito p/retirar.");
			lore12.add(ChatColor.YELLOW + "Segure " + ChatColor.BOLD + "SHIFT " + ChatColor.YELLOW
					+ "para adicionar/retirar " + ChatColor.UNDERLINE + atrpack + ChatColor.YELLOW
					+ " de uma vez.");
			slot12m.setLore(lore12);
			slot12.setItemMeta(slot12m);
			atributos.setItem(11, slot12);

			ItemStack slot13 = new ItemStack(Material.NETHER_STAR);
			ItemMeta slot13m = slot13.getItemMeta();
			slot13m.setDisplayName(ChatColor.AQUA + "Clique para adicionar/retirar um ponto em " 
					+ ChatColor.DARK_AQUA + "DESTREZA");
			List<String> lore13 = new ArrayList<String>();
			lore13.add(ChatColor.GRAY + "Botão esquerdo p/ adicionar.");
			lore13.add(ChatColor.GRAY + "Botão direito p/retirar.");
			lore13.add(ChatColor.YELLOW + "Segure " + ChatColor.BOLD + "SHIFT " + ChatColor.YELLOW
					+ "para adicionar/retirar " + ChatColor.UNDERLINE + atrpack + ChatColor.YELLOW
					+ " de uma vez.");
			slot13m.setLore(lore13);
			slot13.setItemMeta(slot13m);
			atributos.setItem(12, slot13);

			ItemStack slot14 = new ItemStack(Material.NETHER_STAR);
			ItemMeta slot14m = slot14.getItemMeta();
			slot14m.setDisplayName(ChatColor.AQUA + "Clique para adicionar/retirar um ponto em " 
					+ ChatColor.DARK_AQUA + "VITALIDADE");
			List<String> lore14 = new ArrayList<String>();
			lore14.add(ChatColor.GRAY + "Botão esquerdo p/ adicionar.");
			lore14.add(ChatColor.GRAY + "Botão direito p/retirar.");
			lore14.add(ChatColor.YELLOW + "Segure " + ChatColor.BOLD + "SHIFT " + ChatColor.YELLOW
					+ "para adicionar/retirar " + ChatColor.UNDERLINE + atrpack + ChatColor.YELLOW
					+ " de uma vez.");
			slot14m.setLore(lore14);
			slot14.setItemMeta(slot14m);
			atributos.setItem(13, slot14);

			ItemStack slot15 = new ItemStack(Material.NETHER_STAR);
			ItemMeta slot15m = slot15.getItemMeta();
			slot15m.setDisplayName(ChatColor.AQUA + "Clique para adicionar/retirar um ponto em " 
					+ ChatColor.DARK_AQUA + "INTELIGENCIA");
			List<String> lore15 = new ArrayList<String>();
			lore15.add(ChatColor.GRAY + "Botão esquerdo p/ adicionar.");
			lore15.add(ChatColor.GRAY + "Botão direito p/retirar.");
			lore15.add(ChatColor.YELLOW + "Segure " + ChatColor.BOLD + "SHIFT " + ChatColor.YELLOW
					+ "para adicionar/retirar " + ChatColor.UNDERLINE + atrpack + ChatColor.YELLOW
					+ " de uma vez.");
			slot15m.setLore(lore15);
			slot15.setItemMeta(slot15m);
			atributos.setItem(14, slot15);

			ItemStack slot16 = new ItemStack(Material.NETHER_STAR);
			ItemMeta slot16m = slot16.getItemMeta();
			slot16m.setDisplayName(ChatColor.AQUA + "Clique para adicionar/retirar um ponto em " 
					+ ChatColor.DARK_AQUA + "AGILIDADE");
			List<String> lore16 = new ArrayList<String>();
			lore16.add(ChatColor.GRAY + "Botão esquerdo p/ adicionar.");
			lore16.add(ChatColor.GRAY + "Botão direito p/retirar.");
			lore16.add(ChatColor.YELLOW + "Segure " + ChatColor.BOLD + "SHIFT " + ChatColor.YELLOW
					+ "para adicionar/retirar " + ChatColor.UNDERLINE + atrpack + ChatColor.YELLOW
					+ " de uma vez.");
			slot16m.setLore(lore16);
			slot16.setItemMeta(slot16m);
			atributos.setItem(15, slot16);

			ItemStack slot18 = new ItemStack(Material.INK_SACK, 1, (byte) 01);
			ItemMeta slot18m = slot18.getItemMeta();
			slot18m.setDisplayName(ChatColor.RED + "CANCELAR");
			List<String> lore17 = new ArrayList<String>();
			lore17.add(ChatColor.GRAY + "Clique para cancelar a distribuição");
			lore17.add(ChatColor.GRAY + "de seus pontos.");
			slot18m.setLore(lore17);
			slot18.setItemMeta(slot18m);
			atributos.setItem(17, slot18);

			p.openInventory(atributos);
		}
	}

	public void atualizarHash(String s, String nome, boolean aumentar, boolean shift){

		int pt = 1;
		if(shift) pt = atrpack;

		if(aumentar){

			if(nome.contains("FOR")){

				int atual = 0;
				if(addfor.containsKey(s)){
					atual = addfor.get(s);
				}
				addfor.put(s, atual + pt);

			}else if(nome.contains("DES")){

				int atual = 0;
				if(adddes.containsKey(s)){
					atual = adddes.get(s);
				}
				adddes.put(s, atual + pt);

			}else if(nome.contains("VIT")){

				int atual = 0;
				if(addvit.containsKey(s)){
					atual = addvit.get(s);
				}
				addvit.put(s, atual + pt);

			}else if(nome.contains("INT")){

				int atual = 0;
				if(addint.containsKey(s)){
					atual = addint.get(s);
				}
				addint.put(s, atual + pt);

			}else if(nome.contains("AGI")){

				int atual = 0;
				if(addagi.containsKey(s)){
					atual = addagi.get(s);
				}
				addagi.put(s, atual + pt);

			}
		}else if(nome.contains("FOR")){

			int atual = 0;
			if(addfor.containsKey(s)){
				atual = addfor.get(s);
			}
			addfor.put(s, atual - pt);

		}else if(nome.contains("DES")){

			int atual = 0;
			if(adddes.containsKey(s)){
				atual = adddes.get(s);
			}
			adddes.put(s, atual - pt);

		}else if(nome.contains("VIT")){

			int atual = 0;
			if(addvit.containsKey(s)){
				atual = addvit.get(s);
			}
			addvit.put(s, atual - pt);

		}else if(nome.contains("INT")){

			int atual = 0;
			if(addint.containsKey(s)){
				atual = addint.get(s);
			}
			addint.put(s, atual - pt);

		}else if(nome.contains("AGI")){

			int atual = 0;
			if(addagi.containsKey(s)){
				atual = addagi.get(s);
			}
			addagi.put(s, atual - pt);
		}


	}

	public void atualizarResto(Inventory inv, Jogador j){

		int restante = j.getAtributos().getRestante() - retpt.get(j.getNome());

		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta itemm = item.getItemMeta();
		itemm.setDisplayName(ChatColor.DARK_AQUA + "Pontos disponíveis:");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.AQUA +""+ restante + " PONTOS");
		itemm.setLore(lore);
		item.setItemMeta(itemm);
		inv.setItem(0, item);

	}

	public int getUtilidade(Jogador j, String attr){

		int utilidade = 0;
		Classe c = j.getClasse();

		if(c.equals(Classe.GUERREIRO)){
			if(attr.contains("for")){
				utilidade = 3;
			}else if(attr.contains("vit")){
				utilidade = 3;
			}else if(attr.contains("des")){
				utilidade = 1;
			}else if(attr.contains("int")){
				utilidade = 0;
			}else if(attr.contains("agi")){
				utilidade = 2;
			}
		}else if(c.equals(Classe.ARQUEIRO)){
			if(attr.contains("for")){
				utilidade = 2;
			}else if(attr.contains("vit")){
				utilidade = 2;
			}else if(attr.contains("des")){
				utilidade = 3;
			}else if(attr.contains("int")){
				utilidade = 0;
			}else if(attr.contains("agi")){
				utilidade = 2;
			}
		}else if(c.equals(Classe.ASSASSINO)){

			if(attr.contains("for")){
				utilidade = 3;
			}else if(attr.contains("vit")){
				utilidade = 2;
			}else if(attr.contains("des")){
				utilidade = 2;
			}else if(attr.contains("int")){
				utilidade = 0;
			}else if(attr.contains("agi")){
				utilidade = 3;
			}
		}else if(c.equals(Classe.MAGO)){
			if(attr.contains("for")){
				utilidade = 1;
			}else if(attr.contains("vit")){
				utilidade = 2;
			}else if(attr.contains("des")){
				utilidade = 0;
			}else if(attr.contains("int")){
				utilidade = 3;
			}else if(attr.contains("agi")){
				utilidade = 1;
			}
		}else if(c.equals(Classe.SACERDOTE)){
			if(attr.contains("for")){
				utilidade = 1;
			}else if(attr.contains("vit")){
				utilidade = 3;
			}else if(attr.contains("des")){
				utilidade = 0;
			}else if(attr.contains("int")){
				utilidade = 3;
			}else if(attr.contains("agi")){
				utilidade = 1;
			}
		}else if(c.equals(Classe.VIKING)){
			if(attr.contains("for")){
				utilidade = 3;
			}else if(attr.contains("vit")){
				utilidade = 3;
			}else if(attr.contains("des")){
				utilidade = 1;
			}else if(attr.contains("int")){
				utilidade = 0;
			}else if(attr.contains("agi")){
				utilidade = 2;
			}
		}else if(c.equals(Classe.NORMAL)){
			if(attr.contains("for")){
				utilidade = 0;
			}else if(attr.contains("vit")){
				utilidade = 0;
			}else if(attr.contains("des")){
				utilidade = 0;
			}else if(attr.contains("int")){
				utilidade = 0;
			}else if(attr.contains("agi")){
				utilidade = 0;
			}
		}
		return utilidade;
	}

	public void resetarHashs(String s){

		if(addfor.containsKey(s)){
			addfor.remove(s);
		}
		if(addint.containsKey(s)){
			addint.remove(s);
		}
		if(adddes.containsKey(s)){
			adddes.remove(s);
		}
		if(addagi.containsKey(s)){
			addagi.remove(s);
		}
		if(addvit.containsKey(s)){
			addvit.remove(s);
		}
		if(retpt.containsKey(s)){
			retpt.remove(s);
		}

	}

	public String getEstrelasUtilidades(Jogador j, String attr){

		String estrelas = "";
		int util = getUtilidade(j, attr);

		if(util == 0){
			estrelas = nv0;
		}else if(util == 1){
			estrelas = nv1;
		}else if(util == 2){
			estrelas = nv2;
		}else if(util == 3){
			estrelas = nv3;
		}

		return estrelas;

	}

}
