package pt.ulusofona.aed.deisimdb.test.testClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.GeneroFilme;

public class TestGeneroFilmes {
    GeneroFilme generoFilme = new GeneroFilme(1, 1, "GeneroFilme");


    @Test
    public void VerificarToString() {
        if (!generoFilme.toString().equals("1 | GeneroFilme")) {
            Assertions.fail("\n\nErro ao testar toString()");
        }
    }
}
