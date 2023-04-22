package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;

public class CadastroPostTestData {

    public static Ganhos.Builder ganhos(){
        return Ganhos.builder()
                .valorPagoPorPalavra(ONE)
                .quantidadePalavras(2000)
                .totalGanho(BigDecimal.valueOf(2000L));
    }

    public static Editor.Builder editor(){
        return Editor.builder()
                .comNome("Ronaldo")
                .comEmail("ronaldo@gmail.com")
                .comvalorPagoPorPalavra(BigDecimal.valueOf(2000))
                .comPremium(false);
    }

    public static Post.Builder post(){
        return Post.builder()
                .id(null)
                .titulo("Algo sobre a vida")
                .conteudo("Reflexões")
                .autor(editor().build())
                .slug("algo")
                .ganhos(ganhos().build())
                .pago(true)
                .publicado(true);
    }

    public static Post.Builder postSalvo(){
        return post()
                .id(1L);
    }

    public static Post.Builder postInexistente(){
        return post()
                .id(10L);
    }

    public static Post.Builder postNaoPagoENaoPublicado(){
        return post()
                .pago(false)
                .publicado(false);
    }

    public static Post.Builder postPublicadoENaoPago(){
        return post()
                .pago(false)
                .publicado(true);
    }

    public static Post.Builder postPagoENaoPublicado(){
        return post()
                .pago(true)
                .publicado(false);
    }

}