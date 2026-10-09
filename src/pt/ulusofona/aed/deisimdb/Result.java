package pt.ulusofona.aed.deisimdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Result {
    public boolean success;
    public String error;
    public String result;



    public Result() {}



    public void verificarComando(ArrayList<String> conteudo) {
        if (conteudo.isEmpty()) {
            comandoNaoEncontrouResultado();
        } else {
            comandoCorreto(conteudo);
        }
    }

    public void verificarComando(String conteudo) {
        if (conteudo.isEmpty()) {
            comandoNaoEncontrouResultado();
        } else {
            comandoCorreto(conteudo);
        }
    }

    // Usado apenas no comando DISTANCE_BETWEEN_ACTORS.
    public void verificarComando(int distancia) {
        if (distancia != -1) {
            comandoCorreto(distancia);
        } else {
            comandoNaoEncontrouResultado();
        }
    }

    public void verificarComandoInsercao(boolean insercaoValida) {
        if (insercaoValida) {
            comandoInsercaoCorreto();
        } else {
            comandoInsercaoInvalido();
        }
    }



    public void comandoInvalido() {
        success = false;
        error = "Comando invalido";
        result = null;
    }

    public void comandoCorreto(String resultado) {
        success = true;
        error = null;
        this.result = resultado;
    }

    public void comandoCorreto(int resultado) {
        comandoCorreto(resultado + "");
    }

    public void comandoCorreto(ArrayList<String> resultado) {
        comandoCorreto(String.join("\n", resultado));
    }

    public void comandoNaoEncontrouResultado() {
        success = true;
        error = null;
        result = "No results";
    }

    private void comandoInsercaoCorreto() {
        success = true;
        error = null;
        result = "OK";
    }

    public void comandoInsercaoInvalido() {
        success = false;
        error = null;
        result = "Erro";
    }


    public boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public String getResult() {
        return result;
    }



    @Override
    public String toString() {
        return "Sucesso: " + success + "\nError: " + error + "\nResultado: " + result;
    }
}
