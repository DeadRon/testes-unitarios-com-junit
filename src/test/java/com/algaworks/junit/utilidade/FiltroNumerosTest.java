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
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_pares_Entao_deve_retornar_apenas_numeros_pares(){
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

    @Test
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_impares_Entao_deve_retornar_apenas_numeros_impares(){
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosPares = Arrays.asList(1, 3);
        List<Integer> resultadoFiltro = FiltroNumeros.numerosImpares(numeros);
        assertIterableEquals(numerosPares, resultadoFiltro, "Comparação de listas de números pares");

        assertArrayEquals(numerosPares.toArray(new Object[]{}), resultadoFiltro.toArray(new Object[]{}), "Comparação de listas de números pares");

    }
}