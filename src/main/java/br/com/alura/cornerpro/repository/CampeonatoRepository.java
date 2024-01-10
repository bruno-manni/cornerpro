package br.com.alura.cornerpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.cornerpro.model.Campeonato;

public interface CampeonatoRepository extends JpaRepository<Campeonato, String>{

}
