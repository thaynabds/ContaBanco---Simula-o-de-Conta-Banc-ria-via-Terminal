package com.thaynabds.contabanco.service;

import com.thaynabds.contabanco.exception.DadosInvalidosException;
import com.thaynabds.contabanco.model.Cliente;
import com.thaynabds.contabanco.model.Conta;
import com.thaynabds.contabanco.repository.ContaRepository;

import java.math.BigDecimal;

public final class ContaService {

    private final ContaRepository repositorio;

    public ContaService(ContaRepository repositorio) {
        if (repositorio == null) {
            throw new DadosInvalidosException("O repositório de contas é obrigatório.");
        }
        this.repositorio = repositorio;
    }

    public Conta abrir(String agencia, int numero, String nome, BigDecimal saldoInicial) {
        if (repositorio.existe(numero)) {
            throw new DadosInvalidosException("Já existe uma conta com o número " + numero + ".");
        }
        Conta conta = new Conta(numero, agencia, new Cliente(nome), saldoInicial);
        repositorio.salvar(conta);
        return conta;
    }

    public BigDecimal depositar(int numero, BigDecimal valor) {
        Conta conta = buscar(numero);
        conta.creditar(valor);
        repositorio.salvar(conta);
        return conta.saldo();
    }

    public BigDecimal sacar(int numero, BigDecimal valor) {
        Conta conta = buscar(numero);
        conta.debitar(valor);
        repositorio.salvar(conta);
        return conta.saldo();
    }

    public BigDecimal consultarSaldo(int numero) {
        return buscar(numero).saldo();
    }

    private Conta buscar(int numero) {
        return repositorio.buscarPorNumero(numero)
                .orElseThrow(() -> new DadosInvalidosException("Conta " + numero + " não encontrada."));
    }
}
