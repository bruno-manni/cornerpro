package br.com.alura.cornerpro.service;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.cornerpro.model.Jogo;
import br.com.alura.cornerpro.repository.JogoRepository;

public class Analise {

	private JogoRepository jogoRepositorio;

	public Analise() {

	}

	public Analise(JogoRepository jogoRepositorio) {
		this.jogoRepositorio = jogoRepositorio;
	}

	//Time jogando em Casa e Fora
	//%Jogos onde teve canto depois dos 40 min > 70%
	public void analisarEstrategia01(String time, boolean analiseIndividual) {
		try {
			int totalJogos = 0;
			int jogosCantoLimite = 0;
			List<Long> mediaPressaoTotal = new ArrayList<>();
			List<Long> mediaPressaoSemCanto = new ArrayList<>();
			List<Long> mediaPressaoCanto = new ArrayList<>();
			for (Jogo jf : jogoRepositorio.findAll().stream()
					.filter(j->j.getTimeCasa().getNome().equals(time)
							||j.getTimeFora().getNome().equals(time)).toList()) {
				Long mediaPressao = Math.round(jf.mediaPressao(34, 39));
				if (mediaPressao>1) {
					totalJogos++;
					if (jf.numeroCantos(40, 45) > 0) {
						jogosCantoLimite++;
						mediaPressaoCanto.add(mediaPressao);
					} else {
						mediaPressaoSemCanto.add(mediaPressao);
					}
					mediaPressaoTotal.add(mediaPressao);
				}
			}
			if (totalJogos>0 && (analiseIndividual || (totalJogos>10 && ((jogosCantoLimite * 100) / totalJogos)>=70))) {
				System.out.println("inicio analisarEstratégia 01: "+time);
				System.out.println("Total de jogos: " + totalJogos);
				System.out.println("Jogos Canto Limite: " + jogosCantoLimite);
				System.out.println("%Jogos Canto Limite: " + (jogosCantoLimite * 100) / totalJogos);
				System.out.println("mediaPressaoTotal: " + mediaPressaoTotal.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("mediaPressaoSemCanto: " + mediaPressaoSemCanto.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("mediaPressaoCanto: " + mediaPressaoCanto.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("fim analisarEstratégia 01");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Time jogando só em Casa
	//%Jogos onde teve canto depois dos 40 min > 70%
	public void analisarEstrategia02(String time, boolean analiseIndividual) {
		try {
			int totalJogos = 0;
			int jogosCantoLimite = 0;
			List<Long> mediaPressaoTotal = new ArrayList<>();
			List<Long> mediaPressaoSemCanto = new ArrayList<>();
			List<Long> mediaPressaoCanto = new ArrayList<>();
			for (Jogo jf : jogoRepositorio.findAll().stream()
					.filter(j->j.getTimeCasa().getNome().equals(time)).toList()) {
				Long mediaPressao = Math.round(jf.mediaPressao(34, 39));
				if (mediaPressao>1) {
					totalJogos++;
					if (jf.numeroCantos(40, 45) > 0) {
						jogosCantoLimite++;
						mediaPressaoCanto.add(mediaPressao);
					} else {
						mediaPressaoSemCanto.add(mediaPressao);
					}
					mediaPressaoTotal.add(mediaPressao);
				}
			}
			if (totalJogos>0 && (analiseIndividual || (totalJogos>3 && ((jogosCantoLimite * 100) / totalJogos)>=70))) {
				System.out.println("inicio analisarEstratégia 02: "+time);
				System.out.println("Total de jogos: " + totalJogos);
				System.out.println("Jogos Canto Limite: " + jogosCantoLimite);
				System.out.println("%Jogos Canto Limite: " + (jogosCantoLimite * 100) / totalJogos);
				System.out.println("mediaPressaoTotal: " + mediaPressaoTotal.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("mediaPressaoSemCanto: " + mediaPressaoSemCanto.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("mediaPressaoCanto: " + mediaPressaoCanto.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("fim analisarEstratégia 02");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Time jogando só em Fora
	//%Jogos onde teve canto depois dos 40 min > 70%
	public void analisarEstrategia03(String time, boolean analiseIndividual) {
		try {
			int totalJogos = 0;
			int jogosCantoLimite = 0;
			List<Long> mediaPressaoTotal = new ArrayList<>();
			List<Long> mediaPressaoSemCanto = new ArrayList<>();
			List<Long> mediaPressaoCanto = new ArrayList<>();
			for (Jogo jf : jogoRepositorio.findAll().stream()
					.filter(j->j.getTimeFora().getNome().equals(time)).toList()) {
				Long mediaPressao = Math.round(jf.mediaPressao(34, 39));
				if (mediaPressao>1) {
					totalJogos++;
					if (jf.numeroCantos(40, 45) > 0) {
						jogosCantoLimite++;
						mediaPressaoCanto.add(mediaPressao);
					} else {
						mediaPressaoSemCanto.add(mediaPressao);
					}
					mediaPressaoTotal.add(mediaPressao);
				}
			}
			if (totalJogos>0 && (analiseIndividual || (totalJogos>3 && ((jogosCantoLimite * 100) / totalJogos)>=70))) {
				System.out.println("inicio analisarEstratégia 03: "+time);
				System.out.println("Total de jogos: " + totalJogos);
				System.out.println("Jogos Canto Limite: " + jogosCantoLimite);
				System.out.println("%Jogos Canto Limite: " + (jogosCantoLimite * 100) / totalJogos);
				System.out.println("mediaPressaoTotal: " + mediaPressaoTotal.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("mediaPressaoSemCanto: " + mediaPressaoSemCanto.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("mediaPressaoCanto: " + mediaPressaoCanto.stream().mapToLong(Long::longValue).average().orElse(0.0));
				System.out.println("fim analisarEstratégia 03");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Time jogando em Casa e Fora
	//%Jogos onde Pressão alta entre os 34 e 39 min
	public void analisarEstrategia04(String time, int pressao, boolean analiseIndividual) {
		try {
			int totalJogos = 0;
			int jogosCantoLimite = 0;
			List<Jogo> jogos = jogoRepositorio.findAll().stream()
					.filter(j->j.getTimeCasa().getNome().equals(time)|| j.getTimeFora().getNome().equals(time)).toList();
			for (Jogo jf : jogos) {
				if (jf.mediaPressao(34, 39)>=pressao) {
					totalJogos++;
					if (jf.numeroCantos(40, 45) > 0) {
						jogosCantoLimite++;
					}
				}
				
			}
			if (totalJogos>1 && (analiseIndividual || ((jogosCantoLimite * 100) / totalJogos)>=70)) {
				System.out.println("inicio analisarEstratégia 04: "+time);
				System.out.println("Total de jogos: " + totalJogos);
				System.out.println("Jogos Canto Limite + Pressão>"+pressao+": " + jogosCantoLimite);
				System.out.println("%Jogos Canto Limite: " + (jogosCantoLimite * 100) / totalJogos);
				System.out.println("fim analisarEstratégia 04");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
