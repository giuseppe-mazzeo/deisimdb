package pt.ulusofona.aed.deisimdb;

public class VotosFilme {
    private final int movieId;
    private final float movieRating;
    private final int movieRatingCount;



    public VotosFilme(int movieId, float movieRating, int movieRatingCount) {
        this.movieId = movieId;
        this.movieRating = movieRating;
        this.movieRatingCount = movieRatingCount;
    }



    public int getMovieId() {
        return movieId;
    }

    public float getMovieRating() {
        return movieRating;
    }

    public int getMovieRatingCount() {
        return movieRatingCount;
    }



    @Override
    public String toString() {
        return movieId + " | " + movieRating + " | " + movieRatingCount;
    }
}
