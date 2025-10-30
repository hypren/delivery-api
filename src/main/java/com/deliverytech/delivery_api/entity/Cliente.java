package com.deliverytech.delivery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity avisa ao JPA que esta classe é uma tabela no banco de dados
@Entity
// @Table dá o nome para a tabela (opcional, mas boa prática)
@Table(name = "clientes")
public class Cliente {

    // @Id avisa que este é o campo de "Identificação Única" (Chave Primária)
    @Id
    // @GeneratedValue avisa que o banco de dados deve gerar o valor automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private Boolean ativo;

    // --- Getters e Setters ---
    // (O JPA precisa deles para funcionar)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}