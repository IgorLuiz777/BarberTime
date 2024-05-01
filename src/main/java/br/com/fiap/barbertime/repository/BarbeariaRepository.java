package br.com.fiap.barbertime.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.barbertime.model.Barbearia;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {

    @Query("SELECT b FROM Barbearia b WHERE b.nome = :nome")
    Page<Barbearia> findByNome(@Param("nome") String nome, Pageable pageable);

}