package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorEsperaTest {

    @Test
    //@Disabled("Não é mais aplicável") //esta anotação faz com o teste não seja executado

    /* faz com que o teste não execute DADA UMA CONDIÇÃO. Neste caso
       compara se as variáveis de ambientes possuir o mesmo valor, se sim, o teste executa.
       OBS: para este teste foi setada a variável(com valor de "PROD" de ambiente na IDE.
       Este teste não executa por isso.*/
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "DEV")
    public void deveEsperarEnaoDarTimeout(){
        Assumptions.assumeTrue("PROD".equals(System.getenv("ENV")),
                () -> "ABORTANDO TESTE: NÃO DEVE SER EXECUTADO EM PROD");
        /*Assumptions.assumeTrue faz com que o teste não execute DADA UMA CONDIÇÃO. Neste caso
        * compara se as variáveis de ambientes possuir o mesmo valor*/
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