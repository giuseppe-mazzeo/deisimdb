package pt.ulusofona.aed.deisimdb;

public class GeneroFilme {
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Dados obtidos no ficheiro genres_movies.csv
    private final int genreId;
    private final int movieId;
    // - - - - - - - - - - - - - - - - - - - - - - -
    private final String genreName;



    public GeneroFilme(int genreId, int movieId, String genreName) {
        this.genreId = genreId;
        this.movieId = movieId;
        this.genreName = genreName;
    }



    public String getGenreName() {
        return genreName;
    }



    @Override
    public String toString() {
        return genreId + " | " + genreName;
    }
}
