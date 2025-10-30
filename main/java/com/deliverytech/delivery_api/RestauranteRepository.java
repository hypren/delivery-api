package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    
    // Buscar por nome (ou parte do nome) [cite: 30]
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    // Buscar por ativos e ordenar por avaliação [cite: 30]
    List<Restaurante> findByAtivoOrderByAvaliacaoDesc(boolean ativo);
    
    // Buscar por categoria (assumindo que Categoria é um campo na Entidade Restaurante) [cite: 30]
    List<Restaurante> findByCategoria(String categoria);
}