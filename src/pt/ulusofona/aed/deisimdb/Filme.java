package pt.ulusofona.aed.deisimdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Filme {
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Dados obtidos no ficheiro director.csv
    private final int movieId;
    private final String movieName;
    private final float movieDuration;
    private final long movieBudget;
    private final String movieReleaseDate;
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Dados obtidos em outros ficheiros
    private HashMap<Integer, String> idNomesAtores; // key - actorId ; value - actorName
    private HashMap<Integer, String> idNomesDiretores; // key - directorId ; value - directorName
    private final ArrayList<String> generosAssociados;
    private float movieVote;
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Guarda os dados separados no movieReleaseDate
    private final String day;
    private final String month;
    private final String year;
    private final int monthInt;
    private final int yearInt;
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Guarda uma String para ser usada na função toString
    private String toString;
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Guarda a informação de quantos atores masculinos e femininos têm no filme
    private int numAtoresMasculino;
    private int numAtoresFeminino;
    // - - - - - - - - - - - - - - - - - - - - - - -



    public Filme(int movieId, String movieName, float movieDuration, long movieBudget, String movieReleaseDate) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieDuration = movieDuration;
        this.movieBudget = movieBudget;
        this.movieReleaseDate = movieReleaseDate;

        idNomesAtores = new HashMap<>();
        idNomesDiretores = new HashMap<>();
        generosAssociados = new ArrayList<>();
        movieVote = 0;

        numAtoresMasculino = 0;
        numAtoresFeminino = 0;

        String[] separaData = this.movieReleaseDate.split("-");
        day = separaData[0];
        month = separaData[1];
        year = separaData[2];
        monthInt = Integer.parseInt(month);
        yearInt = Integer.parseInt(year);
    }



    // - - - - - - - - - - - - - - - - - - - - - - -
    // Funções getters
    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public float getMovieDuration() {
        return movieDuration;
    }

    public long getMovieBudget() {
        return movieBudget;
    }

    public String getMovieReleaseOnlyDay() {
        return day;
    }

    public String getMovieReleaseOnlyMonth() {
        return month;
    }

    public int getMovieReleaseOnlyMonthInt() {
        return monthInt;
    }

    public String getMovieReleaseOnlyYear() {
        return year;
    }

    public int getMovieReleaseOnlyYearInt() {
        return yearInt;
    }

    public float getMovieVote() {
        return movieVote;
    }

    public HashSet<Integer> getAllActorsId() {
        return new HashSet<>(idNomesAtores.keySet());
    }

    public HashSet<String> getAllActorsName() {
        return new HashSet<>(idNomesAtores.values());
    }

    public int getQuantAllActors() {
        return (numAtoresMasculino+numAtoresFeminino);
    }

    public int getNumMaleActors() {
        return numAtoresMasculino;
    }

    public int getNumFemaleActors() {
        return numAtoresFeminino;
    }

    public HashSet<String> getAllDirectorsName() {
        return new HashSet<>(idNomesDiretores.values());
    }

    public ArrayList<String> getGenerosAssociados() {
        return generosAssociados;
    }
    // - - - - - - - - - - - - - - - - - - - - - - -



    // - - - - - - - - - - - - - - - - - - - - - - -
    // Funções setters
    public void setAtoresAssociados(HashSet<Ator> atores) {
        int numMasculino = 0;
        int numFeminino = 0;
        HashMap<Integer, String> idNomesAtores = new HashMap<>();

        for (Ator ator : atores) {
            idNomesAtores.put(ator.getActorId(), ator.getActorName());

            if (ator.getActorGender().equals("Masculino")) {
                numMasculino++;
            } else {
                numFeminino++;
            }
        }

        this.numAtoresMasculino = numMasculino;
        this.numAtoresFeminino = numFeminino;
        this.idNomesAtores = idNomesAtores;
    }

    public void setDiretoresAssociados(HashSet<Diretor> diretores) {
        HashMap<Integer, String> idNomesDiretores = new HashMap<>();

        for (Diretor diretor : diretores) {
            idNomesDiretores.put(diretor.getDirectorId(), diretor.getDirectorName());
        }

        this.idNomesDiretores = idNomesDiretores;
    }

    public void setGenerosAssociados(HashSet<String> generos) {
        this.generosAssociados.addAll(generos);
    }

    public void setMovieVote(Float movieVote) {
        this.movieVote = movieVote;
    }
    // - - - - - - - - - - - - - - - - - - - - - - -



    // - - - - - - - - - - - - - - - - - - - - - - -
    // Verifica quando o movieId é maior do que 1000
    public void verificarIdMaior1000() {
        String generos, diretores;
        ArrayList<String> nomesDiretores = new ArrayList<>();

        if (100 < movieId && movieId < 200) {
            double porcentagemFeminina = 0;

            if (numAtoresFeminino == 0) {
                porcentagemFeminina = 0.0;
            } else {
                if (numAtoresMasculino == 0) {
                    porcentagemFeminina = 100.0;
                } else {
                    porcentagemFeminina = 100.0 - Math.round((Math.max(numAtoresMasculino, numAtoresFeminino) * 100.0) / (numAtoresMasculino + numAtoresFeminino));
                }
            }

            toString = movieId + " | " + movieName + " | " + year + " | " + porcentagemFeminina;
            return;
        } else if (movieId < 1000) {
            Collections.sort(generosAssociados);
            generos = String.join(",", generosAssociados);

            nomesDiretores.addAll(idNomesDiretores.values());
            Collections.sort(nomesDiretores);
            diretores = String.join(",", nomesDiretores);
        } else {
            generos = generosAssociados.size() + "";
            diretores = idNomesDiretores.size() + "";
        }

        toString = movieId + " | " + movieName + " | " + year + "-" + month + "-" + day + " | " + generos + " | " + diretores + " | " + numAtoresMasculino + " | " + numAtoresFeminino;
    }
    // - - - - - - - - - - - - - - - - - - - - - - -



    // - - - - - - - - - - - - - - - - - - - - - - -
    // Funções que adicionam novos elementos (autores ou diretores)
    // São usadas no comando de INSERT_ACTOR e INSERT_DIRECTOR
    public void adicionarNovoAtor(int actorId, String actorName, String genderActor) {
        idNomesAtores.put(actorId, actorName);

        if (genderActor.equals("M")) {
            numAtoresMasculino++;
        } else {
            numAtoresFeminino++;
        }

        verificarIdMaior1000();
    }

    public void adicionarNovoDiretor(int directorId, String directorName) {
        idNomesDiretores.put(directorId, directorName);

        verificarIdMaior1000();
    }
    // - - - - - - - - - - - - - - - - - - - - - - -



    @Override
    public String toString() {
        return toString;
    }
}
