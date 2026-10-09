package pt.ulusofona.aed.deisimdb.test.testClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.Ator;
import pt.ulusofona.aed.deisimdb.Diretor;
import pt.ulusofona.aed.deisimdb.Filme;

import java.util.HashSet;

public class TestFilme {
    Filme filmeSonho = new Filme(1001, "O Sonho de um Programador", 60.0f, 1000, "31-02-1994");
    Filme filmePesadelo = new Filme(999, "O Pesadelo de um Programador", 30.0f, 100, "03-04-2005");



    private HashSet<Diretor> gerarDiretores() {
        filmeSonho = new Filme(1001, "O Sonho de um Programador", 60.0f, 1000, "31-02-1994");
        filmePesadelo = new Filme(999, "O Pesadelo de um Programador", 30.0f, 100, "03-04-2005");
        HashSet<Diretor> diretores = new HashSet<>();
        diretores.add(new Diretor(1, "João", 10));
        diretores.add(new Diretor(2, "Joana", 100));
        diretores.add(new Diretor(3, "Ana", 20));
        return diretores;
    }

    private HashSet<String> gerarNomesDiretores() {
        HashSet<String> nomesDiretores = new HashSet<>();
        nomesDiretores.add("João");
        nomesDiretores.add("Joana");
        nomesDiretores.add("Ana");
        return nomesDiretores;
    }

    private HashSet<Ator> gerarAtores() {
        filmeSonho = new Filme(1001, "O Sonho de um Programador", 60.0f, 1000, "31-02-1994");
        filmePesadelo = new Filme(999, "O Pesadelo de um Programador", 30.0f, 100, "03-04-2005");
        HashSet<Ator> atores = new HashSet<>();
        atores.add(new Ator(1, "Ator1", "M", 10));
        atores.add(new Ator(2, "Ator2", "M", 20));
        atores.add(new Ator(3, "Atriz1", "F", 10));
        return atores;
    }

    private HashSet<Integer> gerarIdAtores() {
        HashSet<Integer> idAtores = new HashSet<>();
        idAtores.add(1);
        idAtores.add(2);
        idAtores.add(3);
        return idAtores;
    }

    private HashSet<String> gerarNomesAtores() {
        HashSet<String> nomesAtores = new HashSet<>();
        nomesAtores.add("Ator1");
        nomesAtores.add("Ator2");
        nomesAtores.add("Atriz1");
        return nomesAtores;
    }



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Testar getters e setters
    //
    @Test
    public void testGetMovieId() {
        if (filmeSonho.getMovieId() != 1001 || filmePesadelo.getMovieId() != 999) {
            Assertions.fail("\n\nErro ao testar getMovieId()");
        }
    }

    @Test
    public void testGetMovieName() {
        if (!filmeSonho.getMovieName().equals("O Sonho de um Programador") || !filmePesadelo.getMovieName().equals("O Pesadelo de um Programador")) {
            Assertions.fail("\n\nErro ao testar getMovieName()");
        }
    }

    @Test
    public void testGetMovieReleaseOnlyDay() {
        if (!filmeSonho.getMovieReleaseOnlyDay().equals("31") || !filmePesadelo.getMovieReleaseOnlyDay().equals("03")) {
            Assertions.fail("\n\nErro ao testar getMovieReleaseOnlyDay()");
        }
    }

    @Test
    public void testGetMovieReleaseOnlyMonth() {
        if (!filmeSonho.getMovieReleaseOnlyMonth().equals("02") || !filmePesadelo.getMovieReleaseOnlyMonth().equals("04")) {
            Assertions.fail("\n\nErro ao testar getMovieReleaseOnlyMonth()");
        }
    }

    @Test
    public void testGetMovieReleaseOnlyYear() {
        if (!filmeSonho.getMovieReleaseOnlyYear().equals("1994") || !filmePesadelo.getMovieReleaseOnlyYear().equals("2005")) {
            Assertions.fail("\n\nErro ao testar getMovieReleaseOnlyYear()");
        }
    }

    @Test
    public void testGetMovieVoteESetMovieVote() {
        if (filmeSonho.getMovieVote() != 0.0 || filmePesadelo.getMovieVote() != 0.0) {
            Assertions.fail("\n\nErro ao testar getMovieVote()");
        }

        filmeSonho.setMovieVote(10.0f);
        filmePesadelo.setMovieVote(0.5f);

        if (filmeSonho.getMovieVote() != 10.0 || filmePesadelo.getMovieVote() != 0.5) {
            Assertions.fail("\n\nErro ao testar setMovieVote()");
        }
    }

