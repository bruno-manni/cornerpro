package br.com.alura.cornerpro.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.alura.cornerpro.model.Campeonato;
import br.com.alura.cornerpro.model.CantosCasa;
import br.com.alura.cornerpro.model.CantosFora;
import br.com.alura.cornerpro.model.CartaoACasa;
import br.com.alura.cornerpro.model.CartaoAFora;
import br.com.alura.cornerpro.model.CartaoVCasa;
import br.com.alura.cornerpro.model.CartaoVFora;
import br.com.alura.cornerpro.model.Equipe;
import br.com.alura.cornerpro.model.Evento;
import br.com.alura.cornerpro.model.GolCasa;
import br.com.alura.cornerpro.model.GolFora;
import br.com.alura.cornerpro.model.Jogo;
import br.com.alura.cornerpro.model.Pressao;
import br.com.alura.cornerpro.model.RemateGolCasa;
import br.com.alura.cornerpro.model.RemateGolFora;
import br.com.alura.cornerpro.model.RemateLadoCasa;
import br.com.alura.cornerpro.model.RemateLadoFora;


public class CarregaJogo {
	
	private static String getCampeonato(Element element) {
		return element.getElementsByTag("p").first().text();
	}
	
	private static LocalDateTime getDiaHora(Element element) {
		return LocalDateTime.parse(element.text(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	public static void listaJogosFromUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		//System.out.println(doc);
		Elements links = doc.getElementsByClass("all_games");
		for (Element element : links) {
			//System.out.println(element);
			System.out.println(element.attr("data-name"));
			for (Element item : element.getElementsByClass("league")) {
				System.out.println(item.html());
			}
		}
//		Elements links = doc.getElementsByTag("a");
//		for (Element element : links) {
//			if (element.attr("href").contains("/analysis/"))
//				System.out.println(element.attr("href").replace("..", "https://cornerprobet.com"));
//		}
	}
	public static List<String> listaJogosFromEquipeUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.getElementsByTag("a");
		List<String> listaUrl = new ArrayList<String>();
		for (Element element : links) {
			if (element.attr("href").contains("/analysis/")) {
				System.out.println(element.attr("href").replace("..", "https://cornerprobet.com"));
				listaUrl.add(element.attr("href").replace("..", "https://cornerprobet.com"));
			}
		}
		return listaUrl;
	}
	
	public static List<String> listaEquipesFromCampeonatoUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.getElementsByTag("a");
		List<String> listaUrl = new ArrayList<String>();
		for (Element element : links) {
			if (element.attr("href").contains("/team?teamId=")) {
				//System.out.println(element.attr("href").replace("..", "https://cornerprobet.com"));
				listaUrl.add(element.attr("href").replace("..", "https://cornerprobet.com"));
			}
		}
		return listaUrl;
	}
	
