package pt.ulusofona.aed.deisimdb;

public class JogadorQuizz {
    private final String nomeJogador;
    private int perguntasAcertadas;
    private long tempo;
    private int tentativa;

    public JogadorQuizz(String nomeJogador) {
        this.nomeJogador = nomeJogador;

        perguntasAcertadas = 0;
        tempo = 0;
        tentativa = 1;
    }



    public String getNomeJogador() {
        return nomeJogador;
    }

    public int getPerguntasAcertadas() {
        return perguntasAcertadas;
    }

    public long getTempo() {
        return tempo;
    }

    public void accTentativa() {
        tentativa++;
    }

    public void setPerguntasAcertadas(int perguntasAcertadas) {
        this.perguntasAcertadas = perguntasAcertadas;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }


    public String centralizar(String texto, int tamanho) {
        int espaco = tamanho - texto.length();
        int espacoEsq = espaco / 2;
        int espacosDir = espaco - espacoEsq;

        String resultado = "";

        for (int i = 0; i < espacoEsq; i++) {
            resultado += " ";
        }

        resultado += texto;

        for (int i = 0; i < espacosDir; i++) {
            resultado += " ";
        }

        return resultado;
    }


    @Override
    public String toString() {
        return centralizar(nomeJogador, 14) + centralizar(perguntasAcertadas+"", 16) + centralizar(tempo+"", 16) + centralizar(tentativa+"", 16);
    }
}
