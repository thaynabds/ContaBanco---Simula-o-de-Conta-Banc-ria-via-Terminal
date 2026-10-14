# ContaBanco

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://docs.oracle.com/en/java/javase/17/)
[![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/docs/current/user-guide/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

Simulação de abertura de conta bancária pelo terminal. O programa pede agência, número da conta, nome e saldo, mostra a mensagem do desafio e segue com depósito, saque e consulta de saldo.

Este projeto faz parte do módulo de Sintaxe Básica Java da Trilha Java Básico da [Digital Innovation One (DIO)](https://www.dio.me/).

## Requisito do desafio

1. Solicitar no terminal:
   - número da agência (`String`)
   - número da conta (`int`)
   - nome do cliente (`String`)
   - saldo inicial (`double` no enunciado, `BigDecimal` nesta versão)
2. Exibir:

```text
Olá [Nome Cliente], obrigado por criar uma conta em nosso banco, sua agência é [Agencia], conta [Numero] e seu saldo [Saldo] já está disponível para saque.
```

## O que esta versão acrescenta

- Entrada inválida pede o dado de novo, sem encerrar o programa.
- Nome completo, com mais de uma palavra.
- Vírgula ou ponto no valor (`237,48` ou `237.48`).
- Depósito, saque e consulta de saldo depois da abertura.
- Saque maior que o saldo é recusado e o saldo permanece o mesmo.
- Dinheiro com duas casas decimais, usando [`BigDecimal`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html).
- Código separado em controller, service, repository e model.
- Testes com JUnit 5.

A leitura usa linha inteira, não `Scanner.next()`, para aceitar nome e sobrenome. O [`Scanner`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html) do enunciado, com `Locale` dos Estados Unidos, existe para o ponto ser o separador decimal. Aqui a conversão da vírgula é feita antes do `BigDecimal`.

## Como executar

Requisito: Java 17 ou superior.

```bash
./mvnw test
./mvnw -q exec:java
```

Se o Maven já estiver instalado:

```bash
mvn test
mvn -q exec:java
```

Exemplo de uso:

```text
Por favor, digite o número da Agência:
067-8
Por favor, digite o número da Conta:
1021
Por favor, digite o nome do Cliente:
Mario Andrade
Por favor, digite o saldo:
237.48
Olá Mario Andrade, obrigado por criar uma conta em nosso banco, sua agência é 067-8, conta 1021 e seu saldo 237.48 já está disponível para saque.
```

Depois disso o menu oferece depositar, sacar, consultar saldo ou encerrar.

## Regras

- Agência obrigatória.
- Número da conta maior que zero.
- Nome obrigatório, com pelo menos 3 caracteres e sem dígitos.
- Saldo inicial maior ou igual a zero, com no máximo duas casas decimais.
- Depósito e saque maiores que zero.
- Saque permitido só até o saldo disponível.
- Não é possível abrir duas contas com o mesmo número na mesma execução.

## Estrutura

```text
src/main/java/com/thaynabds/contabanco/
├── ContaTerminal.java
├── controller/
│   ├── ContaController.java
│   └── MensagemAbertura.java
├── service/
│   └── ContaService.java
├── repository/
│   ├── ContaRepository.java
│   └── ContaMemoriaRepository.java
├── model/
│   ├── Cliente.java
│   └── Conta.java
└── exception/
    ├── DadosInvalidosException.java
    └── SaldoInsuficienteException.java
```

`ContaTerminal` só inicia o programa. O controller lê e escreve no terminal. O service aplica as regras e o repository guarda a conta em memória durante a execução. Não há banco de dados porque o desafio é uma simulação de console.

## Testes

```bash
./mvnw test
```

O relatório de cobertura fica em `target/site/jacoco/index.html`. A verificação do build exige pelo menos 80% das linhas cobertas, fora a classe `ContaTerminal`, que apenas liga as outras classes.

## Licença

MIT. Veja o arquivo [LICENSE](LICENSE).
