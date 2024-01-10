package br.com.alura.cornerpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.cornerpro.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

}

