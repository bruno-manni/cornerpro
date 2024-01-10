package br.com.alura.cornerpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.cornerpro.model.Jogo;
import br.com.alura.cornerpro.model.JogoId;

public interface JogoRepository extends JpaRepository<Jogo, JogoId>{

}

