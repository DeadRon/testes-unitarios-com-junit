package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.INCLUDE;

class MultiplicadorTest {

    @ParameterizedTest
    @EnumSource(value = Multiplicador.class, names = {"TRIPLO", "DOBRO"})
    void aplicarMultiplicador(Multiplicador mutiplicador) {
        assertNotNull(mutiplicador.aplicarMultiplicador(10.0));
    }

    @ParameterizedTest
    @EnumSource(value = Multiplicador.class)
    void aplicarMultiplicadorTodos(Multiplicador mutiplicador) {
        assertNotNull(mutiplicador.aplicarMultiplicador(10.0));
    }
}