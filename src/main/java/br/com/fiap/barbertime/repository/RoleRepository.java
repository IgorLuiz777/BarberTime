package br.com.fiap.barbertime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.barbertime.model.Role;
import br.com.fiap.barbertime.model.RoleName;


public interface RoleRepository extends JpaRepository<Role, Long> {
    

    Optional<Role> findByName(RoleName name);

}
