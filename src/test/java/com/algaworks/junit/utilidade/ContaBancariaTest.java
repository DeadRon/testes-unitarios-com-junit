package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class ContaBancariaTest {

    @Nested
    class Saque {

        @Test
        public void deveRealizarSaque(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            contaBancaria.saque(BigDecimal.valueOf(300));
            BigDecimal saldoNaConta = contaBancaria.saldo();
            assertEquals(BigDecimal.valueOf(700), saldoNaConta, "Valor presente na conta");
        }

        @Test
        public void deveLancarIllegalArgumentExceptionQuandoValorSaqueSerNulo(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            IllegalArgumentException valorNulolillegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(null), "Valida valor para realizar saque!");
            assertEquals("Valor do saque não pode ser nulo", valorNulolillegalArgumentException.getMessage());
        }

        @Test
        public void deveLancarIllegalArgumentExceptionQuandoValorSaqueSerZero(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            IllegalArgumentException valorNulolillegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.ZERO), "Valida valor para realizar saque!");
            assertEquals("Valor de saque não pode ser de O", valorNulolillegalArgumentException.getMessage());
        }
    }

    @Nested
    class Deposito{

        @Test
        public void deveRealizarDeposito(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            contaBancaria.deposito(BigDecimal.valueOf(10000));
            assertEquals(BigDecimal.valueOf(11000), contaBancaria.saldo(), "Novo saldo após depósito");
        }

        @Test
        public void deveLancarExceptionQuandoValorDoDepositoSerNulo(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            RuntimeException depositoComNulluntimeException = assertThrows(RuntimeException.class, () -> contaBancaria.deposito(null));
            assertEquals("Valor para depósito não deve ser nulo", depositoComNulluntimeException.getMessage(), "Não permite valor nulo no depóstio");
        }

        @Test
        public void deveLancarExceptionQuandoValorDoDepositoSerZero(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            RuntimeException depositoComZeroRuntimeException = assertThrows(RuntimeException.class, () -> contaBancaria.deposito(BigDecimal.ZERO));
            assertEquals("Não é possível fazer depósito no valor de 0", depositoComZeroRuntimeException.getMessage(), "Não permite valor zero no depóstio");
        }

        @Test
        public void deveLancarExceptionQuandoValorDoDepositoSerMenorQueZero(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            RuntimeException depositoComZeroRuntimeException = assertThrows(RuntimeException.class, () -> contaBancaria.deposito(BigDecimal.valueOf(-4500)));
            assertEquals("Não é possível fazer depósito com valores abaixo de 0", depositoComZeroRuntimeException.getMessage(), "Não permite valor menor que zero no depóstio");
        }
    }

    @Nested
    class Saldo {

        @Test
        public void lancaIllegalArgumentExceptionQuandoSaldoEstiverNulo(){
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new ContaBancaria(null), "Não permite saldo inicial com valor nulo");
            assertEquals(illegalArgumentException.getMessage(), "Saldo não pode ser nulo");
        }

        @Test
        public void deveRealizarSaque(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            contaBancaria.saque(BigDecimal.valueOf(300));
            BigDecimal saldoNaConta = contaBancaria.saldo();
            assertEquals(BigDecimal.valueOf(700), saldoNaConta, "Valor presente na conta");
        }

        @Test
        public void deveLancarIllegalArgumentExceptionQuandoValorSaqueSerNulo(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            IllegalArgumentException valorNulolillegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(null), "Valida valor para realizar saque!");
            assertEquals("Valor do saque não pode ser nulo", valorNulolillegalArgumentException.getMessage());
        }

        @Test
        public void deveLancarIllegalArgumentExceptionQuandoValorSaqueSerZero(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            IllegalArgumentException valorNulolillegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.ZERO), "Valida valor para realizar saque!");
            assertEquals("Valor de saque não pode ser de O", valorNulolillegalArgumentException.getMessage());
        }

        @Test
        public void deveLancarRuntimeExceptionQuandoSaldoEstiverComValorZero(){
            ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.valueOf(1000));
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> contaBancaria.saque(BigDecimal.valueOf(1100)), "Valida saldo restante após realizar saque");
            assertEquals("Saldo insuficiente para realizar operação de saque", runtimeException.getMessage());
        }

    }
}