package br.com.alura.cornerpro.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.alura.cornerpro.model.Equipe;
import br.com.alura.cornerpro.model.Jogo;
import br.com.alura.cornerpro.model.JogoId;
import br.com.alura.cornerpro.repository.EquipeRepository;
import br.com.alura.cornerpro.repository.JogoRepository;
import br.com.alura.cornerpro.service.Analise;
import br.com.alura.cornerpro.service.CarregaJogo;

@Component
public class Principal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private JogoRepository jogoRepositorio;
	@Autowired
	private EquipeRepository equipeRepositorio;

	JButton buttonAnalisarTime;
	JButton buttonAnalisarTodosTimes;
	JTextField textEquipe;
	JTextField textPressao;
	JButton buttonCarregarJogo;
	JButton buttonCarregarJogosCampeonato;
	JButton buttonAtualizarNumeros;
	JTextField textUrl;
	JTextField textUrlCampeonato;

	public Principal() {
		this.jogoRepositorio = null;
		setTitle("Meu JFrame");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação padrão ao fechar
		setLayout(null);
		textUrlCampeonato = new JTextField();
		textUrlCampeonato.setText("");
		textUrlCampeonato.setBounds(50, 50, 400, 30);
		add(textUrlCampeonato);
		buttonCarregarJogosCampeonato = new JButton("Carregar Jogos Campeonato");
		buttonCarregarJogosCampeonato.setBounds(470, 50, 200, 30);
		buttonCarregarJogosCampeonato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("inicio");
					
					String[] urls = textUrlCampeonato.getText().split(";");
					for (String urlCampeonato : urls) {
						CarregaJogo.listaEquipesFromCampeonatoUrl(urlCampeonato).forEach(urlEquipe->{
							try {
								CarregaJogo.listaJogosFromEquipeUrl(urlEquipe).forEach(u -> {
									try {
										Jogo jogo = CarregaJogo.getJogoFromUrl(u);
										
										if (jogo != null && !jogoRepositorio
												.existsById(new JogoId(jogo.getDiaHora(), jogo.getTimeCasa(), jogo.getTimeFora()))) {
											jogoRepositorio.save(jogo);
											System.out.println("Jogo carregado: "+jogo.getDiaHora()+ " - "+jogo.getTimeCasa().getNome()+" vs "+jogo.getTimeFora().getNome());
										}
									} catch (MalformedURLException ex) {
										ex.printStackTrace();
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								});
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						});
					}
					System.out.println("fim");
//				} catch (MalformedURLException ex) {
//					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
				
			}
		});
		add(buttonCarregarJogosCampeonato);
		textUrl = new JTextField();
		textUrl.setText("");
		textUrl.setBounds(50, 100, 400, 30);
		add(textUrl);
		buttonCarregarJogo = new JButton("Carregar Jogos Time");
		buttonCarregarJogo.setBounds(470, 100, 200, 30);
		buttonCarregarJogo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("inicio");
					
					String[] urls = textUrl.getText().split(";");
					for (String url : urls) {
						CarregaJogo.listaJogosFromEquipeUrl(url).forEach(u -> {
							try {
								Jogo jogo = CarregaJogo.getJogoFromUrl(u);
								
								if (jogo != null && !jogoRepositorio
										.existsById(new JogoId(jogo.getDiaHora(), jogo.getTimeCasa(), jogo.getTimeFora()))) {
									jogoRepositorio.save(jogo);
									System.out.println("Jogo carregado: "+jogo.getDiaHora()+ " - "+jogo.getTimeCasa().getNome()+" vs "+jogo.getTimeFora().getNome());
								}
							} catch (MalformedURLException ex) {
								ex.printStackTrace();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						});
					}
					System.out.println("fim");
				} catch (MalformedURLException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
				
			}
		});
		add(buttonCarregarJogo);
		buttonAtualizarNumeros = new JButton("AtualizarNumeros");
		buttonAtualizarNumeros.setBounds(680, 100, 200, 30);
		buttonAtualizarNumeros.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Jogos carregados: "+jogoRepositorio.findAll().stream().count());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		add(buttonAtualizarNumeros);
		textEquipe = new JTextField();
		textEquipe.setText("Juventus");
		textEquipe.setBounds(50, 150, 150, 30);
		add(textEquipe);
		textPressao = new JTextField();
		textPressao.setText("20");
		textPressao.setBounds(210, 150, 50, 30);
		add(textPressao);
		buttonAnalisarTime = new JButton("Analisar Time");
		buttonAnalisarTime.setBounds(280, 150, 150, 30);
		buttonAnalisarTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("inicio analise");
					Analise analise = new Analise(jogoRepositorio);
					analise.analisarEstrategia01(textEquipe.getText(), true);
					analise.analisarEstrategia02(textEquipe.getText(), true);
					analise.analisarEstrategia03(textEquipe.getText(), true);
					analise.analisarEstrategia04(textEquipe.getText(), Integer.valueOf(textPressao.getText()), true);
					
					System.out.println("fim analise");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		add(buttonAnalisarTime);

		buttonAnalisarTodosTimes = new JButton("Analisar Times");
		buttonAnalisarTodosTimes.setBounds(450, 150, 150, 30);
		buttonAnalisarTodosTimes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("inicio analise");
					Analise analise = new Analise(jogoRepositorio);
					for (Equipe element : equipeRepositorio.findAll()) {
						analise.analisarEstrategia01(element.getNome(), false);
						analise.analisarEstrategia02(element.getNome(), false);
						analise.analisarEstrategia03(element.getNome(), false);
						analise.analisarEstrategia04(element.getNome(), Integer.valueOf(textPressao.getText()), false);
					}
					
					System.out.println("fim analise");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		add(buttonAnalisarTodosTimes);
		// Outras configurações opcionais
		// setLocationRelativeTo(null); // Centraliza o JFrame na tela
		// setResizable(false); // Impede o redimensionamento
	}

	public void capturarJogo() throws IOException {

//		try {
//			Jogo jogo = CarregaJogo.getJogoFromUrl(textUrl.getText());
//			jogoRepositorio.save(jogo);
//			System.out.println(jogo);
//		} catch (MalformedURLException ex) {
//			ex.printStackTrace();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

	}

	public void analisarEstratégia(String string) {
		// TODO Auto-generated method stub

	}

}
