package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Mapeamento que reflete a principal entidade gerenciada
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // POST /pedidos?clienteId=X : Criar pedido (Tarefa: Criar pedido)
    // O ID do cliente é passado como parâmetro de consulta para vincular
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestParam Long clienteId, @RequestBody Pedido pedido) {
        try {
            Pedido novoPedido = pedidoService.criarPedido(clienteId, pedido);
            // Retorna status 201 Created
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Cliente não encontrado, ou dados inválidos no pedido
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            // Cliente inativo
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    // GET /pedidos/{id} : Consultar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /pedidos/cliente/{clienteId} : Consultar por cliente (Tarefa: consultar por cliente)
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Long clienteId) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPorCliente(clienteId);
            return ResponseEntity.ok(pedidos);
        } catch (IllegalArgumentException e) {
            // Cliente não encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /pedidos/{id}/status : Atualizar status (Tarefa: atualizar status)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestParam String novoStatus) {
        try {
            Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            // Pedido não encontrado
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // Transição de status inválida (ex: já entregue/cancelado)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}