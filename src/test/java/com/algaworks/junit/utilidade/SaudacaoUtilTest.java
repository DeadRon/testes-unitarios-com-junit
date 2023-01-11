package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudar(){
        String saudacao = SaudacaoUtil.saudar(9);
        Assertions.assertEquals("Bom dia", saudacao, "comparação de saudação");
    }

    @Test
    public void deveLancarException(){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(-100), "lançar exceção");
        assertEquals("Hora inválida", illegalArgumentException.getMessage(), "Mensagem da exceção");
    }

    @Test
    public void naoDeveLancarException(){
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(0), "Não deve lançar uma exceção");
    }

    @Test
    public void saudarDeveRetornarBoaTarde(){
        String saudacao = SaudacaoUtil.saudar(14);
        assertEquals("Boa tarde", saudacao, "Comparação de saudação de boa tarde");
    }

    @Test
    void saudarDeveRetornarBoaNoite(){
        String saudacao = SaudacaoUtil.saudar(23);
        assertEquals("Boa noite", saudacao, "Comparação de saudação de Boa noite");
    }
}