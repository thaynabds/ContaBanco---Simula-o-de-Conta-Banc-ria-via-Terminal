package com.thaynabds.contabanco.controller;

import com.thaynabds.contabanco.model.Cliente;
import com.thaynabds.contabanco.model.Conta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MensagemAberturaTest {

    @Test
    void montaMensagemExigidaPeloDesafio() {
        Conta conta = new Conta(1021, "067-8", new Cliente("Mario Andrade"), new BigDecimal("237.48"));
        String mensagem = new MensagemAbertura().montar(conta);

        assertEquals(
                "Olá Mario Andrade, obrigado por criar uma conta em nosso banco, sua agência é 067-8, conta 1021 e seu saldo 237.48 já está disponível para saque.",
                mensagem
        );
    }
}
