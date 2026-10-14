package com.thaynabds.contabanco.model;

import com.thaynabds.contabanco.exception.DadosInvalidosException;
import com.thaynabds.contabanco.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaTest {

    @Test
    void abreContaComSaldoEmDuasCasas() {
        Conta conta = contaPadrao(new BigDecimal("237.4"));

        assertEquals("067-8", conta.agencia());
        assertEquals(1021, conta.numero());
        assertEquals("Mario Andrade", conta.cliente().nome());
        assertEquals(new BigDecimal("237.40"), conta.saldo());
    }

    @Test
    void rejeitaDadosObrigatoriosInvalidos() {
        Cliente cliente = new Cliente("Ana Silva");

        assertThrows(DadosInvalidosException.class,
                () -> new Conta(0, "067-8", cliente, BigDecimal.TEN));
        assertThrows(DadosInvalidosException.class,
                () -> new Conta(10, "  ", cliente, BigDecimal.TEN));
        assertThrows(DadosInvalidosException.class,
                () -> new Conta(10, "067-8", null, BigDecimal.TEN));
        assertThrows(DadosInvalidosException.class,
                () -> new Conta(10, "067-8", cliente, new BigDecimal("-0.01")));
        assertThrows(DadosInvalidosException.class,
                () -> new Conta(10, "067-8", cliente, null));
        assertThrows(DadosInvalidosException.class,
                () -> new Conta(10, "067-8", cliente, new BigDecimal("10.555")));
    }

    @Test
    void creditaEdebitaMantendoDuasCasas() {
        Conta conta = contaPadrao(new BigDecimal("100"));

        conta.creditar(new BigDecimal("50.5"));
        conta.debitar(new BigDecimal("20"));

        assertEquals(new BigDecimal("130.50"), conta.saldo());
    }

    @Test
    void rejeitaMovimentoInvalidoESaqueSemSaldo() {
        Conta conta = contaPadrao(new BigDecimal("10.00"));

        assertThrows(DadosInvalidosException.class, () -> conta.creditar(BigDecimal.ZERO));
        assertThrows(DadosInvalidosException.class, () -> conta.debitar(new BigDecimal("-1")));
        assertThrows(DadosInvalidosException.class, () -> conta.debitar(null));
        assertThrows(SaldoInsuficienteException.class, () -> conta.debitar(new BigDecimal("10.01")));
        assertEquals(new BigDecimal("10.00"), conta.saldo());
    }

    @Test
    void normalizaNomeErejeitaNomeInvalido() {
        assertEquals("Ana Maria", new Cliente("  Ana   Maria  ").nome());
        assertThrows(DadosInvalidosException.class, () -> new Cliente(" "));
        assertThrows(DadosInvalidosException.class, () -> new Cliente("An"));
        assertThrows(DadosInvalidosException.class, () -> new Cliente("Ana 2"));
        assertThrows(DadosInvalidosException.class, () -> new Cliente(null));
    }

    private static Conta contaPadrao(BigDecimal saldo) {
        return new Conta(1021, " 067-8 ", new Cliente("Mario Andrade"), saldo);
    }
}
