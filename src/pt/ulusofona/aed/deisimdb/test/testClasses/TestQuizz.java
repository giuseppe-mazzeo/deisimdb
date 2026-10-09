package pt.ulusofona.aed.deisimdb.test.testClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.JogadorQuizz;
import pt.ulusofona.aed.deisimdb.Main;
import pt.ulusofona.aed.deisimdb.Quizz;

public class TestQuizz {
    @Test
    // Parâmetro 1: play (não tem como eu testar esse comando...)
    // Parâmtetro 2: history (irei testar duas vezes)
    public void testCreativeCommand() {
        Quizz quizz = new Quizz();

        String resultadoEsperado = """
                Nenhum histórico de jogador
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                     NOME    PERGUNTAS CERTAS    TEMPO (seg.)    TENTATIVA
                     ----         ---                ---            ---
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                """;
        String resultadoAtual = quizz.mostrarEstatisticaTodosJogadores();
        Assertions.assertEquals(resultadoEsperado, resultadoAtual);

        JogadorQuizz jogador = new JogadorQuizz("jogador1");
        jogador.setPerguntasAcertadas(3);
        jogador.setTempo(30);
        quizz.listaJogadores.add(jogador);

        jogador = new JogadorQuizz("jogador2");
        jogador.setPerguntasAcertadas(10);
        jogador.setTempo(40);
        quizz.listaJogadores.add(jogador);

        resultadoAtual = quizz.mostrarEstatisticaTodosJogadores();

        resultadoEsperado = """
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                     NOME    PERGUNTAS CERTAS    TEMPO (seg.)    TENTATIVA
                   jogador2          10              40              1       \s
                   jogador1          3               30              1       \s
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                """;

        Assertions.assertEquals(resultadoEsperado, resultadoAtual);
    }
}
