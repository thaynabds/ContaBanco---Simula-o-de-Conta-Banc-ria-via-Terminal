package com.thaynabds.contabanco.repository;

import com.thaynabds.contabanco.model.Conta;

import java.util.Optional;

public interface ContaRepository {

    void salvar(Conta conta);

    Optional<Conta> buscarPorNumero(int numero);

    boolean existe(int numero);
}
