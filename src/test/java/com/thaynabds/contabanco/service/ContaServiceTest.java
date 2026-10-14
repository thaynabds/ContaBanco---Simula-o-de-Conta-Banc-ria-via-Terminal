package com.thaynabds.contabanco.service;

import com.thaynabds.contabanco.exception.DadosInvalidosException;
import com.thaynabds.contabanco.exception.SaldoInsuficienteException;
import com.thaynabds.contabanco.model.Conta;
import com.thaynabds.contabanco.repository.ContaMemoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaServiceTest {

    private ContaService servico;

    @BeforeEach
    void setUp() {
        servico = new ContaService(new ContaMemoriaRepository());
    }

    @Test
    void abreDepositaSacaEconsulta() {
        Conta conta = servico.abrir("067-8", 1021, "Mario Andrade", new BigDecimal("237.48"));

        assertEquals(1021, conta.numero());
        assertEquals(new BigDecimal("287.48"), servico.depositar(1021, new BigDecimal("50")));
        assertEquals(new BigDecimal("267.48"), servico.sacar(1021, new BigDecimal("20")));
        assertEquals(new BigDecimal("267.48"), servico.consultarSaldo(1021));
    }

    @Test
    void impedeContaDuplicadaEcontaInexistente() {
        servico.abrir("067-8", 1021, "Mario Andrade", BigDecimal.ZERO);

        assertThrows(DadosInvalidosException.class,
                () -> servico.abrir("1234", 1021, "Ana Silva", BigDecimal.ONE));
        assertThrows(DadosInvalidosException.class,
                () -> servico.depositar(999, BigDecimal.ONE));
        assertThrows(DadosInvalidosException.class,
                () -> servico.sacar(999, BigDecimal.ONE));
        assertThrows(DadosInvalidosException.class, () -> servico.consultarSaldo(999));
    }

    @Test
    void saqueAcimaDoSaldoNaoAlteraOSaldo() {
        servico.abrir("067-8", 1021, "Mario Andrade", new BigDecimal("10.00"));

        assertThrows(SaldoInsuficienteException.class,
                () -> servico.sacar(1021, new BigDecimal("10.01")));
        assertEquals(new BigDecimal("10.00"), servico.consultarSaldo(1021));
    }

    @Test
    void repositorioObrigatorio() {
        assertThrows(DadosInvalidosException.class, () -> new ContaService(null));
    }
}
