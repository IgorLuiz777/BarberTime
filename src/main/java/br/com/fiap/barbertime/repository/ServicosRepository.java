package br.com.fiap.barbertime.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.barbertime.model.Servicos;

public interface ServicosRepository extends JpaRepository<Servicos, Long>{
    
    @Query("SELECT s FROM Servicos s WHERE s.nome = :nome")
    Page<Servicos> findByNome(@Param("nome") String nome, Pageable pageable);

}
