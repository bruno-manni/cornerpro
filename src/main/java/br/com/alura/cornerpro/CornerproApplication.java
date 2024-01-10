package br.com.alura.cornerpro;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.alura.cornerpro.view.Principal;

@SpringBootApplication
public class CornerproApplication {
	
//	@Autowired
//	private JogoRepository jogoRepositorio;
	
	public static void main(String[] args) {
		//SpringApplication.run(CornerproApplication.class, args);
		ApplicationContext context = new SpringApplicationBuilder(CornerproApplication.class)
			    .headless(false).run(args);
			    Principal a = context.getBean(Principal.class);
		        // Exibe o JFrame
		        a.setVisible(true);
	}
//
//	@Override
//	public void run(String... args) throws Exception {
		//Principal principal = new Principal(jogoRepositorio);
		//principal.setVisible(true);
		//principal.capturarJogo();
		//principal.analisarEstratégia("Juventus");
//		ApplicationContext context = new SpringApplicationBuilder(CornerproApplication.class)
//	    .headless(false).run(args);
//	    Principal a = context.getBean(Principal.class);
//	    a.setVisible(true);
//	}
}
