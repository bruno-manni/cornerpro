package br.com.alura.cornerpro.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Equipe {
	@Id
	private String nome;
	private String pais;
	@OneToMany(mappedBy = "timeCasa", cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	private List<Jogo> jogosCasa = new ArrayList<>();
	@OneToMany(mappedBy = "timeFora", cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	private List<Jogo> jogosFora = new ArrayList<>();
	//------------------------------------------------------
	public List<Jogo> getJogosCasa() {
		return jogosCasa;
	}
	public void setJogosCasa(List<Jogo> jogosCasa) {
		this.jogosCasa = jogosCasa;
	}
	public List<Jogo> getJogosFora() {
		return jogosFora;
	}
	public void setJogosFora(List<Jogo> jogosFora) {
		this.jogosFora = jogosFora;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	//------------------------------------------------------
	public Equipe(){
	}
	public Equipe(String nome, String pais){
		this.setNome(nome);
		this.setPais(pais);
	}
	//------------------------------------------------------
	public void addJogosCasa(Jogo jogoCasa) {
        this.jogosCasa.add(jogoCasa);
        jogoCasa.setTimeCasa(this);
    }
	public void addJogosFora(Jogo jogoFora) {
        this.jogosFora.add(jogoFora);
        jogoFora.setTimeCasa(this);
    }
}
