package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    // CRUD Básico
    public Restaurante salvar(Restaurante restaurante) {
        // Validação básica: Garante que o restaurante tem um nome
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do restaurante é obrigatório.");
        }
        // Garante que o status inicial é ativo
        if (restaurante.getAtivo() == null) {
            restaurante.setAtivo(true);
        }
        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }
    
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    // Busca personalizada (usando o método do Repository)
    public List<Restaurante> buscarAtivosOrdenadosPorAvaliacao() {
        return restauranteRepository.findByAtivoOrderByAvaliacaoDesc(true);
    }

    // Regra de Negócio: Controle de Status Ativo/Inativo 
    public Restaurante ativar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + id));

        if (restaurante.getAtivo()) {
            throw new IllegalStateException("O restaurante já está ativo.");
        }

        restaurante.setAtivo(true);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante inativar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + id));
        
        if (!restaurante.getAtivo()) {
            throw new IllegalStateException("O restaurante já está inativo.");
        }
        
        restaurante.setAtivo(false);
        return restauranteRepository.save(restaurante);
    }
}