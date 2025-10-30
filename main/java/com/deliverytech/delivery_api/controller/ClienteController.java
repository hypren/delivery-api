package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Define que esta classe é um Controller REST e mapeia a URL base
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    // Injeta a camada de Service (Regras de Negócio)
    @Autowired
    private ClienteService clienteService;

    // POST /clientes : Criar um novo cliente
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.salvar(cliente);
            // Retorna status 201 Created
            return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Trata exceção de validação (ex: e-mail duplicado)
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /clientes : Buscar todos os clientes
    @GetMapping
    public List<Cliente> buscarTodos() {
        return clienteService.buscarTodos();
    }

    // GET /clientes/{id} : Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                // Se encontrar, retorna 200 OK com o cliente
                .map(ResponseEntity::ok)
                // Se não encontrar, retorna 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /clientes/{id} : Atualizar um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteDetalhes) {
        try {
            Cliente clienteAtualizado = clienteService.atualizar(id, clienteDetalhes);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            // Trata exceção de cliente não encontrado
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /clientes/{id} : Inativar (Soft Delete) o cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> inativarCliente(@PathVariable Long id) {
        try {
            clienteService.inativar(id);
            // Retorna 204 No Content para indicar sucesso na operação sem corpo de resposta
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // Cliente não encontrado
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // Cliente já inativo
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
        }
    }
}