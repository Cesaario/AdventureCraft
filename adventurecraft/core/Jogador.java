package me.soldado.adventurecraft.core;

public class Jogador {

	String nome;
	Classe classe;
	Trabalho trabalho;
	Povo povo;
	double stamina;
	double mana;
	Atributos atributos;
	int nivel;
	double xp;
	
	public Jogador(String nome, Classe classe, Trabalho trabalho, Povo povo, double stamina, double mana, Atributos atributos, int nivel, double xp){
		this.nome = nome;
		this.classe = classe;
		this.trabalho = trabalho;
		this.povo = povo;
		this.stamina = stamina;
		this.mana = mana;
		this.atributos = atributos;
		this.nivel = nivel;
		this.xp = xp;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Povo getPovo() {
		return povo;
	}

	public void setPovo(Povo povo) {
		this.povo = povo;
	}

	public Trabalho getTrabalho() {
		return trabalho;
	}

	public void setTrabalho(Trabalho trabalho) {
		this.trabalho = trabalho;
	}

	public double getStamina() {
		return stamina;
	}

	public void setStamina(double stamina) {
		this.stamina = stamina;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public Atributos getAtributos() {
		return atributos;
	}

	public void setAtributos(Atributos atributos) {
		this.atributos = atributos;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public double getXp() {
		return xp;
	}

	public void setXp(double xp) {
		this.xp = xp;
	}
	
	public boolean magia(){
		if(classe.equals(Classe.MAGO) || classe.equals(Classe.SACERDOTE)) return true;
		return false;
	}
	
}
