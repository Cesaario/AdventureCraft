package me.soldado.adventurecraft.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.soldado.adventurecraft.Main;
import me.soldado.adventurecraft.core.Atributos;
import me.soldado.adventurecraft.core.Classe;
import me.soldado.adventurecraft.core.Jogador;
import me.soldado.adventurecraft.core.Trabalho;


public class NPCClasses implements Listener {

	public Main plugin;

	public NPCClasses(Main plugin) {
		this.plugin = plugin;
	}
	
	ArrayList<String> esq = new ArrayList<String>();
	
	@EventHandler
	public void npcClick(PlayerInteractEntityEvent event) {
		
		//if(!event.getHand().equals(EquipmentSlot.HAND)) return;
		if(event.getRightClicked() instanceof HumanEntity) {
			HumanEntity npc = (HumanEntity) event.getRightClicked();
			Player p = event.getPlayer();
			if(npc.getCustomName().contains("Mestre de Classe")) {
				mestreClasses(p);
			}else if(npc.getCustomName().contains("Esquecimento")) {
				esq.add(p.getName());
				p.sendMessage(ChatColor.GRAY + "Mestre do esquecimento: " + ChatColor.WHITE + "Deseja esquecer"
						+ " tudo que aprendeu e conquistou até então?");
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Ao resetar seu personagem, perderá toda a "
						+ "experiência, níveis e atributos ganhos. Seu inventário também será resetado, portanto "
						+ "guarde seus itens no banco antes de prosseguir.");
				p.sendMessage(ChatColor.GRAY + "Digite " + ChatColor.GREEN + "sim " + ChatColor.GRAY + "para resetar e" +
						ChatColor.RED + " cancelar " + ChatColor.GRAY + "para cancelar o procedimento.");
			}
		}
	}
	
	@EventHandler
	public void chatesquecer(AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if(esq.contains(p.getName())){
			if(event.getMessage().toLowerCase().contains("cancelar")){
				p.sendMessage(ChatColor.GRAY + "Mestre do esquecimento: " + ChatColor.WHITE +"Volte se mudar de ideia.");
				esq.remove(p.getName());
			}else if(event.getMessage().toLowerCase().contains("sim")){
				esquecer(p);
				esq.remove(p.getName());
			}else{
				p.sendMessage(ChatColor.GRAY + "Mestre do esquecimento: " + ChatColor.WHITE +"Volte se mudar de ideia.");
				esq.remove(p.getName());
			}//A mensagem de cancelamento acontecerá se o jogador escrever cancelar ou nenhuma das duas coisas.
			event.setCancelled(true);
		}//Foi colocado nessa ordem para deixar a prioridade como: cancelar > sim > qualquer coisa
	}
	
	@SuppressWarnings("deprecation")
	public void esquecer(Player p){
		p.sendMessage(ChatColor.GRAY + "Mestre do esquecimento: " + ChatColor.WHITE + "Bons sonhos...");
		p.sendMessage(ChatColor.GREEN + "Seu personagem foi resetado com sucesso!");
		p.getInventory().clear();
		//p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 2));
		//p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 2)); 
		p.playSound(p.getLocation(), Sound.AMBIENCE_CAVE, 1, 1);
		p.setExp(0);
		p.setLevel(0);
		Jogador jog = plugin.corejogador.getJogador(p.getName());
		Atributos atr = jog.getAtributos();
		atr.setAgilidade(0);
		atr.setDestreza(0);
		atr.setForça(0);
		atr.setInteligencia(0);
		atr.setRestante(0);
		atr.setVitalidade(0);
		jog.setAtributos(atr);
		jog.setClasse(Classe.NORMAL);
		jog.setTrabalho(Trabalho.NORMAL);
		jog.setXp(0);
		jog.setNivel(1);
		jog.setMana(50); //É a mana base né?
		p.setMaxHealth(50); //É a vida base né?
		p.setHealthScale(20);
		p.sendMessage("teleport");
		//p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
	}

	public void mestreClasses(Player p){

		Jogador jog = plugin.corejogador.jogadores.get(p.getName());

		if(jog.getClasse().equals(Classe.NORMAL) && jog.getTrabalho().equals(Trabalho.NORMAL)){

			Inventory menu = Bukkit.getServer().createInventory(null, 54, "Menu de Classes");

			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GREEN + "Classe exclusiva para Nobres");

			ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta em = espada.getItemMeta();
			em.setDisplayName(ChatColor.DARK_AQUA + "Clique para selecionar a classe Guerreiro");
			espada.setItemMeta(em);

			ItemStack arco = new ItemStack(Material.BOW);
			ItemMeta am = arco.getItemMeta();
			am.setDisplayName(ChatColor.DARK_AQUA + "Clique para selecionar a classe Arqueiro");
			arco.setItemMeta(am);

			ItemStack livro = new ItemStack(Material.BOOK);
			ItemMeta lm = livro.getItemMeta();
			lm.setDisplayName(ChatColor.DARK_AQUA + "Clique para selecionar a classe Mago");
			livro.setItemMeta(lm);

			ItemStack potion = new ItemStack(Material.POTION,1, (byte)05);
			ItemMeta pm = potion.getItemMeta();
			pm.setDisplayName(ChatColor.DARK_AQUA + "Clique para selecionar a classe Sacerdote");
			potion.setItemMeta(pm);

			ItemStack flint = new ItemStack(Material.FLINT);
			ItemMeta fm = flint.getItemMeta();
			fm.setDisplayName(ChatColor.DARK_AQUA + "Clique para selecionar a classe Assassino");
			fm.setLore(lore);
			flint.setItemMeta(fm);

			ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
			ItemMeta axm = axe.getItemMeta();
			axm.setDisplayName(ChatColor.DARK_AQUA + "Clique para selecionar a classe Viking");
			axe.setItemMeta(axm);

			ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
			ItemMeta pim = pick.getItemMeta();
			pim.setDisplayName(ChatColor.GOLD + "Clique para selecionar a subclasse Minerador");
			pick.setItemMeta(pim);

			ItemStack log = new ItemStack(Material.LOG);
			ItemMeta logm = log.getItemMeta();
			logm.setDisplayName(ChatColor.GOLD + "Clique para selecionar a subclasse Lenhador");
			log.setItemMeta(logm);

			ItemStack ss = new ItemStack(Material.STONE_SWORD);
			ItemMeta ssm = ss.getItemMeta();
			ssm.setDisplayName(ChatColor.GOLD + "Clique para selecionar a subclasse Caçador");
			ss.setItemMeta(ssm);

			ItemStack bigorna = new ItemStack(Material.ANVIL);
			ItemMeta bm = bigorna.getItemMeta();
			bm.setDisplayName(ChatColor.GOLD + "Clique para selecionar a subclasse Ferreiro");
			bigorna.setItemMeta(bm);

			ItemStack stand = new ItemStack(Material.WHEAT);
			ItemMeta sm = stand.getItemMeta();
			sm.setDisplayName(ChatColor.GOLD + "Clique para selecionar a subclasse Fazendeiro");
			stand.setItemMeta(sm);

			ItemStack vara = new ItemStack(Material.FISHING_ROD);
			ItemMeta vm = vara.getItemMeta();
			vm.setDisplayName(ChatColor.GOLD + "Clique para selecionar a subclasse Pescador");
			vm.setLore(lore);
			vara.setItemMeta(vm);

			ItemStack primaria = new ItemStack(Material.PAPER);
			ItemMeta primariam = primaria.getItemMeta();
			primariam.setDisplayName(ChatColor.YELLOW + "Classes Primarias");
			primaria.setItemMeta(primariam);

			ItemStack secundaria = new ItemStack(Material.PAPER);
			ItemMeta secundariam = secundaria.getItemMeta();
			secundariam.setDisplayName(ChatColor.YELLOW + "Classes Secundárias (Subclasses)");
			secundaria.setItemMeta(secundariam);

			ItemStack confirmar = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
			ItemMeta confirmarm = confirmar.getItemMeta();
			confirmarm.setDisplayName(ChatColor.GREEN + "Clique para confimar");
			confirmar.setItemMeta(confirmarm);

			ItemStack cancelar = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)14);
			ItemMeta cancelarm = cancelar.getItemMeta();
			cancelarm.setDisplayName(ChatColor.RED + "Clique para cancelar");
			cancelar.setItemMeta(cancelarm);

			ItemStack prselec = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)11);
			ItemMeta prselecm = prselec.getItemMeta();
			prselecm.setDisplayName(ChatColor.BLUE + "Classe Selecionada: Nenhuma");
			prselec.setItemMeta(prselecm);

			ItemStack seselec = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)3);
			ItemMeta seselecm = seselec.getItemMeta();
			seselecm.setDisplayName(ChatColor.BLUE + "Subsclasse Selecionada: Nenhuma");
			seselec.setItemMeta(seselecm);

			menu.setItem(0, espada);	  
			menu.setItem(9, arco);	  
			menu.setItem(18, livro);	  
			menu.setItem(27, potion);	  
			menu.setItem(36, flint);
			menu.setItem(45, axe);

			menu.setItem(8, pick);	  
			menu.setItem(17, log);	  
			menu.setItem(26, ss);	  
			menu.setItem(35, bigorna);	
			menu.setItem(44, vara);  
			menu.setItem(53, stand);

			menu.setItem(4, confirmar);
			menu.setItem(13, cancelar);

			menu.setItem(31, prselec);
			menu.setItem(40, seselec);

			p.openInventory(menu);

		}

	}

	@EventHandler
	public void clickMenu(InventoryClickEvent event){

		if(event.getInventory().getTitle().contains("Menu de Classes")){

			Player p = (Player) event.getWhoClicked();
			Jogador jog = plugin.corejogador.getJogador(p.getName());
			ItemStack i = event.getCurrentItem();

			if(i != null && i.getType() != null){

				if(i.getType().equals(Material.DIAMOND_SWORD)||
						i.getType().equals(Material.BOW)||
						i.getType().equals(Material.BOOK)||
						i.getType().equals(Material.POTION)||
						i.getType().equals(Material.DIAMOND_AXE)){

					if(i.hasItemMeta()) if(i.getItemMeta().hasDisplayName()){

						String c = i.getItemMeta().getDisplayName().substring(34);

						ItemStack pr = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte) 11);
						ItemMeta prm = pr.getItemMeta();
						prm.setDisplayName(ChatColor.BLUE + "Classe Selecionada: " + c);
						pr.setItemMeta(prm);

						event.getInventory().setItem(31, pr);

						for(int in = 0; in < 6; in++){
							event.getInventory().setItem(1 + (9*in), null);
						}

						ItemStack prselec1 = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)11);
						ItemMeta prselecm1 = prselec1.getItemMeta();
						prselecm1.setDisplayName(ChatColor.BLUE + "Classe Selecionada");
						prselec1.setItemMeta(prselecm1);

						event.getInventory().setItem(event.getSlot() + 1, prselec1);

					}

				}else if(i.getType().equals(Material.FLINT)){

					if(p.hasPermission("classe.assassino")){

						if(i.hasItemMeta()) if(i.getItemMeta().hasDisplayName()){

							String c = i.getItemMeta().getDisplayName().substring(34);

							ItemStack pr = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte) 11);
							ItemMeta prm = pr.getItemMeta();
							prm.setDisplayName(ChatColor.BLUE + "Classe Selecionada: " + c);
							pr.setItemMeta(prm);

							event.getInventory().setItem(31, pr);

							for(int in = 0; in < 6; in++){
								event.getInventory().setItem(1 + (9*in), null);
							}

							ItemStack prselec1 = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)11);
							ItemMeta prselecm1 = prselec1.getItemMeta();
							prselecm1.setDisplayName(ChatColor.BLUE + "Classe Selecionada");
							prselec1.setItemMeta(prselecm1);

							event.getInventory().setItem(event.getSlot() + 1, prselec1);

						}

					}else p.sendMessage(ChatColor.RED + "A classe Assassino é exclusiva para nobres.");

				}else if(i.getType().equals(Material.DIAMOND_PICKAXE)||
						i.getType().equals(Material.LOG)||
						i.getType().equals(Material.STONE_SWORD)||
						i.getType().equals(Material.ANVIL)||
						i.getType().equals(Material.WHEAT)){
					if(i.hasItemMeta()) if(i.getItemMeta().hasDisplayName()){

						String c = i.getItemMeta().getDisplayName().substring(37);

						ItemStack pr = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte) 3);
						ItemMeta prm = pr.getItemMeta();
						prm.setDisplayName(ChatColor.BLUE + "Subclasse Selecionada: " + c);
						pr.setItemMeta(prm);

						event.getInventory().setItem(40, pr);

						for(int in = 0; in < 6; in++){
							event.getInventory().setItem(7 + (9*in), null);
						}

						ItemStack prselec1 = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)3);
						ItemMeta prselecm1 = prselec1.getItemMeta();
						prselecm1.setDisplayName(ChatColor.BLUE + "Subclasse Selecionada");
						prselec1.setItemMeta(prselecm1);

						event.getInventory().setItem(event.getSlot() - 1, prselec1);

					}

				}else if(i.getType().equals(Material.FISHING_ROD)){

					if(p.hasPermission("classe.pescador")){

						if(i.hasItemMeta()) if(i.getItemMeta().hasDisplayName()){

							String c = i.getItemMeta().getDisplayName().substring(37);

							ItemStack pr = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte) 3);
							ItemMeta prm = pr.getItemMeta();
							prm.setDisplayName(ChatColor.BLUE + "Subclasse Selecionada: " + c);
							pr.setItemMeta(prm);

							event.getInventory().setItem(40, pr);

							for(int in = 0; in < 6; in++){
								event.getInventory().setItem(7 + (9*in), null);
							}

							ItemStack prselec1 = new ItemStack(Material.STAINED_GLASS_PANE,1, (byte)3);
							ItemMeta prselecm1 = prselec1.getItemMeta();
							prselecm1.setDisplayName(ChatColor.BLUE + "Subclasse Selecionada");
							prselec1.setItemMeta(prselecm1);

							event.getInventory().setItem(event.getSlot() - 1, prselec1);
						}

					}else p.sendMessage(ChatColor.RED + "A subclasse Pescador é exclusiva para nobres.");

				}else if(i.getType().equals(Material.STAINED_GLASS_PANE)){

					if(i.hasItemMeta()) if(i.getItemMeta().hasDisplayName()){

						if(i.getItemMeta().getDisplayName().contains("confimar")){

							ItemStack i31 = event.getInventory().getItem(31);
							ItemStack i40 = event.getInventory().getItem(40);

							if(i31 != null && i31.getType() != null){
								if(i31.hasItemMeta() &&i31.getItemMeta().hasDisplayName()){
									if(i40 != null && i40.getType() != null){
										if(i40.hasItemMeta() &&i40.getItemMeta().hasDisplayName()){

											String c = i31.getItemMeta().getDisplayName().substring(22);
											Classe cls = Classe.NORMAL;
											String t = i40.getItemMeta().getDisplayName().substring(25);
											Trabalho trab = Trabalho.NORMAL;

											if(!c.contains("Nenhuma") || !t.contains("Nenhuma")){
												if(c.contains("Guerreiro")){
													cls = Classe.GUERREIRO;
												}else if(c.contains("Arqueiro")){
													cls = Classe.ARQUEIRO;
												}else if(c.contains("Mago")){
													cls = Classe.MAGO;
												}else if(c.contains("Sacerdote")){
													cls = Classe.SACERDOTE;
												}else if(c.contains("Assassino")){
													cls = Classe.ASSASSINO;
												}else if(c.contains("Viking")){
													cls = Classe.VIKING;
												}

												if(t.contains("Minerador")){
													trab = Trabalho.MINERADOR;
												}else if(t.contains("Lenhador")){
													trab = Trabalho.LENHADOR;
												}else if(t.contains("Caçador")){
													trab = Trabalho.CAÇADOR;
												}else if(t.contains("Ferreiro")){
													trab = Trabalho.FERREIRO;
												}else if(t.contains("Fazendeiro")){
													trab = Trabalho.FAZENDEIRO;
												}else if(t.contains("Pescador")){
													trab = Trabalho.PESCADOR;
												}

												p.closeInventory();
												plugin.coreclasses.setClasse(jog, cls, trab);
												//Location loc = new Location(Bukkit.getServer()
												//	  .getWorld("world"), 93, 71, 466);
												//p.teleport(loc);

												//Bukkit.getServer().dispatchCommand(p, "localtp1");

												//Coloca um som dahora pra tocar aqui
												p.sendMessage("Classe: " + cls.toString());
												p.sendMessage("Trabalho: " + trab.toString());
												p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
												p.sendMessage("Musica noteblock");
											}
										}
									}
								}
							}

						}else if(i.getItemMeta().getDisplayName().contains("cancelar")){
							p.closeInventory();
						}

					}
				}
			}
			event.setCancelled(true);

		}

	}

}
