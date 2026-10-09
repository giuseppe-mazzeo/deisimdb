package pt.ulusofona.aed.deisimdb.test.testClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.Genero;

public class TestGenero {
    Genero genero = new Genero(1, "Ficção Científica");


    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Testar toString
    @Test
    public void testToString() {
        if (!genero.toString().equals("1 | Ficção Científica")) {
            Assertions.fail("\n\nErro ao testar toString()");
        }
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
}
