package com.thaynabds.contabanco;

import com.thaynabds.contabanco.controller.ContaController;
import com.thaynabds.contabanco.repository.ContaMemoriaRepository;
import com.thaynabds.contabanco.service.ContaService;

import java.util.Scanner;

public final class ContaTerminal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContaService servico = new ContaService(new ContaMemoriaRepository());
        new ContaController(servico, scanner, System.out).iniciar();
    }

    private ContaTerminal() {
    }
}
