package pt.ulusofona.aed.deisimdb;

public class Diretor {
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Dados obtidos no ficheiro director.csv
    private final int directorId;
    private final String directorName;
    private final int movieId;
    // - - - - - - - - - - - - - - - - - - - - - - -



    public Diretor(int directorId, String directorName, int movieId) {
        this.directorId = directorId;
        this.directorName = directorName;
        this.movieId = movieId;
    }



    // - - - - - - - - - - - - - - - - - - - - - - -
    // Funções getters
    public int getMovieId() {
        return movieId;
    }

    public int getDirectorId() {
        return directorId;
    }

    public String getDirectorName() {
        return directorName;
    }
    // - - - - - - - - - - - - - - - - - - - - - - -



    @Override
    public String toString() {
        return directorId + " | " + directorName + " | " + movieId;
    }
}
