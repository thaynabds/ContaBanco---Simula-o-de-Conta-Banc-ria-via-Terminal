package com.thaynabds.contabanco.controller;

import com.thaynabds.contabanco.model.Conta;

public final class MensagemAbertura {

    public String montar(Conta conta) {
        return "Olá "
                + conta.cliente().nome()
                + ", obrigado por criar uma conta em nosso banco, sua agência é "
                + conta.agencia()
                + ", conta "
                + conta.numero()
                + " e seu saldo "
                + conta.saldo().toPlainString()
                + " já está disponível para saque.";
    }
}
