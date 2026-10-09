package pt.ulusofona.aed.deisimdb;

public class Genero {
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Dados obtidos no ficheiro genres.csv
    private final int genreId;
    private final String genreName;
    // - - - - - - - - - - - - - - - - - - - - - - -



    public Genero(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }



    @Override
    public String toString() {
        return genreId + " | " + genreName;
    }
}
