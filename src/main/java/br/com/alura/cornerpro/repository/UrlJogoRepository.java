package br.com.alura.cornerpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.cornerpro.model.Jogo;

public interface UrlJogoRepository extends JpaRepository<Jogo, Long>{

}

