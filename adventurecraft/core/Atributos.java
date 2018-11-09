package me.soldado.adventurecraft.core;

public class Atributos {

	int restante = 0;
	int força = 0;
	int destreza = 0;
	int inteligencia = 0;
	int agilidade = 0;
	int vitalidade = 0;
	
	
	public Atributos(int restante, int força, int destreza, int inteligencia, int agilidade, int vitalidade){
		this.restante = restante;
		this.força = força;
		this.destreza = força;
		this.inteligencia = inteligencia;
		this.agilidade = agilidade;
		this.vitalidade = vitalidade;
	}


	public int getRestante() {
		return restante;
	}


	public void setRestante(int restante) {
		this.restante = restante;
	}


	public int getForça() {
		return força;
	}


	public void setForça(int força) {
		this.força = força;
	}


	public int getDestreza() {
		return destreza;
	}


	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}


	public int getInteligencia() {
		return inteligencia;
	}


	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}


	public int getAgilidade() {
		return agilidade;
	}


	public void setAgilidade(int agilidade) {
		this.agilidade = agilidade;
	}


	public int getVitalidade() {
		return vitalidade;
	}


	public void setVitalidade(int vitalidade) {
		this.vitalidade = vitalidade;
	}
	
	
	
}
