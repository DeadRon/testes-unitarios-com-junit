package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FiltroNumerosTest {

    @Test
    public void deve_retornar_numeros_Pares(){
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosPares = Arrays.asList(2, 4);
        List<Integer> resultadoFiltro = FiltroNumeros.numerosPares(numeros);
        assertIterableEquals(numerosPares, resultadoFiltro, "Comparação de listas de números pares");
        /*assertIterableEquals verifica CONTEÚDO e ORDEM da lista.
        Usa o método equals do próprio objeto para fazer a comparação,
        sem um método Equais implementado, a comparação não funciona*/
        assertArrayEquals(numerosPares.toArray(new Object[]{}), resultadoFiltro.toArray(new Object[]{}), "Comparação de listas de números pares");
        /*realiza a mesma ação do assertIterableEquals, mas é preciso converter as listas em arrays*/
    }


}