package com.thaynabds.contabanco.controller;

import com.thaynabds.contabanco.repository.ContaMemoriaRepository;
import com.thaynabds.contabanco.service.ContaService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ContaControllerTest {

    @Test
    void cadastraContaEexecutaMovimentosComEntradaInvalida() {
        String entrada = String.join("\n",
                "",
                "067-8",
                "abc",
                "0",
                "1021",
                "",
                "An",
                "Ana2",
                "Mario Andrade",
                "-1",
                "10.555",
                "abc",
                "237,48",
                "x",
                "1",
                "0",
                "50",
                "2",
                "300",
                "2",
                "20",
                "3",
                "4"
        ) + "\n";

        String saida = executar(entrada);

        assertTrue(saida.contains("A agência é obrigatória."));
        assertTrue(saida.contains("Informe um número de conta válido."));
        assertTrue(saida.contains("O número da conta deve ser maior que zero."));
        assertTrue(saida.contains("O nome do cliente é obrigatório."));
        assertTrue(saida.contains("O nome do cliente deve ter pelo menos 3 caracteres."));
        assertTrue(saida.contains("O nome do cliente não pode conter números."));
        assertTrue(saida.contains("O saldo inicial não pode ser negativo."));
        assertTrue(saida.contains("Informe o valor com no máximo duas casas decimais."));
        assertTrue(saida.contains("Informe um valor válido. Exemplo: 237.48"));
        assertTrue(saida.contains(
                "Olá Mario Andrade, obrigado por criar uma conta em nosso banco, sua agência é 067-8, conta 1021 e seu saldo 237.48 já está disponível para saque."
        ));
        assertTrue(saida.contains("Opção inválida."));
        assertTrue(saida.contains("O valor deve ser maior que zero."));
        assertTrue(saida.contains("Depósito realizado. Saldo atual: 287.48"));
        assertTrue(saida.contains("Saldo insuficiente para saque. Saldo disponível: 287.48. Valor solicitado: 300.00."));
        assertTrue(saida.contains("Saque realizado. Saldo atual: 267.48"));
        assertTrue(saida.contains("Saldo atual: 267.48"));
    }

    private static String executar(String entrada) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)));
        StringBuilder saida = new StringBuilder();
        ContaService servico = new ContaService(new ContaMemoriaRepository());
        new ContaController(servico, scanner, saida).iniciar();
        return saida.toString();
    }
}
