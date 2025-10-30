package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Mapeamento que reflete a hierarquia: Produtos são acessados via Restaurantes
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // POST /restaurantes/{restauranteId}/produtos : Criar um novo produto para o restaurante
    // Tarefa: Cadastro por restaurante
    @PostMapping
    public ResponseEntity<Produto> criarProduto(
            @PathVariable Long restauranteId,
            @RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.salvar(restauranteId, produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Trata se o restaurante não for encontrado ou a validação do preço falhar
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /restaurantes/{restauranteId}/produtos : Buscar todos os produtos do restaurante
    // Tarefa: buscar produtos por restaurante
    @GetMapping
    public ResponseEntity<List<Produto>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        try {
            List<Produto> produtos = produtoService.buscarPorRestaurante(restauranteId);
            return ResponseEntity.ok(produtos);
        } catch (IllegalArgumentException e) {
            // Se o RestauranteId não for encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // GET /restaurantes/{restauranteId}/produtos/{produtoId} : Buscar um produto específico
    // O ID do restaurante na URL é bom para validação, embora o Service só precise do produtoId
    @GetMapping("/{produtoId}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long produtoId) {
        return produtoService.buscarPorId(produtoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /restaurantes/{restauranteId}/produtos/{produtoId}/disponibilidade
    // Tarefa: Alternar a disponibilidade do produto
    @PatchMapping("/{produtoId}/disponibilidade")
    public ResponseEntity<Produto> alterarDisponibilidade(
            @PathVariable Long produtoId,
            @RequestParam boolean disponivel) {
        try {
            Produto produtoAtualizado = produtoService.alterarDisponibilidade(produtoId, disponivel);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (IllegalArgumentException e) {
            // Produto não encontrado
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // Status redundante (ex: tentar indisponibilizar algo já indisponível)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}