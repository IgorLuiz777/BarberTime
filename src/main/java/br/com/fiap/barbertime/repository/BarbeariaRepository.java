package br.com.fiap.barbertime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.barbertime.model.Barbearia;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Long>{
  
} 