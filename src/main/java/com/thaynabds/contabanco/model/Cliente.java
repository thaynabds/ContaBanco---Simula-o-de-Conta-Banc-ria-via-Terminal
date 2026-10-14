package com.thaynabds.contabanco.model;

import com.thaynabds.contabanco.exception.DadosInvalidosException;

public final class Cliente {

    private final String nome;

    public Cliente(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome do cliente é obrigatório.");
        }
        String ajustado = nome.trim().replaceAll("\\s+", " ");
        if (ajustado.length() < 3) {
            throw new DadosInvalidosException("O nome do cliente deve ter pelo menos 3 caracteres.");
        }
        if (ajustado.chars().anyMatch(Character::isDigit)) {
            throw new DadosInvalidosException("O nome do cliente não pode conter números.");
        }
        this.nome = ajustado;
    }

    public String nome() {
        return nome;
    }
}
