package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository; 
    // Assumindo que Pedido tem a lista de Itens (que contêm o Produto)
    // Para simplificar, vamos focar na lógica principal de Pedido.

    private static final Double TAXA_ENTREGA = 5.00; // Valor fixo para exemplo

    // 1. Criação do Pedido e Cálculo de Valores (Regra de Negócio)
    public Pedido criarPedido(Long clienteId, Pedido pedido) {
        
        // 1.1. Validação do Cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        
        if (!cliente.getAtivo()) {
            throw new IllegalStateException("O cliente está inativo e não pode realizar pedidos.");
        }
        
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus("PENDENTE"); // Status inicial

        // 1.2. Cálculo de Valores (Simplificado para o exemplo)
        double subtotal = calcularSubtotal(pedido);
        
        pedido.setSubtotal(subtotal);
        pedido.setTaxaEntrega(TAXA_ENTREGA);
        pedido.setValorTotal(subtotal + TAXA_ENTREGA);

        // A validação de itens e produtos deve ser mais robusta em um cenário real.
        // Aqui, assumimos que os ItensPedido foram validados previamente.

        return pedidoRepository.save(pedido);
    }
    
    // Método auxiliar para calcular o subtotal
    // NOTA: Em um projeto real, essa lógica seria mais complexa, iterando pelos ItensPedido
    // e buscando o preço atual de cada produto no banco de dados.
    private double calcularSubtotal(Pedido pedido) {
        // Exemplo: Simplesmente retorna o subtotal que veio no objeto, 
        // mas em produção você faria o cálculo item por item.
        return pedido.getSubtotal() != null ? pedido.getSubtotal() : 0.0;
    }
    
    // 2. Busca por ID
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // 3. Consulta por Cliente
    public List<Pedido> buscarPorCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        
        return pedidoRepository.findByCliente(cliente);
    }
    
    // 4. Mudança de Status (Regra de Negócio)
    public Pedido atualizarStatus(Long pedidoId, String novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + pedidoId));

        // Validação básica de transição de status (Exemplo)
        if (pedido.getStatus().equals("ENTREGUE") || pedido.getStatus().equals("CANCELADO")) {
            throw new IllegalStateException("Não é possível alterar o status de um pedido já finalizado/cancelado.");
        }
        
        pedido.setStatus(novoStatus.toUpperCase());
        return pedidoRepository.save(pedido);
    }
}