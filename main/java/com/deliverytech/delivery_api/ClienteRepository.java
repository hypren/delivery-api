package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Implementar busca por e-mail 
    Optional<Cliente> findByEmail(String email);

    // Implementar busca por status ativo 
    List<Cliente> findByAtivo(boolean ativo);
}