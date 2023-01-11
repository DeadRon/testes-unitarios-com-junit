package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorEsperaTest {

    @Test
    @Disabled("não é mais aplicável") //esta anotação faz com o teste não seja executado
    public void deveEsperarEnaoDarTimeout(){
        assertTimeout(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofSeconds(10)));
        /*assertTimeout assegura que o método testado deve ser executado em um segundo. Mesmo que o
        * método execute de forma correta o teste irá invalidar devido ao tempo necessário para executar.
        * No exemplo leva 10 segundos para executar, o teste falha porque a condição de validação é 1 segundo*/
    }

    @Test
    public void deveEsperarEnaoDarTimeoutPreventivamente(){
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofMillis(100)));
        /* assertTimeoutPreemptively faz o mesmo que assertTimeout, porém apresenta falha caso o método não execute no tempo estipulado
        * diferente do método assertTimeout que iria esperar para validar apenas quando o terminar sua exevução. */
    }

}