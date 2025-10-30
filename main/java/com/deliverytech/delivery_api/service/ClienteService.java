package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Indica que esta classe é um componente de serviço e contém a lógica de negócio
@Service
public class ClienteService {

    // Injeção de dependência do Repository
    @Autowired
    private ClienteRepository clienteRepository;

    // 1. Cadastro, Validação de E-mail Único e Busca (Regra de Negócio)
    public Cliente salvar(Cliente cliente) {
        // Validação de E-mail: Verifica se já existe um cliente com este e-mail
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        // Garante que o cliente é criado como ativo
        if (cliente.getAtivo() == null) {
            cliente.setAtivo(true);
        }
        return clienteRepository.save(cliente);
    }

    // 2. Busca todos
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }
    
    // 3. Busca por ID
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // 4. Atualização
    public Cliente atualizar(Long id, Cliente clienteDetalhes) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        // Atualiza os campos necessários (exemplo)
        cliente.setNome(clienteDetalhes.getNome());
        cliente.setEmail(clienteDetalhes.getEmail());
        // Não permite mudar o status de ativo/inativo diretamente, use o método dedicado (opcional, mas boa prática)

        return clienteRepository.save(cliente);
    }

    // 5. Inativação (Regra de Negócio)
    public Cliente inativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        
        if (!cliente.getAtivo()) {
            throw new IllegalStateException("O cliente já está inativo.");
        }
        
        cliente.setAtivo(false);
        return clienteRepository.save(cliente);
    }
    
    // 6. Ativação (Opcional, mas útil)
    public Cliente ativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        if (cliente.getAtivo()) {
            throw new IllegalStateException("O cliente já está ativo.");
        }

        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }
}