package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante; // Assumindo que Produto tem uma referência a Restaurante
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos por restaurante [cite: 32]
    List<Produto> findByRestaurante(Restaurante restaurante);

    // Buscar produtos por restaurante e disponibilidade [cite: 32]
    List<Produto> findByRestauranteAndDisponivel(Restaurante restaurante, boolean disponivel);
    
    // Buscar por categoria e disponibilidade [cite: 32]
    List<Produto> findByCategoriaAndDisponivel(String categoria, boolean disponivel);
}