    @Test
    public void testGetAllActorsId() {
        if (!filmeSonho.getAllActorsId().isEmpty() || !filmePesadelo.getAllActorsId().isEmpty()) {
            Assertions.fail("\n\nErro ao testar getAllActorsId()");
        }

        HashSet<Ator> atores = gerarAtores();
        HashSet<Integer> idAtores = gerarIdAtores();

        filmeSonho.setAtoresAssociados(atores);
        filmePesadelo.setAtoresAssociados(atores);

        if (!filmeSonho.getAllActorsId().containsAll(idAtores) || !filmePesadelo.getAllActorsId().containsAll(idAtores)) {
            Assertions.fail("\n\nErro ao testar setAtoresAssociados()");
        }
    }

    @Test
    public void testGetAllActorsName() {
        if (!filmeSonho.getAllActorsName().isEmpty() || !filmePesadelo.getAllActorsName().isEmpty()) {
            Assertions.fail("\n\nErro ao testar getAllActorsName()");
        }

        HashSet<Ator> atores = gerarAtores();
        HashSet<String> nomesAtores = gerarNomesAtores();

        filmeSonho.setAtoresAssociados(atores);
        filmePesadelo.setAtoresAssociados(atores);

        if (!filmeSonho.getAllActorsName().containsAll(nomesAtores) || !filmePesadelo.getAllActorsName().containsAll(nomesAtores)) {
            Assertions.fail("\n\nErro ao testar setAtoresAssociados()");
        }
    }

    @Test
    public void testGetQuantAllActors() {
        if (filmeSonho.getQuantAllActors() != 0 || filmePesadelo.getQuantAllActors() != 0) {
            Assertions.fail("\n\nErro ao testar getQuantAllActors()");
        }

        HashSet<Ator> atores = gerarAtores();

        filmeSonho.setAtoresAssociados(atores);
        filmePesadelo.setAtoresAssociados(atores);

        if (filmeSonho.getQuantAllActors() != 3 || filmePesadelo.getQuantAllActors() != 3) {
            Assertions.fail("\n\nErro ao testar setAtoresAssociados()");
        }
    }

    @Test
    public void testGetNumMaleActors() {
        if (filmeSonho.getNumMaleActors() != 0 || filmePesadelo.getNumMaleActors() != 0) {
            Assertions.fail("\n\nErro ao testar getNumMaleActors()");
        }

        HashSet<Ator> atores = gerarAtores();

        filmeSonho.setAtoresAssociados(atores);
        filmePesadelo.setAtoresAssociados(atores);

        if (filmeSonho.getNumMaleActors() != 2 || filmePesadelo.getNumMaleActors() != 2) {
            Assertions.fail("\n\nErro ao testar setAtoresAssociados()");
        }
    }

    @Test
    public void testGetNumFemaleActors() {
        if (filmeSonho.getNumFemaleActors() != 0 || filmePesadelo.getNumFemaleActors() != 0) {
            Assertions.fail("\n\nErro ao testar getNumFemaleActors()");
        }

        HashSet<Ator> atores = gerarAtores();

        filmeSonho.setAtoresAssociados(atores);
        filmePesadelo.setAtoresAssociados(atores);

        if (filmeSonho.getNumFemaleActors() != 1 || filmePesadelo.getNumFemaleActors() != 1) {
            Assertions.fail("\n\nErro ao testar setAtoresAssociados()");
        }
    }

    @Test
    public void testGetAllDirectorsName() {
        if (!filmeSonho.getAllDirectorsName().isEmpty() || !filmePesadelo.getAllDirectorsName().isEmpty()) {
            Assertions.fail("\n\nErro ao testar getAllDirectorsName()");
        }

        HashSet<Diretor> diretores = gerarDiretores();
        HashSet<String> nomesDiretores = gerarNomesDiretores();

        filmeSonho.setDiretoresAssociados(diretores);
        filmePesadelo.setDiretoresAssociados(diretores);

        if (!filmeSonho.getAllDirectorsName().containsAll(nomesDiretores) || !filmePesadelo.getAllDirectorsName().containsAll(nomesDiretores)) {
            Assertions.fail("\n\nErro ao testar setDiretoresAssociados()");
        }
    }


    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Testar toString
    @Test
    public void testToString() {
        if (filmeSonho.toString() != null || filmePesadelo.toString() != null) {
            Assertions.fail("\n\nErro ao testar toString(). Esperava null");
        }

        filmeSonho.verificarIdMaior1000();
        filmePesadelo.verificarIdMaior1000();

        if (!filmeSonho.toString().equals("1001 | O Sonho de um Programador | 1994-02-31 | 0 | 0 | 0 | 0") ||
            !filmePesadelo.toString().equals("999 | O Pesadelo de um Programador | 2005-04-03 |  |  | 0 | 0")) {
            Assertions.fail("\n\nErro ao testar toString(). Espera toString correto");
        }
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
}
