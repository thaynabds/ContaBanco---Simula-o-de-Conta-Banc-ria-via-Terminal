package com.thaynabds.contabanco.exception;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(BigDecimal saldoAtual, BigDecimal valorSolicitado) {
        super("Saldo insuficiente para saque. Saldo disponível: "
                + saldoAtual.toPlainString()
                + ". Valor solicitado: "
                + valorSolicitado.toPlainString()
                + ".");
    }
}
