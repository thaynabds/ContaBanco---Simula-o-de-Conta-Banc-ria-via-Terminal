package com.thaynabds.contabanco.model;

import com.thaynabds.contabanco.exception.DadosInvalidosException;
import com.thaynabds.contabanco.exception.SaldoInsuficienteException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Conta {

    private final int numero;
    private final String agencia;
    private final Cliente cliente;
    private BigDecimal saldo;

    public Conta(int numero, String agencia, Cliente cliente, BigDecimal saldoInicial) {
        if (numero <= 0) {
            throw new DadosInvalidosException("O número da conta deve ser maior que zero.");
        }
        if (agencia == null || agencia.isBlank()) {
            throw new DadosInvalidosException("A agência é obrigatória.");
        }
        if (cliente == null) {
            throw new DadosInvalidosException("O cliente é obrigatório.");
        }
        this.numero = numero;
        this.agencia = agencia.trim();
        this.cliente = cliente;
        this.saldo = normalizarSaldoInicial(saldoInicial);
    }

    public void creditar(BigDecimal valor) {
        saldo = saldo.add(normalizarValorMovimento(valor));
    }

    public void debitar(BigDecimal valor) {
        BigDecimal normalizado = normalizarValorMovimento(valor);
        if (saldo.compareTo(normalizado) < 0) {
            throw new SaldoInsuficienteException(saldo, normalizado);
        }
        saldo = saldo.subtract(normalizado);
    }

    public int numero() {
        return numero;
    }

    public String agencia() {
        return agencia;
    }

    public Cliente cliente() {
        return cliente;
    }

    public BigDecimal saldo() {
        return saldo;
    }

    private static BigDecimal normalizarSaldoInicial(BigDecimal saldoInicial) {
        BigDecimal normalizado = normalizarEscala(saldoInicial, "Informe o saldo inicial.");
        if (normalizado.signum() < 0) {
            throw new DadosInvalidosException("O saldo inicial não pode ser negativo.");
        }
        return normalizado;
    }

    private static BigDecimal normalizarValorMovimento(BigDecimal valor) {
        BigDecimal normalizado = normalizarEscala(valor, "Informe um valor.");
        if (normalizado.signum() <= 0) {
            throw new DadosInvalidosException("O valor deve ser maior que zero.");
        }
        return normalizado;
    }

    private static BigDecimal normalizarEscala(BigDecimal valor, String mensagemAusente) {
        if (valor == null) {
            throw new DadosInvalidosException(mensagemAusente);
        }
        if (valor.scale() > 2) {
            throw new DadosInvalidosException("Informe o valor com no máximo duas casas decimais.");
        }
        return valor.setScale(2, RoundingMode.UNNECESSARY);
    }
}
