package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Testes do utilitário de saudação")
class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saudar com Bom dia")
    public void saudarComBomdiaApartir5h(){
        //Arrange
        int horaValida = 9;
        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);
        //Assert
        Assertions.assertEquals("Bom dia", saudacao, "comparação de saudação");
    }

    @Test
    @DisplayName("Deve saudar com Boa noite")
    public void saudarComBoaNoiteAntesDas5h(){
        //Arrange
        int horaValida = 4;
        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);
        //Assert
        Assertions.assertEquals("Boa noite", saudacao, "comparação de saudação");
    }

    @Test
    @DisplayName("Deve lançar exceção")
    public void deveLancarException(){
        //Arrange
        int horaInvalida = -100;
        Executable executable = () -> SaudacaoUtil.saudar(horaInvalida);
        //Act
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable, "lançar exceção");
        //Assert
        assertEquals("Hora inválida", e.getMessage(), "Mensagem da exceção");
    }

    @Test
    @DisplayName("Não deve lançar exceção")
    public void naoDeveLancarException(){
        //Arrange
        int horaValida = 0;
        //Act
        ThrowingSupplier<String> chamadaInvalidaDeMetodo = () -> SaudacaoUtil.saudar(horaValida);
        //Assert
        assertDoesNotThrow(chamadaInvalidaDeMetodo, "Não deve lançar uma exceção");
    }

    @Test
    @DisplayName("Deve lançar boa tarde")
    public void saudarDeveRetornarBoaTarde(){
        //Arrange
        int horaValida = 14;
        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);
        //Assert
        assertEquals("Boa tarde", saudacao, "Comparação de saudação de boa tarde");
    }

    @Test
    @DisplayName("Deve saudar com Boa noite")
    void saudarDeveRetornarBoaNoite(){
        //Arrange
        int horaValida = 23;
        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);
        //Assert
        assertEquals("Boa noite", saudacao, "Comparação de saudação de Boa noite");
    }
}