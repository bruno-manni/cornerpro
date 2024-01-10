package br.com.alura.cornerpro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Evento{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
    private Jogo jogo;
	private int minuto;
	
	//------------------------------------------------------
	public Jogo getJogo() {
		return jogo;
	}
	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	public int getMinuto() {
		return minuto;
	}
	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}
	
	//------------------------------------------------------
	@Override
	public String toString() {
		String s = "minuto: " + minuto + "\n";
//		s += "pressaoCasa: " + pressaoCasa + "\n";
//		s += "pressaoFora: " + pressaoFora + "\n";
//		s += "remateBalizaCasa: " + rematesGolCasa + "\n";
//		s += "remateBalizaFora: " + rematesGolFora + "\n";
//		s += "rematesLateralCasa: " + rematesLateralCasa + "\n";
//		s += "rematesLateralFora: " + rematesLateralFora + "\n";
//		s += "cantosCasa: " + cantosCasa + "\n";
//		s += "cantosFora: " + cantosFora + "\n";
		return s;
	}
}


