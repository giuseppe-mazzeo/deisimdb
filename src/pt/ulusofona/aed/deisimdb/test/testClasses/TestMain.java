package pt.ulusofona.aed.deisimdb.test.testClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //
    // Testes para a função parseFiles()
    //
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    @Test
    public void PastaNaoExiste() {
        boolean resultadoEsperado = false;
        boolean resultadoAtual = Main.parseFiles(new File("abacate"));
        Assertions.assertEquals(resultadoEsperado, resultadoAtual, "\n\n-> A pasta deveria não existir.");
    }


    @Test
    public void PastaVazia() {
        boolean resultadoEsperado = false;
        boolean resultadoAtual = Main.parseFiles(new File("test-files/pasta-vazia"));
        Assertions.assertEquals(resultadoEsperado, resultadoAtual, "\n\n-> ParseFiles não deveria ler uma pasta vazia.");
    }


    @Test
    public void PastaLeituraCorreta() {
        boolean resultadoEsperado = true;
        boolean resultadoAtual = Main.parseFiles(new File("test-files"));
        Assertions.assertEquals(resultadoEsperado, resultadoAtual, "\n\n-> Leitura incorreta dos ficheiros.");
    }


    @Test
    public void VariasChamadasDeParseFiles() {
        boolean resultadoEsperado = true;
        boolean resultadoAtual;

        Main.parseFiles(new File("test-files"));

        int listaAtoresEsperado = Main.listaAtores.size();
        int listaDiretoresEsperado = Main.listaDiretores.size();
        int listaGeneroFilmesEsperado = Main.listaGeneroFilmes.size();
        int listaFilmesEsperado = Main.listaFilmes.size();
        int listaVotosFilmesEsperado = Main.listaVotosFilmes.size();
        int listaGenerosEsperado = Main.listaGeneros.size();
        String linhaOKEsperado = Arrays.toString(Main.linhaOK);
        String linhaNOKEsperado = Arrays.toString(Main.linhaNOK);
        String primeiraLinhaErradaEsperado = Arrays.toString(Main.primeiraLinhaErrada);

        Main.parseFiles(new File("test-files"));
        Main.parseFiles(new File("test-files"));
        Main.parseFiles(new File("test-files"));
        Main.parseFiles(new File("test-files"));
        Main.parseFiles(new File("test-files"));
        Main.parseFiles(new File("test-files"));

        int listaAtoresAtual = Main.listaAtores.size();
        int listaDiretoresAtual = Main.listaDiretores.size();
        int listaGeneroFilmesAtual = Main.listaGeneroFilmes.size();
        int listaVotosFilmesAtual = Main.listaVotosFilmes.size();
        int listaFilmesAtual = Main.listaFilmes.size();
        int listaGenerosAtual = Main.listaGeneros.size();
        String linhaOKAtual = Arrays.toString(Main.linhaOK);
        String linhaNOKAtual = Arrays.toString(Main.linhaNOK);
        String  primeiraLinhaErradaAtual = Arrays.toString(Main.primeiraLinhaErrada);

        if (listaAtoresEsperado == listaAtoresAtual && listaDiretoresEsperado == listaDiretoresAtual &&
            listaGeneroFilmesEsperado == listaGeneroFilmesAtual && listaVotosFilmesEsperado == listaVotosFilmesAtual &&
            listaFilmesEsperado == listaFilmesAtual && listaGenerosEsperado == listaGenerosAtual &&
            linhaOKEsperado.equals(linhaOKAtual) && linhaNOKEsperado.equals(linhaNOKAtual) &&
            primeiraLinhaErradaEsperado.equals(primeiraLinhaErradaAtual)
        ) {
            resultadoAtual = true;
        } else {
            resultadoAtual = false;
        }

        Assertions.assertEquals(resultadoEsperado, resultadoAtual,
                "\n\n-> As variáveis globais devem ser reinicializadas quando parseFile é novamente chamado." +
                "\n\nlistaAtoresEsperado - " + listaAtoresEsperado + " | listaAtoresAtual - " + listaAtoresAtual +
                "\nlistaDiretoresEsperado - " + listaDiretoresEsperado + " | listaDiretoresAtual - " + listaDiretoresAtual +
                "\nlistaGeneroFilmesEsperado - " + listaGeneroFilmesEsperado + " | listaGeneroFilmesAtual - " + listaGeneroFilmesAtual +
                "\nlistaVotosFilmesEsperado - " + listaVotosFilmesEsperado + " | listaVotosFilmesAtual - " + listaVotosFilmesAtual +
                "\nlistaFilmesEsperado - " + listaFilmesEsperado + " | listaFilmesAtual - " + listaFilmesAtual +
                "\nlistaGenerosEsperado - " + listaGenerosEsperado + " | listaGenerosAtual - " + listaGenerosAtual +
                "\nlinhaOKEsperado - " + linhaOKEsperado + " | linhaOKAtual - " + linhaOKAtual +
                "\nlinhaNOKEsperado - " + linhaNOKEsperado + " | linhaNOKAtual - " + linhaNOKAtual +
                "\nprimeiraLinhaErradaEsperado - " + primeiraLinhaErradaEsperado + " | primeiraLinhaErradaAtual - " + primeiraLinhaErradaAtual);
    }


    @Test
    public void VerificarFilmesComIdsMaioresOuMenoresDoQue1000() {
        Main.parseFiles(new File("test-files"));

        List<Filme> resultadoAtual = Main.listaFilmes;

        Assertions.assertEquals("680 | Pulp Fiction | 1994-09-10 | Crime,Drama | Fantasma Tarantino,Joel Coen,Ola Ola,Ola Ola Ola,Quentin Tarantino | 2 | 1" ,resultadoAtual.get(0).toString());
        Assertions.assertEquals("278 | The Shawshank Redemption | 1994-09-23 | Crime,Drama | Frank Darabont | 3 | 1" ,resultadoAtual.get(1).toString());
        Assertions.assertEquals("27205 | Inception | 2010-07-14 | 3 | 1 | 3 | 0" ,resultadoAtual.get(2).toString());
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =






    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //
    // Testes para a função getObject()
    //
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    @Test
    public void TestarTipoEntidadeInputAtor() {
        Main.parseFiles(new File("test-files"));

        List<Ator> resultadoEsperado = Main.listaAtores;
        ArrayList resultadoAtual = Main.getObjects(TipoEntidade.ATOR);

        if (resultadoAtual == null) {
            Assertions.fail("\n\nArrayList do TipoEntidade.ATOR retornou null.");
        }

        for (int pos = 0; pos < resultadoEsperado.size(); pos++) {
            if (!resultadoEsperado.get(pos).equals(resultadoAtual.get(pos))) {
                Assertions.fail("\n\nDados errados na posição " + pos + ". Esperado - " + resultadoEsperado.get(pos) + ". Atual - " + resultadoAtual.get(pos));
            }
        }
    }


    @Test
    public void TestarTipoEntidadeInputFilme() {
        Main.parseFiles(new File("test-files"));

        List<Filme> resultadoEsperado = Main.listaFilmes;
        ArrayList resultadoAtual = Main.getObjects(TipoEntidade.FILME);

        if (resultadoAtual == null) {
            Assertions.fail("\n\nArrayList do TipoEntidade.FILME retornou null.");
        }

        for (int pos = 0; pos < resultadoEsperado.size(); pos++) {
            if (!resultadoEsperado.get(pos).equals(resultadoAtual.get(pos))) {
                Assertions.fail("\n\nDados errados na posição " + pos + ". Esperado - " + resultadoEsperado.get(pos) + ". Atual - " + resultadoAtual.get(pos));
            }
        }
    }


    @Test
    public void TestarTipoEntidadeRealizador() {
        Main.parseFiles(new File("test-files"));

        List<Diretor> resultadoEsperado = Main.listaDiretores;
        ArrayList resultadoAtual = Main.getObjects(TipoEntidade.REALIZADOR);

        if (resultadoAtual == null) {
            Assertions.fail("\n\nArrayList do TipoEntidade.REALIZADOR retornou null.");
        }

        for (int pos = 0; pos < resultadoEsperado.size(); pos++) {
            if (!resultadoEsperado.get(pos).equals(resultadoAtual.get(pos))) {
                Assertions.fail("\n\nDados errados na posição " + pos + ". Esperado - " + resultadoEsperado.get(pos) + ". Atual - " + resultadoAtual.get(pos));
            }
        }
    }


    @Test
    public void TestarTipoEntidadeGeneroCinematograficos() {
        Main.parseFiles(new File("test-files"));

        List<Genero> resultadoEsperado = Main.listaGeneros;
        ArrayList resultadoAtual = Main.getObjects(TipoEntidade.GENERO_CINEMATOGRAFICO);

        if (resultadoAtual == null) {
            Assertions.fail("\n\nArrayList do TipoEntidade.GENERO_CINEMATOGRAFICO retornou null.");
        }

        for (int pos = 0; pos < resultadoEsperado.size(); pos++) {
            if (!resultadoEsperado.get(pos).equals(resultadoAtual.get(pos))) {
                Assertions.fail("\n\nDados errados na posição " + pos + ". Esperado - " + resultadoEsperado.get(pos) + ". Atual - " + resultadoAtual.get(pos));
            }
        }
    }


    @Test
    public void TestarTipoEntidadeInputInvalido() {
        Main.parseFiles(new File("test-files"));

        ArrayList resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add("movies.csv | 12 | 0 | -1");
        resultadoEsperado.add("actors.csv | 41 | 0 | -1");
        resultadoEsperado.add("directors.csv | 17 | 0 | -1");
        resultadoEsperado.add("genres.csv | 20 | 2 | 4");
        resultadoEsperado.add("genres_movies.csv | 3 | 2 | 2");
        resultadoEsperado.add("movie_votes.csv | 4 | 2 | 2");

        ArrayList resultadoAtual = Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        if (resultadoAtual == null) {
            Assertions.fail("\n\nArrayList do TipoEntidade.INPUT_INVALIDO retornou null.");
        }

        for (int pos = 0; pos < resultadoEsperado.size(); pos++) {
            if (!resultadoEsperado.get(pos).equals(resultadoAtual.get(pos))) {
                Assertions.fail("\n\nDados errados na posição " + pos + ". Esperado - " + resultadoEsperado.get(pos) + ". Atual - " + resultadoAtual.get(pos));
            }
        }
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    @Test
    public void testCountMoviesMonthYear() {
        Result result = Main.execute("COUNT_MOVIES_MONTH_YEAR 09 1994");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("2", result.getResult());

        result = Main.execute("COUNT_MOVIES_MONTH_YEAR 07 2010");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("1", result.getResult());
    }


    @Test
    public void testCountMoviesDirector() {
        Result result = Main.execute("COUNT_MOVIES_DIRECTOR Robert Zemeckis");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("2", result.getResult());

        result = Main.execute("COUNT_MOVIES_DIRECTOR Quentin Tarantino");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("1", result.getResult());
    }


    @Test
    public void testCountActorsIn2Years() {
        Result result = Main.execute("COUNT_ACTORS_IN_2_YEARS 1994 2010");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("0", result.getResult());

        result = Main.execute("COUNT_ACTORS_IN_2_YEARS 1994 2000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("1", result.getResult());
    }


    @Test
    public void testCountMoviesBetweenYearsWithNActors() {
        Result result = Main.execute("COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS 1994 1996 3 5");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("0", result.getResult());

        result = Main.execute("COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS 1994 2009 0 4");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("2", result.getResult());
    }


    @Test
    public void testGetMoviesActorYear() {
        Result result = Main.execute("GET_MOVIES_ACTOR_YEAR 1994 John Travolta");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertArrayEquals(new String[] {
                "Forrest Gump",
                "Pulp Fiction",
                "The Shawshank Redemption"
        }, resultParts);

        result = Main.execute("GET_MOVIES_ACTOR_YEAR 2010 Christopher Nolan");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("No results", result.getResult());
    }


    @Test
    public void testGetMoviesWithActorContaining() {
        Result result = Main.execute("GET_MOVIES_WITH_ACTOR_CONTAINING a");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertArrayEquals(new String[] {
                "Alien",
                "Cast Away",
                "Forrest Gump",
                "Inception",
                "Interstellar",
                "Pulp Fiction",
                "Se7en",
                "Shutter Island",
                "The Dark Knight",
                "The Shawshank Redemption"
        }, resultParts);

        result = Main.execute("GET_MOVIES_WITH_ACTOR_CONTAINING Travolta");
        assertNotNull(result);
        assertTrue(result.success);
        resultParts = result.result.split("\n");
        assertEquals(new String[]{
                "Forrest Gump",
                "Pulp Fiction",
                "The Shawshank Redemption",
        }, resultParts);
    }


    @Test
    public void testGetTop4YearWithMoviesContaining() {
        Result result = Main.execute("GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING a");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertArrayEquals(new String[] {
                "1994:1",
                "2000:1",
                "2008:1",
                "2010:1"
        }, resultParts);

        result = Main.execute("GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING on");
        assertNotNull(result);
        assertTrue(result.success);
        resultParts = result.result.split("\n");
        assertArrayEquals(new String[]{
                "1994:2",
                "2010:1"
        }, resultParts);
    }


    @Test
    public void testGetActorsByDirector() {
        Result result = Main.execute("GET_ACTORS_BY_DIRECTOR 2 Christopher Nolan");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Christian Bale:2", result.result);

        result = Main.execute("GET_ACTORS_BY_DIRECTOR 1 Joel Coen");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertArrayEquals(new String[]{
                "Uma Thurman:1",
                "Samuel L. Jackson:1",
                "John Travolta:1"
        }, resultParts);
    }


    @Test
    public void testTopMonthMovieCount() {
        Result result = Main.execute("TOP_MONTH_MOVIE_COUNT 1994");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("9:2", result.result);

        result = Main.execute("TOP_MONTH_MOVIE_COUNT 2010");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("2:1", result.result);
    }


    @Test
    public void testTopVotedActors() {
        Result result = Main.execute("TOP_VOTED_ACTORS 2 2010");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertArrayEquals(new String[]{
                "Joseph Gordon-Levitt:8.8",
                "Elliot Page:8.8"
        }, resultParts);

        result = Main.execute("TOP_VOTED_ACTORS 3 1994");
        assertNotNull(result);
        assertTrue(result.success);
        resultParts = result.result.split("\n");
        assertArrayEquals(new String[]{
                "Morgan Freeman:9.3",
                "Tim Robbins:9.3",
                "Uma Thurman:9.1"
        }, resultParts);
    }


    @Test
    public void testTopMoviesWithGenderBias() {
        Result result = Main.execute("TOP_MOVIES_WITH_GENDER_BIAS 2 1994");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("No results", result.result);

        result = Main.execute("TOP_MOVIES_WITH_GENDER_BIAS 2 2020");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertArrayEquals(new String[]{
                "So Homens:F:71"
        }, resultParts);
    }

    @Test
    public void testTopMoviesWithGender() {
        Result result = Main.execute("TOP_MOVIES_WITH_GENDER_BIAS 2 1994");
        assertNotNull(result);
        assertTrue(result.success);
        String[] resultParts = result.result.split("\n");
        assertEquals(new String[]{
                "The Shawshank Redemption:3",
                "Forrest Gump:2",
                "Pulp Fiction:2"
        }, resultParts);

        result = Main.execute("TOP_MOVIES_WITH_MORE_GENDER 2 2010 F");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals(new String[]{
                "Inception:0",
                "Shutter Island:0"
        }, resultParts);
    }
}