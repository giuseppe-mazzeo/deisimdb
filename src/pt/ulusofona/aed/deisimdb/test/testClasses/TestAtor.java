package pt.ulusofona.aed.deisimdb.test.testClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.Ator;

public class TestAtor {
    Ator ator = new Ator(1,"Joao da Silva", "M", 1234);
    Ator atriz = new Ator(2,"Joana Pereira", "F", 1234);


    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Testar getters
    //
    @Test
    public void testGetActorId() {
        if (ator.getActorId() != 1 || atriz.getActorId() != 2) {
            Assertions.fail("\n\nErro ao testar getActorId()");
        }
    }

    @Test
    public void testGetActorName() {
        if (!ator.getActorName().equals("Joao da Silva") || !atriz.getActorName().equals("Joana Pereira")) {
            Assertions.fail("\n\nErro ao testar getActorName()");
        }
    }

    @Test
    public void testGetActorGender() {
        if (!ator.getActorGender().equals("Masculino") || !atriz.getActorGender().equals("Feminino")) {
            Assertions.fail("\n\nErro ao testar getActorGender()");
        }
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Testar toString
    @Test
    public void testToString() {
        if (!ator.toString().equals("1 | Joao da Silva | Masculino | 1234") ||
                !atriz.toString().equals("2 | Joana Pereira | Feminino | 1234")) {
            Assertions.fail("\n\nErro ao testar toString()");
        }
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
}
