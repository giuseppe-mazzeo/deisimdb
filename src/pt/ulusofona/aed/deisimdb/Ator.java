package pt.ulusofona.aed.deisimdb;

public class Ator {
    // - - - - - - - - - - - - - - - - - - - - - - -
    // Dados obtidos no ficheiro actors.csv
    private final int actorId;
    private final String actorName;
    private final String actorGender;
    private final int movieId;
    // - - - - - - - - - - - - - - - - - - - - - - -



    public Ator(int actorId, String actorName, String actorGender, int movieId) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorGender = (actorGender.equals("M")) ? "Masculino" : "Feminino";
        this.movieId = movieId;
    }



    // - - - - - - - - - - - - - - - - - - - - - - -
    // Funções getters
    public int getActorId() {
        return actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public String getActorGender() {
        return actorGender;
    }
    // - - - - - - - - - - - - - - - - - - - - - - -



    @Override
    public String toString() {
        return actorId + " | " + actorName + " | " + actorGender + " | " + movieId;
    }
}
