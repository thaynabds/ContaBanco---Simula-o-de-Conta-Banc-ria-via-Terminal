package com.thaynabds.contabanco.controller;

import com.thaynabds.contabanco.exception.DadosInvalidosException;
import com.thaynabds.contabanco.exception.SaldoInsuficienteException;
import com.thaynabds.contabanco.model.Conta;
import com.thaynabds.contabanco.service.ContaService;

import java.math.BigDecimal;
import java.util.Scanner;

public final class ContaController {

    private final ContaService servico;
    private final Scanner entrada;
    private final Appendable saida;
    private final MensagemAbertura mensagemAbertura;

    public ContaController(ContaService servico, Scanner entrada, Appendable saida) {
        this.servico = servico;
        this.entrada = entrada;
        this.saida = saida;
        this.mensagemAbertura = new MensagemAbertura();
    }

    public void iniciar() {
        String agencia = lerAgencia();
        int numero = lerNumeroConta();
        String nome = lerNome();
        BigDecimal saldo = lerValor("Por favor, digite o saldo:", true);
        try {
            Conta conta = servico.abrir(agencia, numero, nome, saldo);
            escrever(mensagemAbertura.montar(conta));
            operar(conta.numero());
        } catch (DadosInvalidosException excecao) {
            escrever(excecao.getMessage());
        }
    }

    private void operar(int numero) {
        boolean ativo = true;
        while (ativo) {
            escrever("1. Depositar");
            escrever("2. Sacar");
            escrever("3. Consultar saldo");
            escrever("4. Encerrar");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1 -> depositar(numero);
                case 2 -> sacar(numero);
                case 3 -> escrever("Saldo atual: " + servico.consultarSaldo(numero).toPlainString());
                case 4 -> ativo = false;
                default -> escrever("Opção inválida.");
            }
        }
    }

    private void depositar(int numero) {
        BigDecimal valor = lerValor("Informe o valor do depósito:", false);
        BigDecimal saldo = servico.depositar(numero, valor);
        escrever("Depósito realizado. Saldo atual: " + saldo.toPlainString());
    }

    private void sacar(int numero) {
        BigDecimal valor = lerValor("Informe o valor do saque:", false);
        try {
            BigDecimal saldo = servico.sacar(numero, valor);
            escrever("Saque realizado. Saldo atual: " + saldo.toPlainString());
        } catch (SaldoInsuficienteException excecao) {
            escrever(excecao.getMessage());
        }
    }

    private String lerAgencia() {
        while (true) {
            String agencia = lerLinha("Por favor, digite o número da Agência:").trim();
            if (!agencia.isBlank()) {
                return agencia;
            }
            escrever("A agência é obrigatória.");
        }
    }

    private int lerNumeroConta() {
        while (true) {
            String informado = lerLinha("Por favor, digite o número da Conta:").trim();
            try {
                int numero = Integer.parseInt(informado);
                if (numero > 0) {
                    return numero;
                }
                escrever("O número da conta deve ser maior que zero.");
            } catch (NumberFormatException excecao) {
                escrever("Informe um número de conta válido.");
            }
        }
    }

    private String lerNome() {
        while (true) {
            String nome = lerLinha("Por favor, digite o nome do Cliente:").trim();
            if (nome.isBlank()) {
                escrever("O nome do cliente é obrigatório.");
                continue;
            }
            if (nome.length() < 3) {
                escrever("O nome do cliente deve ter pelo menos 3 caracteres.");
                continue;
            }
            if (nome.chars().anyMatch(Character::isDigit)) {
                escrever("O nome do cliente não pode conter números.");
                continue;
            }
            return nome;
        }
    }

    private int lerOpcao() {
        while (true) {
            String informado = lerLinha("Escolha uma opção:").trim();
            try {
                return Integer.parseInt(informado);
            } catch (NumberFormatException excecao) {
                escrever("Opção inválida.");
            }
        }
    }

    private BigDecimal lerValor(String prompt, boolean permiteZero) {
        while (true) {
            String informado = lerLinha(prompt).trim().replace(',', '.');
            try {
                BigDecimal valor = new BigDecimal(informado);
                if (valor.scale() > 2) {
                    escrever("Informe o valor com no máximo duas casas decimais.");
                    continue;
                }
                if (permiteZero && valor.signum() < 0) {
                    escrever("O saldo inicial não pode ser negativo.");
                    continue;
                }
                if (!permiteZero && valor.signum() <= 0) {
                    escrever("O valor deve ser maior que zero.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException excecao) {
                escrever("Informe um valor válido. Exemplo: 237.48");
            }
        }
    }

    private String lerLinha(String prompt) {
        escrever(prompt);
        if (!entrada.hasNextLine()) {
            return "";
        }
        return entrada.nextLine();
    }

    private void escrever(String texto) {
        try {
            saida.append(texto).append(System.lineSeparator());
        } catch (java.io.IOException excecao) {
            throw new IllegalStateException("Falha ao escrever no terminal.", excecao);
        }
    }
}
