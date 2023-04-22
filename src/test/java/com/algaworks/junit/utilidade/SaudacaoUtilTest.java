package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SaudacaoUtilTest {

    @Test
    public void Dado_um_horario_matutino_Quando_saudar_Entao_deve_saudar_com_bom_dia(){
        //Arrange
        int horaValida = 9;
        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);
        //Assert
        //Assertions.assertEquals("Bom dia", saudacao, "comparação de saudação");
        org.assertj.core.api.Assertions.assertThat(saudacao)
                //.withFailMessage("Saudação incorreta")
                .isNotNull()
                .isNotBlank()
                .isEqualTo("Bom dia");
    }

    @Test
    public void Dado_um_horario_matutino_Quando_saudar_Entao_deve_saudar_com_bom_dia_com_AssertJ(){
        int horaValida = 9;
        String saudacao = SaudacaoUtil.saudar(horaValida).toUpperCase();
        String saudacaoCorreta = "Bom dia";

        org.assertj.core.api.Assertions.assertThat(saudacao)
                .as("Validando se a saudação é %s", saudacaoCorreta)
                .withFailMessage("Erro: Saudação incorreta! Resulta: %s", saudacao)
                .isEqualTo(saudacaoCorreta);
    }

    @Test
    public void Dado_horario_matuino_Quando_saudar_Entao_deve_saudar_com_bom_dia(){
        //Arrange
        int horaValida = 4;
        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);
        //Assert
        Assertions.assertEquals("Boa noite", saudacao, "comparação de saudação");
    }

    @Test
    public void Dado_um_horario_invalido_Quando_saudar_Entao_deve_lancar_illegalArgumentException(){
        //Arrange
        int horaInvalida = -100;
        //Executable executable = () -> SaudacaoUtil.saudar(horaInvalida);
        //Act
        //IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable, "lançar exceção");
        //Assert
        //assertEquals("Hora inválida", e.getMessage(), "Mensagem da exceção");

        //IllegalArgumentException e = org.assertj.core.api.Assertions.catchThrowableOfType(() -> SaudacaoUtil.saudar(horaInvalida), IllegalArgumentException.class);
        ///org.assertj.core.api.Assertions.assertThat(e).hasMessage("Hora inválida");
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> SaudacaoUtil.saudar(horaInvalida))
                //.hasRootCause(new IllegalArgumentException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hora inválida");
    }

    @Test
    public void Dado_horario_Vespertino_Quando_saudar_Entao_nao_deve_lançar_excecao(){
        //Arrange
        int horaValida = 0;
        //Act
        ThrowingSupplier<String> chamadaInvalidaDeMetodo = () -> SaudacaoUtil.saudar(horaValida);
        //Assert
        assertDoesNotThrow(chamadaInvalidaDeMetodo, "Não deve lançar uma exceção");
    }

    @Test
    public void Dado_horario_Vespertino_Quando_saudar_Entao_deve_saudar_com_boa_tarde(){
        //Arrange
        int horaValida = 14;
        //Act
        String saudacao = SaudacaoUtil.saudar(horaValida);
        //Assert
        assertEquals("Boa tarde", saudacao, "Comparação de saudação de boa tarde");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
    public void Dado_horario_matinal_Quando_saudar_Entao_deve_retornar_bom_dia(int hora){
        String saudacao = SaudacaoUtil.saudar(hora);
        Assertions.assertEquals("Bom dia", saudacao, "comparação de saudação");
    }

}