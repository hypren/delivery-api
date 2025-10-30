package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository; // Necessário para validar a associação

    // 1. Cadastro por restaurante, validação de preço (Regra de Negócio)
    public Produto salvar(Long restauranteId, Produto produto) {
        
        // 1.1. Validação de Associação: Busca o restaurante e verifica se ele existe
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + restauranteId));
        
        // 1.2. Validação de Preço (Regra de Negócio)
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser positivo.");
        }

        // 1.3. Define o restaurante e status inicial
        produto.setRestaurante(restaurante);
        if (produto.getDisponivel() == null) {
            produto.setDisponivel(true); // Por padrão, o produto é criado como disponível
        }

        return produtoRepository.save(produto);
    }

    // 2. Busca por ID
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // 3. Busca de produtos por restaurante (usando o método do Repository)
    public List<Produto> buscarPorRestaurante(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + restauranteId));
                
        return produtoRepository.findByRestaurante(restaurante);
    }

    // 4. Controle de Disponibilidade (Regra de Negócio)
    public Produto alterarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));

        // Validação: evita operação redundante
        if (produto.getDisponivel() == disponivel) {
            String status = disponivel ? "disponível" : "indisponível";
            throw new IllegalStateException("O produto já está " + status + ".");
        }
        
        produto.setDisponivel(disponivel);
        return produtoRepository.save(produto);
    }
}