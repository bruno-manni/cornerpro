package br.com.alura.cornerpro.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LoadPage {

    public void getPage(URL url, File file) throws IOException {
        BufferedReader in =
                new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        String inputLine;
        
        while ((inputLine = in.readLine()) != null) {
		
            // Imprime página no console
            System.out.println(inputLine);
			
            // Grava pagina no arquivo
            out.write(inputLine);
            out.newLine();
        }

        in.close();
        out.flush();
        out.close();
    }
    
    public void jsonUp(String url) throws IOException {
    	Document doc = Jsoup.connect(url).get();
    	Elements events = doc.getElementsByClass("events");
    	System.out.println("for");
    	for (Element element : events) {
    		for (Element item : element.getElementsByClass("events_home")) {
				if (item.hasClass("fa-flag")) {
					System.out.println(item.attr("title"));
				}
    		}
		}
    	System.out.println("foreach");
    	events.forEach(e->{
    		e.getElementsByClass("events_home")
    		.forEach(System.out::println);
    	});
    	events.forEach(e->{
    		e.getElementsByClass("events_away")
    		.forEach(System.out::println);
    	});
    	//System.out.println(events);
    }

//    public static void main(String[] args) throws IOException  {
//        URL url = null;
//        File file = new File("D:\\Dev\\workspace\\connerpro\\files\\page.html");
//        try {
//            url = new URL("https://cornerprobet.com/pt/analysis/qghh5");
//            //new LoadPage().getPage(url, file);
//            new LoadPage().jsonUp("https://cornerprobet.com/pt/analysis/qghh5");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}