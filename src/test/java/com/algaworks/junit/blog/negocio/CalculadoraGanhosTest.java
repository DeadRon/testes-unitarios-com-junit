package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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

    @Test
    public void Dado_um_post_e_um_autor_Quando_calcular_ganhos_Entao_deve_retornar_quantidade_de_palavras_no_post(){
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(7, ganhos.getQuantidadePalavras());
    }

    @Test
    public void Dado_um_post_e_um_autor_Quando_calcular_ganhos_Entao_deve_retornar_valor_pago_por_palavra_do_autor(){
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    public void Dado_um_post_e_um_autor_nao_premiun_Quando_calcular_ganhos_Entao_deve_retornar_valor_sem_bonus(){
        autor.setPremium(false);
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

}