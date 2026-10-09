package pt.ulusofona.aed.deisimdb.test.testClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.Diretor;

public class TestDiretor {
    Diretor diretor = new Diretor(1, "Carlos Olivais", 1234);
    Diretor diretora = new Diretor(10, "Yasmin Pudim", 780);


    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Testar getters
    //
    @Test
    public void testGetDirectorId() {
        if (diretor.getDirectorId() != 1 || diretora.getDirectorId() != 10) {
            Assertions.fail("\n\nErro ao testar getDirectorId()");
        }
    }

    @Test
    public void testGetDirectorName() {
        if (!diretor.getDirectorName().equals("Carlos Olivais") || !diretora.getDirectorName().equals("Yasmin Pudim")) {
            Assertions.fail("\n\nErro ao testar getDirectorName()");
        }
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =


    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Testar toString
    @Test
    public void VerificarToString() {
        if (!diretor.toString().equals("1 | Carlos Olivais | 1234") ||
                !diretora.toString().equals("10 | Yasmin Pudim | 780")) {
            Assertions.fail("\n\nErro ao testar toString()");
        }
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
}
