package br.com.alura.cornerpro.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Campeonato {
	@Id
	String nome;
	String url;
	@OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL)
	private List<Jogo> jogos = new ArrayList<>();
	//------------------------------------------------------
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Jogo> getJogos() {
		return jogos;
	}
	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}
	//------------------------------------------------------
	public Campeonato(){
	}
	public Campeonato(String nome){
		this.nome = nome;
	}
	//------------------------------------------------------
	public void addJogos(Jogo jogo) {
        this.jogos.add(jogo);
        jogo.setCampeonato(this);
    }
}