	public static Jogo getJogoFromUrl(String url) throws IOException {
		Jogo jogo = new Jogo();
		jogo.setUrl(url);
    	Document doc = Jsoup.connect(url).get();
    	//Campeonato
    	Elements match_league = doc.getElementsByClass("match_league");
		jogo.setCampeonato(new Campeonato(getCampeonato(match_league.first())));
		//data e hora
		Elements match_date = doc.getElementsByClass("match_date");
		jogo.setDiaHora(getDiaHora(match_date.first()));
		if (LocalDateTime.now().isBefore(jogo.getDiaHora().plusMinutes(90))) {
			return null;
		}
		//equipe casa
		Elements homeName = doc.getElementsByClass("homeName");
		jogo.setTimeCasa(new Equipe(homeName.first().text(),"Brasil"));
		//equipe fora
		Elements awayName = doc.getElementsByClass("awayName");
		jogo.setTimeFora(new Equipe(awayName.first().text(),"Brasil"));
		//pressão
		Elements pressure_index = doc.getElementsByClass("pressure_index");
		String jsonPressure = pressure_index.first().attr("data-pressure-index");
		List<Evento> eventos = new ArrayList<Evento>();
		if (!jsonPressure.trim().isEmpty()) {
			JSONObject jsonObject = new JSONObject(jsonPressure.trim());
			jsonObject.keys().forEachRemaining(key -> {
		        Object value = jsonObject.get(key);
		        Pressao evento = new Pressao();
		        evento.setMinuto(Integer.valueOf(key));
		        String valor = value.toString();
		        String[] pressao = valor.substring(1,valor.length()-1).split(",");
		        evento.setPressaoCasa((int)Math.round(Double.valueOf(pressao[0])));
		        evento.setPressaoFora((int)Math.round(Double.valueOf(pressao[1])));
		        evento.setJogo(jogo);
		        eventos.add(evento);
		    });
		}
		
//		//remates baliza
		Elements pressureIndexWrapper = doc.getElementsByAttributeValue("title","Remates baliza");
		for (Element element : pressureIndexWrapper) {
			Double top = Utils.getAttrToStyle(element.attr("style"),"top");
			if (top<30) {
				RemateGolCasa ev = new RemateGolCasa();
				ev.setJogo(jogo);
		        eventos.add(ev);
			}
			else {
				RemateGolFora ev = new RemateGolFora();
				ev.setJogo(jogo);
		        eventos.add(ev);
			}
		}
//		//remates lado
		Elements pressureIndexWrapperL = doc.getElementsByAttributeValue("title","Remates ao lado");
		for (Element element : pressureIndexWrapperL) {
			Double top = Utils.getAttrToStyle(element.attr("style"),"top");
			Double left = Utils.getAttrToStyle(element.attr("style"),"left");
			int minuto = (int)((left*90)/98);
			if (top<30) {
				RemateLadoCasa ev = new RemateLadoCasa();
				ev.setJogo(jogo);
				ev.setMinuto(minuto);
		        eventos.add(ev);
			}
			else {
				RemateLadoFora ev = new RemateLadoFora();
				ev.setJogo(jogo);
				ev.setMinuto(minuto);
				eventos.add(ev);
			}
		}
//		//cantos e gols
		Elements events = doc.getElementsByClass("events");
    	for (Element element : events) {
    		for (Element item : element.getElementsByClass("events_home")) {
				if (item.hasClass("fa-flag")) {
					int minuto = Utils.getMinutoForTitle(item.attr("title"));
					CantosCasa ev = new CantosCasa();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
				}
				if (item.hasClass("fa-futbol")) {
					int minuto = Utils.getMinutoForTitle(item.attr("title"));
					GolCasa ev = new GolCasa();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
				}
    		}
    		for (Element item : element.getElementsByClass("events_away")) {
				if (item.hasClass("fa-flag")) {
					int minuto = Utils.getMinutoForTitle(item.attr("title"));
					CantosFora ev = new CantosFora();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
				}
				if (item.hasClass("fa-futbol")) {
					int minuto = Utils.getMinutoForTitle(item.attr("title"));
					GolFora ev = new GolFora();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
				}
    		}
		}
//    	//Cartões
		Elements eventsItemHome = doc.getElementsByClass("event-item-home");
    	for (Element element : eventsItemHome) {
    		if (!element.getElementsByClass("fa-card-yellow").isEmpty()) {
    			int minuto = Utils.getMinutoArredondado(element.getElementsByClass("time").first().text());
    			if (minuto>0) {
    				CartaoACasa ev = new CartaoACasa();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
    			}
    		}
    		if (!element.getElementsByClass("fa-card-red").isEmpty()) {
    			int minuto = Utils.getMinutoArredondado(element.getElementsByClass("time").first().text());
    			if (minuto>0) {
    				CartaoVCasa ev = new CartaoVCasa();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
    			}
    		}
		}
    	Elements eventsItemAway = doc.getElementsByClass("event-item-away");
    	for (Element element : eventsItemAway) {
    		if (!element.getElementsByClass("fa-card-yellow").isEmpty()) {
    			int minuto = Utils.getMinutoArredondado(element.getElementsByClass("time").first().text());
    			if (minuto>0) {
    				CartaoAFora ev = new CartaoAFora();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
    			}
    		}
    		if (!element.getElementsByClass("fa-card-red").isEmpty()) {
    			int minuto = Utils.getMinutoArredondado(element.getElementsByClass("time").first().text());
				if (minuto>0) {
					CartaoVFora ev = new CartaoVFora();
					ev.setMinuto(minuto);
					ev.setJogo(jogo);
					eventos.add(ev);
				}
    			
    		}
		}
    	jogo.setEventos(eventos);
    	//previsão cantos FT HT ST
    	Elements cover = doc.getElementsByClass("cover");
    	int num_cover = 0;
		for (Element element : cover) {
			for (Element item : element.getAllElements()) {
				if (item.tag().getName().equals("span") && item.childNode(0).toString().contains("Prediction")) {
					switch (num_cover) {
						case 0: {
							jogo.setPrevisaoCantosFT(Utils.getPrevisao(item.childNode(0).toString()));					
							num_cover++;
							break;
						}
						case 1: {
							jogo.setPrevisaoCantosHT(Utils.getPrevisao(item.childNode(0).toString()));					
							num_cover++;
							break;
						}
						case 2: {
							jogo.setPrevisaoCantosST(Utils.getPrevisao(item.childNode(0).toString()));					
							num_cover++;
							break;
						}
						default: break;
					}
				}
			}
		}
    	
    	return jogo;
    }
}
