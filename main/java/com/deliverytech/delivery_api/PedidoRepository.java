package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.Cliente; // Assumindo que Pedido tem uma referência a Cliente
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por cliente [cite: 34]
    List<Pedido> findByCliente(Cliente cliente);
    
    // Filtrar por status e data [cite: 34]
    List<Pedido> findByStatusAndDataCriacaoBetween(String status, LocalDateTime dataInicio, LocalDateTime dataFim);

    // Exemplo de relatório simples usando @Query para calcular valor total
    // Nota: Os atributos da entidade devem ser usados na consulta JPQL/HQL.
    @Query("SELECT SUM(p.valorTotal) FROM Pedido p WHERE p.status = :status")
    Double calcularValorTotalPorStatus(String status);
}