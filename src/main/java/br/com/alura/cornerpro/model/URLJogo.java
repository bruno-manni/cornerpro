package br.com.alura.cornerpro.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class URLJogo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private LocalDateTime diaHora;
	private String url;
	private boolean processado;
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public LocalDateTime getDiaHora() {
		return diaHora;
	}
	public void setDiaHora(LocalDateTime diaHora) {
		this.diaHora = diaHora;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		String s = "DiaHora: " + diaHora + " - " + "url: " + url;
		return s;
	}
	public boolean isProcessado() {
		return processado;
	}
	public void setProcessado(boolean processado) {
		this.processado = processado;
	}; 

}
