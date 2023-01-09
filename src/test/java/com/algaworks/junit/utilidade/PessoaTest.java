package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    void assercaoAgrupada(){
        Pessoa pessoa = new Pessoa("Ronaldo", "Melo");

        assertAll("Asserções de Pessoa",
                () -> assertEquals("Ronaldo", pessoa.getNome()),
                () -> assertEquals("Melo", pessoa.getSobrenome()));

        /*Diferença entre usar o assertEquels dentro e fora do assertAll.
        * Fora do assertAll a execução do teste para quando o primeiro aseertEquals declarado falha.
        * Dentro do aseertAll todos os assertEqual declarados são executados e a falha individual de
        * um assertEquals não interrompe na execução das próximas asserções*/
    }

}