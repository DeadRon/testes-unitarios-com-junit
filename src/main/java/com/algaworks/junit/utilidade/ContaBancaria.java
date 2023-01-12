package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

public class ContaBancaria {

    private BigDecimal saldo;

    public ContaBancaria(BigDecimal saldo) {

        //TODO 1 - validar saldo: não pode ser nulo, caso seja, deve lançar uma IllegalArgumentException
        if(saldo == null)
            throw new IllegalArgumentException("Saldo não pode ser nulo");

        //TODO 2 - pode ser zero ou negativo
        this.saldo = saldo;
    }

    public BigDecimal saque(BigDecimal valor) {

        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        if(valor == null)
            throw new IllegalArgumentException("Valor do saque não pode ser nulo");
        if(valor.compareTo(BigDecimal.ZERO) == 0)
            throw new IllegalArgumentException("Valor de saque não pode ser de O");
        //TODO 2 - Deve subtrair o valor do saldo
        int minuendo = saldo.intValue();
        int subTraendo = valor.intValue();
        int diferenca = minuendo - subTraendo;
        saldo = saldo.subtract(valor);

        //TODO 3 - Se o saldo for insuficiente deve lançar uma RuntimeException
        if(saldo.compareTo(BigDecimal.ZERO) == -1)
            throw new RuntimeException("Saldo insuficiente para realizar operação de saque");

        return saldo();
    }

    public void deposito(BigDecimal valor) {
        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        if(valor == null)
            throw new RuntimeException("Valor para depósito não deve ser nulo");

        if(valor.compareTo(BigDecimal.ZERO) == 0)
            throw new RuntimeException("Não é possível fazer depósito no valor de 0");

        if(valor.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException("Não é possível fazer depósito com valores abaixo de 0");

        //TODO 2 - Deve adicionar o valor ao saldo
        saldo = saldo.add(valor);
    }

    public BigDecimal saldo() {

        //TODO 1 - retornar saldo
        return saldo;
    }
}
