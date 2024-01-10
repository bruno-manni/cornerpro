package br.com.alura.cornerpro.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class JogoId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5362589780388611904L;
	private LocalDateTime diaHora;
	private Equipe timeCasa;
	private Equipe timeFora;
	public JogoId() {
	}
	public JogoId(LocalDateTime diaHora,Equipe timeCasa,Equipe timeFora) {
		this.diaHora = diaHora;
		this.timeCasa=timeCasa;
		this.timeFora=timeFora;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogoId jogoId = (JogoId) o;
        return Objects.equals(diaHora, jogoId.diaHora) && Objects.equals(timeCasa, jogoId.timeCasa)  && Objects.equals(timeFora, jogoId.timeFora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaHora, timeCasa, timeFora);
    }
}
