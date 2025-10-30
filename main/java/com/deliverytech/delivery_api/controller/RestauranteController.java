package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // POST /restaurantes : Criar um novo restaurante (CRUD)
    @PostMapping
    public ResponseEntity<Restaurante> criarRestaurante(@RequestBody Restaurante restaurante) {
        try {
            Restaurante novoRestaurante = restauranteService.salvar(restaurante);
            // Retorna status 201 Created
            return new ResponseEntity<>(novoRestaurante, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Trata exceção de validação (ex: nome obrigatório)
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /restaurantes : Buscar todos os restaurantes (CRUD)
    @GetMapping
    public List<Restaurante> buscarTodos() {
        return restauranteService.buscarTodos();
    }
    
    // GET /restaurantes/ativos : Buscar ativos e ordenados por avaliação (Funcionalidade extra do Service)
    @GetMapping("/ativos")
    public List<Restaurante> buscarAtivos() {
        return restauranteService.buscarAtivosOrdenadosPorAvaliacao();
    }

    // GET /restaurantes/{id} : Buscar restaurante por ID (CRUD)
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /restaurantes/{id} : Atualizar um restaurante (CRUD)
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizarRestaurante(@PathVariable Long id, @RequestBody Restaurante restauranteDetalhes) {
        try {
            // No Service, você precisaria de um método 'atualizar' mais completo. 
            // Para simplificar, estamos usando o 'salvar' com o ID.
            if (!restauranteService.buscarPorId(id).isPresent()) {
                 return ResponseEntity.notFound().build();
            }
            restauranteDetalhes.setId(id);
            return ResponseEntity.ok(restauranteService.salvar(restauranteDetalhes));
        } catch (IllegalArgumentException e) {
            // Se a validação do Service falhar
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PATCH /restaurantes/{id}/ativar : Ativar o status
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Restaurante> ativarRestaurante(@PathVariable Long id) {
        try {
            Restaurante restaurante = restauranteService.ativar(id);
            return ResponseEntity.ok(restaurante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // Já ativo
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // PATCH /restaurantes/{id}/inativar : Inativar o status (Controle de Status)
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Restaurante> inativarRestaurante(@PathVariable Long id) {
        try {
            Restaurante restaurante = restauranteService.inativar(id);
            return ResponseEntity.ok(restaurante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // Já inativo
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}