package br.com.alura.cornerpro.model;

import jakarta.persistence.Entity;

@Entity
public class Pressao extends Evento{
	
	private int pressaoCasa;
	private int pressaoFora;
	
	public int getPressaoCasa() {
		return pressaoCasa;
	}
	public void setPressaoCasa(int pressaoCasa) {
		this.pressaoCasa = pressaoCasa;
	}
	public int getPressaoFora() {
		return pressaoFora;
	}
	public void setPressaoFora(int pressaoFora) {
		this.pressaoFora = pressaoFora;
	}
}
