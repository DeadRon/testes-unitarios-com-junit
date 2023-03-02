package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraGanhosTest {

    static CalculadoraGanhos calculadora;
    static Editor autor;
    static Post post;

    @BeforeAll
    static void beforeAll(){
        calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
    }

    @BeforeEach
    void beforeEach(){
        autor = new Editor("Alex", "alex@gmail.com", new BigDecimal(5), true);
        post = new Post(1L, "Ecossitema Java", "O ecossistema do java é muito maduro", autor,
                "ecossistema-java-123", null, false, false);
    }

//    @AfterAll
//    static void afterAll(){
//        System.out.println("@AfterAll: Depois de todos os testes");
//    }
//
//    @AfterEach
//    void afterEach(){
//        System.out.println("@AfterEach: Depois de cada teste");
//    }

    @Test
    public void deveCalcularGanhos(){
        Ganhos ganhos = calculadora.calcular(post);

        assertEquals(new BigDecimal("45"), ganhos.getTotalGanho());
        assertEquals(7, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    public void deveCalcularGanhosSemPremium(){
        CalculadoraGanhos calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
        autor.setPremium(false);

        Ganhos ganhos = calculadora.calcular(post);

        assertEquals(new BigDecimal("35"), ganhos.getTotalGanho());
        assertEquals(7, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

}