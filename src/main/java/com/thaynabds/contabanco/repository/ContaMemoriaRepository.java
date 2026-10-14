package com.thaynabds.contabanco.repository;

import com.thaynabds.contabanco.model.Conta;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ContaMemoriaRepository implements ContaRepository {

    private final Map<Integer, Conta> contas = new HashMap<>();

    @Override
    public void salvar(Conta conta) {
        contas.put(conta.numero(), conta);
    }

    @Override
    public Optional<Conta> buscarPorNumero(int numero) {
        return Optional.ofNullable(contas.get(numero));
    }

    @Override
    public boolean existe(int numero) {
        return contas.containsKey(numero);
    }
}
