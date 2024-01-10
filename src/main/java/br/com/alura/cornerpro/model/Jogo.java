package br.com.alura.cornerpro.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@IdClass(JogoId.class)
public class Jogo {
	@Id
	private LocalDateTime diaHora;
	@ManyToOne(cascade = { CascadeType.ALL })
	private Campeonato campeonato;
	@Id
	@ManyToOne(cascade = { CascadeType.ALL })
	private Equipe timeCasa;
	@Id
	@ManyToOne(cascade = { CascadeType.ALL })
	private Equipe timeFora;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo", fetch = FetchType.EAGER)
	private List<Evento> eventos = new ArrayList<>();
	private int previsaoCantosFT;
	private int previsaoCantosHT;
	private int previsaoCantosST;
	private String url;

	// ------------------------------------------------------
	public LocalDateTime getDiaHora() {
		return diaHora;
	}

	public void setDiaHora(LocalDateTime diaHora) {
		this.diaHora = diaHora;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Equipe getTimeCasa() {
		return timeCasa;
	}

	public void setTimeCasa(Equipe timeCasa) {
		this.timeCasa = timeCasa;
	}

	public Equipe getTimeFora() {
		return timeFora;
	}

	public void setTimeFora(Equipe timeFora) {
		this.timeFora = timeFora;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public int getPrevisaoCantosFT() {
		return previsaoCantosFT;
	}

	public void setPrevisaoCantosFT(int previsaoCantosFT) {
		this.previsaoCantosFT = previsaoCantosFT;
	}

	public int getPrevisaoCantosHT() {
		return previsaoCantosHT;
	}

	public void setPrevisaoCantosHT(int previsaoCantosHT) {
		this.previsaoCantosHT = previsaoCantosHT;
	}

	public int getPrevisaoCantosST() {
		return previsaoCantosST;
	}

	public void setPrevisaoCantosST(int previsaoCantosST) {
		this.previsaoCantosST = previsaoCantosST;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// ------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("DiaHora: ").append(diaHora).append(" - ")
		              .append("Campeonato: ").append(campeonato.getNome()).append(" - ")
		              .append("timeCasa: ").append(timeCasa != null ? timeCasa.getNome() : "").append(" - ")
		              .append("timeFora: ").append(timeFora != null ? timeFora.getNome() : "").append(" - ");

		eventos.stream()
		       .sorted(Comparator.comparing(Evento::getMinuto))
		       .forEach(e -> {
		    	   	stringBuilder.append("minuto: ").append(e.getMinuto()).append(" - ");
		    	   	if (e instanceof CantosCasa) stringBuilder.append("canto casa").append(" - ");
		    	   	
		       });

		stringBuilder.append("\n").append("previsãoCantosFT: ").append(previsaoCantosFT).append("\n")
		              .append("previsãoCantosHT: ").append(previsaoCantosHT).append("\n")
		              .append("previsãoCantosST: ").append(previsaoCantosST).append("\n")
		              .append("url: ").append(url).append("\n");
		
		stringBuilder.append("CantosCasa<38': ").append(eventos.stream()
			.filter(ev -> ev.getMinuto() < 38)
			.filter(e -> e instanceof CantosCasa)
			.count()).append("\n");
	       

		String resultadoFinal = stringBuilder.toString();
		return resultadoFinal;
	};

	public void addEventos(Evento evento) {
		this.eventos.add(evento);
		evento.setJogo(this);
	}
	
	public boolean teveCanto(int minuto_inicial, int minuto_final) {
		return eventos.stream().filter(e->e.getMinuto()>=minuto_inicial && e.getMinuto()<=minuto_final && (e instanceof CantosCasa || e instanceof CantosFora)).count()>0;
	} 
	
	public double mediaPressao(int min_inicial, int min_final) {
		double mediaPressao = eventos.stream()
                .filter(e -> e instanceof Pressao)
                .filter(e->e.getMinuto()>=min_inicial && e.getMinuto()<=min_final 
                	&& (((Pressao)e).getPressaoCasa()+((Pressao)e).getPressaoCasa()>1))
                .map(e -> (Pressao) e)
                .mapToDouble(e->e.getPressaoCasa()+e.getPressaoFora())
                .average()
                .orElse(0.0);

        return Math.round(mediaPressao);
	} 
	public int numeroCantos(int min_inicial, int min_final) {
		List<CantosCasa> eventosCantosCasa = eventos.stream()
                .filter(e -> e instanceof CantosCasa)
                .filter(e->e.getMinuto()>=min_inicial && e.getMinuto()<=min_final)
                .map(e -> (CantosCasa) e)
                .collect(Collectors.toList());
		List<CantosFora> eventosCantosFora = eventos.stream()
                .filter(e -> e instanceof CantosFora)
                .filter(e->e.getMinuto()>=min_inicial && e.getMinuto()<=min_final)
                .map(e -> (CantosFora) e)
                .collect(Collectors.toList());
		if (eventosCantosCasa.isEmpty() && eventosCantosFora.isEmpty()) {
            //System.out.println("Não há eventos de pressão para calcular a média.");
            return 0; 
        }
        return eventosCantosCasa.size()+eventosCantosFora.size();
	} 

}
