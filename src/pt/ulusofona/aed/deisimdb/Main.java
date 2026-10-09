package pt.ulusofona.aed.deisimdb;

import java.io.*;
import java.util.*;

public class Main {

    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Variáveis Globais (guarda todas as informações dos ficheiros que são lidos corretamente).
    //
    public static ArrayList<Ator> listaAtores = new ArrayList<>();
    public static ArrayList<Diretor> listaDiretores = new ArrayList<>();
    public static ArrayList<GeneroFilme> listaGeneroFilmes = new ArrayList<>();
    public static ArrayList<VotosFilme> listaVotosFilmes = new ArrayList<>();
    public static ArrayList<Filme> listaFilmes = new ArrayList<>();
    public static ArrayList<Genero> listaGeneros = new ArrayList<>();
    //
    public static int[] linhaOK;
    public static int[] linhaNOK;
    public static int[] primeiraLinhaErrada;
    //
    public static Quizz quizz = new Quizz();
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Função que lê ficheiros numa pasta e guarda as informações.
    // Retornou false ⇾ Pasta não foi encontrado, pasta vazia ou não existe nenhum ficheiro necessário para este Projeto.
    // Retornou true ⇾ Pasta existe, logo, leu os ficheiros.
    //
    public static boolean parseFiles(File pasta) {
        // Pasta não existe
        if (!pasta.exists()) {
            return false;
        }


        //long start = System.currentTimeMillis();
        //long end = System.currentTimeMillis();
        linhaOK = new int[6];
        linhaNOK = new int[6];
        primeiraLinhaErrada = new int[]{-1, -1, -1, -1, -1, -1};
        String linha;
        String[] dataLinha;
        BufferedReader reader;
        HashMap<Integer, HashSet<Ator>> movieIdActor = new HashMap<>(); // key - movieId ; value - atores relacionados ao filme
        HashMap<Integer, HashSet<Diretor>> movieIdDirector = new HashMap<>(); // key - movieId ; value - diretores relacionados ao filme
        HashMap<Integer, String> genreIdGenreName = new HashMap<>(); // key - genreId ; value - genreName
        HashMap<Integer, HashSet<String>> movieIdGenreName = new HashMap<>(); // key - movieId ; value - gêneros relacionados ao filme
        HashMap<Integer, Float> movieIdMovieVote = new HashMap<>(); // key - movieId ; value - movieVote


        //start = System.currentTimeMillis();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Ficheiro -> actors
        File ficheiroActors = new File(pasta, "actors.csv");
        //
        try {
            listaAtores.clear();
            reader = new BufferedReader(new FileReader(ficheiroActors));
            reader.readLine(); // Ignora a 1.ª linha (cabeçalho)

            Ator novoAtor;
            int actorId;
            String actorName;
            String actorGender;
            int movieId;

            while ((linha = reader.readLine()) != null) {
                dataLinha = linha.split(",");

                // Tem mais ou menos informações do que deveria
                if (dataLinha.length != 4) {
                    acrescentarLinhaNOK(0);
                    continue;
                }

                actorId = Integer.parseInt(dataLinha[0].trim());
                actorName = dataLinha[1].trim();
                actorGender = dataLinha[2].trim();
                movieId = Integer.parseInt(dataLinha[3].trim());
                novoAtor = new Ator(actorId, actorName, actorGender, movieId);

                // Novo código da parte 2.
                // - - -
                // Verifico se já foi "encontrado" um movieId.
                // Se sim ⇾ adiociono um novo Ator relacionado com o movieId.
                // Se não ⇾ crio um novo elemento com o movieId.
                if (!movieIdActor.containsKey(movieId)) {
                    movieIdActor.put(movieId, new HashSet<>());
                }
                movieIdActor.get(movieId).add(novoAtor);
                // - - -

                listaAtores.add(novoAtor);
                linhaOK[0]++;
            }

            reader.close();
        } catch (FileNotFoundException e) {
            // Ficheiro não existe
            return false;
        } catch (IOException e) {
            // Ficheiro vazio ou não tem linhas
            return false;
        }
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //end = System.currentTimeMillis();
        //System.out.println("Ficheiro Atores " + (end - start) + "ms");


        //start = System.currentTimeMillis();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Ficheiro -> directors
        File ficheiroDirectors = new File(pasta, "directors.csv");
        //
        try {
            listaDiretores.clear();
            reader = new BufferedReader(new FileReader(ficheiroDirectors));
            reader.readLine(); // Ignora a 1.ª linha (cabeçalho)

            Diretor novoDiretor;
            int directorId;
            String directorName;
            int movieId;

            while ((linha = reader.readLine()) != null) {
                dataLinha = linha.split(",");

                // Tem mais ou menos informações do que deveria
                if (dataLinha.length != 3) {
                    acrescentarLinhaNOK(1);
                    continue;
                }

                directorId = Integer.parseInt(dataLinha[0].trim());
                directorName = dataLinha[1].trim();
                movieId = Integer.parseInt(dataLinha[2].trim());
                novoDiretor = new Diretor(directorId, directorName, movieId);

                // Novo código da parte 2.
                // - - -
                // Verifico se já foi "encontrado" um movieId.
                // Se sim ⇾ adiociono um novo Diretor relacionado com o movieId.
                // Se não ⇾ crio um novo elemento com o movieId.
                if (!movieIdDirector.containsKey(movieId)) {
                    movieIdDirector.put(movieId, new HashSet<>());
                }
                movieIdDirector.get(movieId).add(novoDiretor);
                // - - -

                listaDiretores.add(novoDiretor);
                linhaOK[1]++;
            }

            reader.close();
        } catch (FileNotFoundException e) {
            // Ficheiro não existe
            return false;
        } catch (IOException e) {
            // Ficheiro vazio ou não tem linhas
            return false;
        }
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //end = System.currentTimeMillis();
        //System.out.println("Ficheiro Diretores " + (end - start) + "ms");


        //start = System.currentTimeMillis();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Ficheiro -> genres
        File ficheiroGenero = new File(pasta, "genres.csv");
        //
        try {
            listaGeneros.clear();
            reader = new BufferedReader(new FileReader(ficheiroGenero));
            reader.readLine(); // Ignora a 1.ª linha (cabeçalho)

            Genero novoGenero;
            int genreId;
            String genreName;

            while ((linha = reader.readLine()) != null) {
                dataLinha = linha.split(",");

                // Tem mais ou menos informações do que deveria
                if (dataLinha.length != 2) {
                    acrescentarLinhaNOK(3);
                    continue;
                }

                genreId = Integer.parseInt(dataLinha[0].trim());
                genreName = dataLinha[1].trim();
                novoGenero = new Genero(genreId, genreName);

                // Novo código da parte 2.
                // - - -
                // Verifico se já foi "encontrado" um genreId.
                // Se não ⇾ adiociono um novo genreName relacionado com o genreId.
                if (!genreIdGenreName.containsKey(genreId)) {
                    genreIdGenreName.put(genreId, genreName);
                }
                // - - -

                listaGeneros.add(novoGenero);
                linhaOK[3]++;
            }

            reader.close();
        } catch (FileNotFoundException e) {
            // Ficheiro não existe
            return false;
        } catch (IOException e) {
            // Ficheiro vazio ou não tem linhas
            return false;
        }
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //end = System.currentTimeMillis();
        //System.out.println("Ficheiro Generos " + (end - start) + "ms");


        //start = System.currentTimeMillis();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Ficheiro -> genres_movies
        File ficheiroGenerosFilme = new File(pasta, "genres_movies.csv");
        //
        try {
            listaGeneroFilmes.clear();
            reader = new BufferedReader(new FileReader(ficheiroGenerosFilme));
            reader.readLine(); // Ignora a 1.ª linha (cabeçalho)

            GeneroFilme novoGeneroFilme;
            int genreId;
            int movieId;
            String genreName;

            while ((linha = reader.readLine()) != null) {
                dataLinha = linha.split(",");

                // Tem mais ou menos informações do que deveria
                if (dataLinha.length != 2) {
                    acrescentarLinhaNOK(2);
                    continue;
                }

                genreId = Integer.parseInt(dataLinha[0].trim());
                movieId = Integer.parseInt(dataLinha[1].trim());

                // Novo código da parte 2.
                // - - -
                genreName = genreIdGenreName.get(genreId);
                // Verifico se já foi "encontrado" um movieId.
                // Se não ⇾ adiociono um novo genreName relacionado com o movieId.
                // Se não ⇾ crio um novo elemento com o movieId.
                if (!movieIdGenreName.containsKey(movieId)) {
                    movieIdGenreName.put(movieId, new HashSet<>());
                }
                movieIdGenreName.get(movieId).add(genreName);
                // - - -

                novoGeneroFilme = new GeneroFilme(genreId, movieId, genreName);

                listaGeneroFilmes.add(novoGeneroFilme);
                linhaOK[2]++;
            }

            reader.close();
        } catch (FileNotFoundException e) {
            // Ficheiro não existe
            return false;
        } catch (IOException e) {
            // Ficheiro vazio ou não tem linhas
            return false;
        }
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //end = System.currentTimeMillis();
        //System.out.println("Ficheiro Generos Filme " + (end - start) + "ms");


        //start = System.currentTimeMillis();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Ficheiro -> movie_votes
        File ficheiroVotosFilmes = new File(pasta, "movie_votes.csv");
        //
        try {
            listaVotosFilmes.clear();
            reader = new BufferedReader(new FileReader(ficheiroVotosFilmes));
            reader.readLine(); // Ignora a 1.ª linha (cabeçalho)

            VotosFilme novoVotosFilme;
            int movieId;
            float movieRating;
            int movieRatingCount;

            while ((linha = reader.readLine()) != null) {
                dataLinha = linha.split(",");

                // Tem mais ou menos informações do que deveria
                if (dataLinha.length != 3) {
                    acrescentarLinhaNOK(4);
                    continue;
                }

                movieId = Integer.parseInt(dataLinha[0].trim());
                movieRating = Float.parseFloat(dataLinha[1].trim());
                movieRatingCount = Integer.parseInt(dataLinha[2].trim());
                novoVotosFilme = new VotosFilme(movieId, movieRating, movieRatingCount);

                // Novo código da parte 2.
                // - - -
                // Verifico se já foi "encontrado" um movieId.
                // Se não ⇾ crio um novo elemento com o movieId e já relaciono a um movieVote.
                if (!movieIdMovieVote.containsKey(movieId)) {
                    movieIdMovieVote.put(movieId, movieRating);
                }
                // - - -

                listaVotosFilmes.add(novoVotosFilme);
                linhaOK[4]++;
            }

            reader.close();
        } catch (FileNotFoundException e) {
            // Ficheiro não existe
            return false;
        } catch (IOException e) {
            // Ficheiro vazio ou não tem linhas
            return false;
        }
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //end = System.currentTimeMillis();
        //System.out.println("Ficheiro Votos Filme " + (end - start) + "ms");


        //start = System.currentTimeMillis();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Ficheiro -> movies
        File ficheiroFilmes = new File(pasta, "movies.csv");
        //
        try {
            listaFilmes.clear();
            reader = new BufferedReader(new FileReader(ficheiroFilmes));
            reader.readLine(); // Ignora a 1.ª linha (cabeçalho)

            // Usando uma variável que guarda todos os movieId consigo diminuir o tempo que o programa demora para "correr" com arquivos grandes
            HashSet<Integer> movieIdUnicos = new HashSet<>();

            Filme novoFilme;
            int movieId;
            String movieName;
            float movieDuration;
            long movieBudget;
            String movieReleaseDate;

            while ((linha = reader.readLine()) != null) {
                dataLinha = linha.split(",");

                // Tem mais ou menos informações do que deveria
                if (dataLinha.length != 5) {
                    acrescentarLinhaNOK(5);
                    continue;
                }

                movieId = Integer.parseInt(dataLinha[0].trim());

                // Verifico se já existe um movieId na listaFilmes.
                // Se não houver ⇾ guardo as informções do filme em listaFilmes e também guardo o movieId do filme para verificar se exite "ids" duplicados.
                // Se houver ⇾ não adiciono à lista, mas mesmo assim a linha foi lida com sucesso.
                if (!movieIdUnicos.contains(movieId)) {
                    movieName = dataLinha[1].trim();
                    movieDuration = Float.parseFloat(dataLinha[2].trim());
                    movieBudget = Long.parseLong(dataLinha[3].trim());
                    movieReleaseDate = dataLinha[4].trim();
                    novoFilme = new Filme(movieId, movieName, movieDuration, movieBudget, movieReleaseDate);

                    // Novo código parte2.
                    // - - -
                    // Grava todos os Atores no respectivo movieId.
                    if (movieIdActor.containsKey(movieId)) {
                        novoFilme.setAtoresAssociados(movieIdActor.get(movieId));
                    }

                    // Faz o mesmo com os Diretores.
                    if (movieIdDirector.containsKey(movieId)) {
                        novoFilme.setDiretoresAssociados(movieIdDirector.get(movieId));
                    }

                    // Faz o mesmo com o movieVote.
                    if (movieIdMovieVote.containsKey(movieId)) {
                        novoFilme.setMovieVote(movieIdMovieVote.get(movieId));
                    }

                    // Faz o mesmo com o genreName.
                    if (movieIdGenreName.containsKey(movieId)) {
                        novoFilme.setGenerosAssociados(movieIdGenreName.get(movieId));
                    }
                    // - - -

                    novoFilme.verificarIdMaior1000();
                    listaFilmes.add(novoFilme);
                    movieIdUnicos.add(movieId);
                }

                linhaOK[5]++;
            }

            reader.close();
        } catch (FileNotFoundException e) {
            // Pasta não existe
            return false;
        } catch (IOException e) {
            // Ficheiro vazio ou não tem linhas
            return false;
        }
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //end = System.currentTimeMillis();
        //System.out.println("Ficheiro Filmes " + (end - start) + "ms");

        return true; // Ficheiros da pasta lidos com sucesso!
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Existe algum erro nesta linha:
    // - dados a mais ou a menos.
    static void acrescentarLinhaNOK(int posAtual) {
        if (linhaNOK[posAtual] == 0) {
            primeiraLinhaErrada[posAtual] = (linhaOK[posAtual] + 1);
        }
        linhaNOK[posAtual]++;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Retorna um arraylist com algumas informações dos ficheiros na mesma ordem lido no ficheiro.
    // Por exemplo: tipoEntidade.ATOR → irá devolver um arraylist com vários objetos relacionado com o ator.
    public static ArrayList getObjects(TipoEntidade tipoEntidade) {
        return switch (tipoEntidade) {
            case ATOR -> listaAtores;
            case FILME -> listaFilmes;
            case REALIZADOR -> listaDiretores;
            case GENERO_CINEMATOGRAFICO -> listaGeneros;
            case INPUT_INVALIDO -> obterStringInputInvalido();
            default -> null;
        };
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Retorna a String como se deve ser para o comando INPUT_INVALIDO
    private static ArrayList<String> obterStringInputInvalido() {
        ArrayList<String> valores = new ArrayList<>();

        valores.add("movies.csv | " + linhaOK[5] + " | " + linhaNOK[5] + " | " + primeiraLinhaErrada[5]);
        valores.add("actors.csv | " + linhaOK[0] + " | " + linhaNOK[0] + " | " + primeiraLinhaErrada[0]);
        valores.add("directors.csv | " + linhaOK[1] + " | " + linhaNOK[1] + " | " + primeiraLinhaErrada[1]);
        valores.add("genres.csv | " + linhaOK[3] + " | " + linhaNOK[3] + " | " + primeiraLinhaErrada[3]);
        valores.add("genres_movies.csv | " + linhaOK[2] + " | " + linhaNOK[2] + " | " + primeiraLinhaErrada[2]);
        valores.add("movie_votes.csv | " + linhaOK[4] + " | " + linhaNOK[4] + " | " + primeiraLinhaErrada[4]);
        return valores;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Função que serve para receber inputs, que no nosso caso são comandos.
    // Só obtemos falha (ou seja, sucesso = false) quando é introduzido um comando errado.
    // Detalhes dos valores retornados pelos comandos:
    // COUNT - dados numéricos ; GET - lista de resultado ; INSERT - altera os dados em memória (mas não os ficheiros)
    public static Result execute(String comando) {
        Result resultado = new Result();
        String[] comandoPorPartes = comando.split(" ");

        if (comandoPorPartes[0].equals("INSERT_ACTOR") || comandoPorPartes[0].equals("INSERT_DIRECTOR")) {
            String dadosNovoElemento = "";
            for (int i = 1; i < comandoPorPartes.length; i++) {
                dadosNovoElemento += " " + comandoPorPartes[i];
            }
            comandoPorPartes[1] = dadosNovoElemento;
        }

        if (comandoPorPartes[0].equals("DISTANCE_BETWEEN_ACTORS")) {
            String atores = "";
            for (int i = 1; i < comandoPorPartes.length; i++) {
                atores += comandoPorPartes[i] + " ";
            }
            comandoPorPartes[1] = atores.split(",")[0];
            comandoPorPartes[2] = atores.split(",")[1];
        }

        switch (comandoPorPartes[0]) {
            // Conta quantos filmes foram feitos no mês e ano passado no parâmetro.
            case "COUNT_MOVIES_MONTH_YEAR": // <month> <year>
                resultado.comandoCorreto(countMoviesMonthYear(comandoPorPartes));
                break;


            // Conta quantos filmes o diretor fez.
            case "COUNT_MOVIES_DIRECTOR": // <full-name>
                resultado.comandoCorreto(countMoviesDirector(comandoPorPartes));
                break;


            // Conta os números de atores que participaram em filmes desses dois anos.
            case "COUNT_ACTORS_IN_2_YEARS": // <year-1> <year-2>
                resultado.comandoCorreto(countActorsIn2Years(comandoPorPartes));
                break;


            // Conta todos os filmes no intervalo de dois anos passado no parâmetro (exclusive) que tenham tido a quantidade de atores no intervalo de 'min' e 'max' (exclusive).
            case "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS": // <year-start> <year-end> <min> <max>
                resultado.comandoCorreto(countMoviesBetweenYearsWithNActors(comandoPorPartes));
                break;


            // Retorna todos os nomes dos filmes de um certo ano em que participa o ator.
            // Filmes ordenados por ordem de lançamento
            case "GET_MOVIES_ACTOR_YEAR": // <year> <full-name>
                resultado.verificarComando(getMoviesActorYear(comandoPorPartes));
                break;


            // Retorna todos os filmes cujo nome ou apelidos, ou até letras que pertençam ao nome e/ou apelido, dos atores estão envolvidos.
            // Filmes ordenados por ordem alfabética.
            case "GET_MOVIES_WITH_ACTOR_CONTAINING": // <name>
                // GET_MOVIES_WITH_ACTOR_CONTAINING on
                resultado.verificarComando(getMoviesWithActorContaining(comandoPorPartes));
                break;


            // Retorna quais os 4 anos (no máximo) em que foram realizados os filmes cujo título contenha uma certa string passada pelo parâmetro.
            // Ordenado pelo n.º de ocorrências, do maior ao menor (ou, se houver empate, pelo ano menor até ao maior).
            case "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING": // <search-string>
                resultado.verificarComando(getTop4YearWithMoviesContaining(comandoPorPartes));
                break;


            // Retorna todos os atores que trabalharam n ou mais vezes com um diretor.
            // Ordenação é indiferente.
            case "GET_ACTORS_BY_DIRECTOR": // <num> <full-name>
                int numVezes = Integer.parseInt(comandoPorPartes[1]);

                if (numVezes <= 0) {
                    resultado.success = false;
                    resultado.error = null;
                    resultado.result = "COMANDO INVALIDO";
                    break;
                }

                resultado.verificarComando(getActorsByDirector(comandoPorPartes));
                break;


            // Retorna qual o mês com mais filmes no ano.
            case "TOP_MONTH_MOVIE_COUNT": // <year>
                resultado.verificarComando(topMonthMovieCount(comandoPorPartes));
                break;


            // Retorna os atores com maior classificação do ano de um filme, juntamente com a avaliação/classificação desse mesmo filme.
            // O parâmetro num indica o número máximo de resultados.
            // Ordenado pelo valor de classificação (se houver empate não importa a ordem dos atores).
            case "TOP_VOTED_ACTORS": // <num> <year>
                resultado.verificarComando(topVotedActors(comandoPorPartes));
                break;


            // Retorna os filmes do ano indicado com mais atores do gênero indicado.
            // O parâmetro num indica o número máximo de resultado.
            // O parâmetro gender pode ser M ou F.
            // Ordenado pelo número de atores de forma decrescente (se houver empate, os filmes devem ser ordenados de forma alfabética).
            case "TOP_MOVIES_WITH_MORE_GENDER": // <num> <year> <gender>
                resultado.verificarComando(topMoviesWithGender(comandoPorPartes));
                break;


            // Retorna os filmes do ano indicado que tem uma maior deferença entre os gêneros dos atores.
            // São apenas consideredos filmes com 11 ou mais atores.
            // Ordenado pela porcentagem de atores masculinos e femininos (ignorar empates).
            case "TOP_MOVIES_WITH_GENDER_BIAS": // <num> <year> (<min> <max>)
                ArrayList<String> retorno = topMoviesWithGenderBias(comandoPorPartes);

                if (retorno == null) {
                    resultado.success = false;
                    resultado.error = null;
                    resultado.result = "ERRO";
                    break;
                }

                resultado.verificarComando(retorno);
                break;


            // Retorna 6 nomes de diretores.
            // Procura por todos os filmes contidos no intervalo de dois anos (inclusive).
            // Para cada filme, será analisado se têm 2 ou mais diretores com o mesmo apelido/sobrenome (último nome). Assumimos, assim, que são da mesma família.
            // Ordernado pelo número de ocorrência, quantos filmes os diretores fizeram juntos (se houver empate é irrelevante a ordem).
            case "TOP_6_DIRECTORS_WITHIN_FAMILY": // <year-start> <year-end>
                // TOP_6_DIRECTORS_WITHIN_FAMILY 1970 1994
                resultado.verificarComando(top6DirectorsWithinFamily(comandoPorPartes));
                break;


            // O camando insert altera os dados de memória dos atores, mas não os ficheiros.
            // Insere um novo ator na memória do programa (variável global).
            // Não se pode adicionar ator com id existente.
            // Caso a inserção do novo ator der certo, será mostrada uma mensagem 'OK'. Caso contrário, será mostrada 'Erro'.
            // Os dados do ator passado no comando devem ser separados por ponto e vígula.
            case "INSERT_ACTOR":
                resultado.verificarComandoInsercao(insertActor(comandoPorPartes));
                break;


            // O camando insert altera os dados de memória dos atores, mas não os ficheiros.
            // Insere um novo diretor na memória do programa (variável global).
            // Não se pode adicionar diretor com id existente.
            // Caso a inserção do novo diretor der certo, será mostrada uma mensagem 'OK'. Caso contrário, será mostrada 'Erro'.
            // Os dados do diretor passado no comando devem ser separados por ponto e vígula.
            case "INSERT_DIRECTOR":
                retorno = insertDirector(comandoPorPartes);

                if (retorno != null) {
                    String nomes = "";

                    for (String nomeDiretores : retorno) {
                        nomes += nomeDiretores;
                    }

                    resultado.success = true;
                    resultado.error = null;
                    resultado.result = nomes;
                    break;
                }

                resultado.comandoInsercaoInvalido();
                break;


            // Retorna a distância que entre dois atores que participaram no mesmo filme ou que trabalharam com atores que, por sua vez, trabalharam com outros atores.
            // Ou seja, retorna a distância que o ator1 tem para o ator2.
            // Distância 0 ⇾ trabalharam juntos no mesmo filme. Ou seja, se X atuou com Y no mesmo filme.
            // Distância 1 ⇾ tem um ator em que já trabalharam em comum. Ou seja, se X atuou com Y num filme e Y atuou com Z num outro filme.
            // No result ⇾ Não tem ligação nenhuma ou licações muito próximas.
            case "DISTANCE_BETWEEN_ACTORS": // <actor-1> <actor-2>
                // DISTANCE_BETWEEN_ACTORS John Travolta,Samuel L. Jackson
                // DISTANCE_BETWEEN_ACTORS John Travolta,Morgan Freeman
                resultado.verificarComando(distanceBetweenActors(comandoPorPartes));
                break;


            // Mostra todos os comandos existentes.
            case "HELP":
                help();
                break;


            case "QUIZZ_TIME":
                quizzTime(comandoPorPartes);
                resultado.comandoCorreto("");
                break;


            default:
                // Comando inserido é inválido ou o formato como foram inseridos os valores (parâmetros).
                resultado.comandoInvalido();
                break;
        }

        return resultado;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Todas as funções para executar cada comando.
    //
    public static int countMoviesMonthYear(String[] comandoPorPartes) {
        String mes = retornaMesCom2Digitos(comandoPorPartes[1]);
        String ano = comandoPorPartes[2];
        int contador = 0;

        // Procuro pelos filmes que têm o mês e o ano de lançamento iguais ao mês e ano alvo (a que foram passadas no parâmetro do comando).
        // Após encontrá-lo, incremento um valor.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieReleaseOnlyYear().equals(ano) && filme.getMovieReleaseOnlyMonth().equals(mes)) {
                contador++;
            }
        }
        return contador;
    }
    //
    //
    public static int countMoviesDirector(String[] comandoPorPartes) {
        String nomeDiretorAlvo = retornaNomeParametro(comandoPorPartes, 1);
        int contador = 0;

        // Procuro pelo nome do diretor alvo.
        // Após encontrá-lo, incremento um valor.
        for (Diretor diretorAtual : listaDiretores) {
            if (diretorAtual.getDirectorName().equals(nomeDiretorAlvo)) {
                contador++;
            }
        }
        return contador;
    }
    //
    //
    public static int countActorsIn2Years(String[] comandoPorPartes) {
        String ano1 = comandoPorPartes[1];
        String ano2 = comandoPorPartes[2];
        HashSet<Integer> actorsIdAno1 = new HashSet<>();
        HashSet<Integer> actorsIdAno2 = new HashSet<>();
        int contador = 0;

        // Procuro por filmes que têm anos iguais aos dois anos passado no parâmetro do comando (nesse caso, as variáveis ano1 e ano2).
        // Após encontrá-las, guardo todos os ids dos atores numa "caixas" (hashset) do ano1 e, em outra "caixa", os ids do ano2.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieReleaseOnlyYear().equals(ano1)) {
                actorsIdAno1.addAll(filme.getAllActorsId());
            }

            if (filme.getMovieReleaseOnlyYear().equals(ano2)) {
                actorsIdAno2.addAll(filme.getAllActorsId());
            }
        }

        // Procuro os ids dos atores que participaram no filme do ano1 e do ano2.
        // Se estiverem nos dois anos, incremento um valor.
        for (int actorId : actorsIdAno1) {
            if (actorsIdAno2.contains(actorId)) {
                contador++;
            }
        }

        return contador;
    }
    //
    //
    public static int countMoviesBetweenYearsWithNActors(String[] comandoPorPartes) {
        int anoInicio = Integer.parseInt(comandoPorPartes[1]);
        int anoFim = Integer.parseInt(comandoPorPartes[2]);
        int minAtores = Integer.parseInt(comandoPorPartes[3]);
        int maxAtores = Integer.parseInt(comandoPorPartes[4]);
        int anoFilmeAtual;
        int quantTodosAtores;
        int contador = 0;

        // Percorro todos os filmes.
        // Em seguida, vejo se está contido no intervalo dos dois anos passado no parâmetro do comando (nesse caso, são as variáveis anoInicio e anoFim).
        // Também verifico se o número de atores do filme está contido no intervalo de atores min e max (minAtores e maxAtores).
        // Se o filme cumprir com todos esses passos, incremento um valor.
        for (Filme filme : listaFilmes) {
            anoFilmeAtual = filme.getMovieReleaseOnlyYearInt();
            if (anoInicio < anoFilmeAtual && anoFilmeAtual < anoFim) {
                quantTodosAtores = filme.getQuantAllActors();
                if (minAtores < quantTodosAtores && quantTodosAtores < maxAtores) {
                    contador++;
                }
            }
        }
        return contador;
    }
    //
    //
    public static ArrayList<String> getMoviesActorYear(String[] comandoPorPartes) {
        String anoAlvo = comandoPorPartes[1];
        String nomeAtorAlvo = retornaNomeParametro(comandoPorPartes, 2);
        HashMap<String, String> lancamentoPorNomeFilme = new HashMap<>(); // key - nomeFilme ; value - lançamento na ordem: mês e dia

        // Procuro por filmes que tenham o mesmo ano de lançamento e o nome do ator, ambos passados no parâmetro do comando.
        // Se forem iguais, guardo o nome do filme e o mês e o ano de lançamento.
        for (Filme filmeAtual : listaFilmes) {
            if (filmeAtual.getMovieReleaseOnlyYear().equals(anoAlvo)) {
                if (filmeAtual.getAllActorsName().contains(nomeAtorAlvo)) {
                    lancamentoPorNomeFilme.put(filmeAtual.getMovieName(), filmeAtual.getMovieReleaseOnlyMonth() + filmeAtual.getMovieReleaseOnlyDay());
                }
            }
        }

        // Ordena pelo lançamento do filme (do mais recente para o menos recente).
        ArrayList<String> nomeFilmesOrdenados = new ArrayList<>(lancamentoPorNomeFilme.keySet());
        nomeFilmesOrdenados.sort(Comparator.comparing(lancamentoPorNomeFilme::get));

        return nomeFilmesOrdenados;
    }
    //
    //
    public static ArrayList<String> getMoviesWithActorContaining(String[] comandoPorPartes) {
        String nomeAtorAlvo = retornaNomeParametro(comandoPorPartes, 1);
        ArrayList<String> todosFilmes = new ArrayList<>();

        // Procuro por filmes que o ator alvo atuou.
        for (Filme filme : listaFilmes) {
            for (String nomeAtorAtual : filme.getAllActorsName()) {
                if (nomeAtorAtual.contains(nomeAtorAlvo) && !todosFilmes.contains(filme.getMovieName())) {
                    todosFilmes.add(filme.getMovieName());
                }
            }
        }

        // Ordena os filmes por ordem alfabética
        Collections.sort(todosFilmes);
        return todosFilmes;
    }
    //
    //
    public static ArrayList<String> getTop4YearWithMoviesContaining(String[] comandoPorPartes) {
        String palavraAlvo = comandoPorPartes[1];
        HashMap<String, Integer> ocorrenciasPorAno = new HashMap<>(); // key - ano ; value - ocorrências
        String anoAtual;
        int ocorrencias;
        int numMaximo = 4;

        // Procuro por filmes que tenham a palavra alvo no título.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieName().contains(palavraAlvo)) {
                anoAtual = filme.getMovieReleaseOnlyYear();
                ocorrencias = ocorrenciasPorAno.getOrDefault(anoAtual, 0) + 1;
                ocorrenciasPorAno.put(anoAtual, ocorrencias);
            }
        }

        // Ordeno pelos anos com mais ocorrências para os de menos ocorrências.
        // Se houver empate, ordeno pelo ano menor ao maio.
        ArrayList<String> anosOrdenados = new ArrayList<>(ocorrenciasPorAno.keySet());

        Collections.sort(anosOrdenados);
        anosOrdenados.sort(Comparator.comparing((String ocorrencia) -> ocorrenciasPorAno.get(ocorrencia)).reversed());

        return retornaFormatoIdealString(anosOrdenados, ocorrenciasPorAno, Math.min(anosOrdenados.size(), numMaximo));
    }
    //
    //
    public static ArrayList<String> getActorsByDirector(String[] comandoPorPartes) {
        int numVezes = Integer.parseInt(comandoPorPartes[1]);
        String nomeDiretorAlvo = retornaNomeParametro(comandoPorPartes, 2);
        HashMap<String, Integer> ocorrenciasPorNomeAtor = new HashMap<>(); // key - nome do ator ; value - ocorrências
        int ocorrencias;

        // Percorro os filmes até encontrar um que tem como diretor o nomeDiretorAlvo.
        // Em seguida, guardo os nomes de todos os atores e as ocorrências que trabalharam nesse filme.
        for (Filme filme : listaFilmes) {
            if (filme.getAllDirectorsName().contains(nomeDiretorAlvo)) {
                for (String nomeAtor : filme.getAllActorsName()) {
                    ocorrencias = ocorrenciasPorNomeAtor.getOrDefault(nomeAtor, 0) + 1;
                    ocorrenciasPorNomeAtor.put(nomeAtor, ocorrencias);
                }
            }
        }

        // Vejo se ocorrência que o ator trabalhou com o diretor é maior ou igual ao numVezes.
        ArrayList<String> atoresOrdenados = new ArrayList<>();
        for (Map.Entry<String, Integer> map : ocorrenciasPorNomeAtor.entrySet()) {
            if (map.getValue() >= numVezes) {
                atoresOrdenados.add(map.getKey() + ":" + map.getValue());
            }
        }

        return atoresOrdenados;
    }
    //
    //
    public static String topMonthMovieCount(String[] comandoPorPartes) {
        String anoAlvo = comandoPorPartes[1];
        HashMap<Integer, Integer> ocorrenciaPorMes = new HashMap<>(); // key - mês ; value - ocorrência
        int mesAtual;
        int ocorrencias;

        // Procuro os meses dos filmes que correspondem ao anoAlvo.
        // Vou incrementanto um valor.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieReleaseOnlyYear().equals(anoAlvo)) {
                mesAtual = filme.getMovieReleaseOnlyMonthInt();
                ocorrencias = ocorrenciaPorMes.getOrDefault(mesAtual, 0) + 1;
                ocorrenciaPorMes.put(mesAtual, ocorrencias);
            }
        }

        // Ordeno do mês com mais ocorrências para o com menos ocorrências.
        ArrayList<Integer> mesesOrdenados = new ArrayList<>(ocorrenciaPorMes.keySet());
        mesesOrdenados.sort(Comparator.comparing((Integer mes) -> ocorrenciaPorMes.get(mes)).reversed());

        return mesesOrdenados.get(0) + ":" + ocorrenciaPorMes.get(mesesOrdenados.get(0));
    }
    //
    //
    public static ArrayList<String> topVotedActors(String[] comandoPorPartes) {
        int numMaximo = Integer.parseInt(comandoPorPartes[1]);
        String anoAlvo = comandoPorPartes[2];
        HashMap<String, Float> votosPorAtor = new HashMap<>(); // key - ator ; value - voto (avaliação) do filme em que o ator atuou
        float media;

        // Procura por filmes com o ano de lançamento igual ao anoAlvo.
        // Faz a média dos votos.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieReleaseOnlyYear().equals(anoAlvo)) {
                for (String nomeAtorAtual : filme.getAllActorsName()) {
                    if (votosPorAtor.containsKey(nomeAtorAtual)) {
                        media = votosPorAtor.get(nomeAtorAtual);
                        media = (media + filme.getMovieVote()) / 2;
                        votosPorAtor.put(nomeAtorAtual, media);
                    } else {
                        votosPorAtor.put(nomeAtorAtual, filme.getMovieVote());
                    }
                }
            }
        }

        ArrayList<String> votosOrdenados = new ArrayList<>(votosPorAtor.keySet());
        votosOrdenados.sort(Comparator.comparing((String voto) -> votosPorAtor.get(voto)).reversed());

        return retornaFormatoIdealStringFloat(votosOrdenados, votosPorAtor, Math.min(votosPorAtor.size(), numMaximo));
    }
    //
    //
    public static ArrayList<String> topMoviesWithGender(String[] comandoPorPartes) {
        int numMaximo = Integer.parseInt(comandoPorPartes[1]);
        String anoAlvo = comandoPorPartes[2];
        char genero = comandoPorPartes[3].charAt(0);
        HashMap<String, Integer> ocorrenciaPorNomeFilme = new HashMap<>(); // key - nome do filme ; value - ocorrências

        // Procuro todos os filmes que tem a data de lançamento igual ao anoAlvo.
        // Depois analiso se preciso guarda a quantidade do número atores ou atrizes que participaram nesse filme.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieReleaseOnlyYear().equals(anoAlvo)) {
                if (genero == 'M') {
                    ocorrenciaPorNomeFilme.put(filme.getMovieName(), filme.getNumMaleActors());
                } else {
                    ocorrenciaPorNomeFilme.put(filme.getMovieName(), filme.getNumFemaleActors());
                }
            }
        }

        // Ordeda alfabeticamente primeiro e depois ordenada pelo número de ocorrências (do maior ao menor).
        ArrayList<String> nomeFilmesOrdenados = new ArrayList<>(ocorrenciaPorNomeFilme.keySet());
        Collections.sort(nomeFilmesOrdenados);
        nomeFilmesOrdenados.sort(Comparator.comparing((String nomeFilme) -> ocorrenciaPorNomeFilme.get(nomeFilme)).reversed());

        return retornaFormatoIdealString(nomeFilmesOrdenados, ocorrenciaPorNomeFilme, Math.min(ocorrenciaPorNomeFilme.size(), numMaximo));
    }
    //
    //
    public static ArrayList<String> topMoviesWithGenderBias(String[] comandoPorPartes) {
        int numMaximo = Integer.parseInt(comandoPorPartes[1]);
        String anoAlvo = comandoPorPartes[2];
        int min = 0;
        int max = 0;

        if (comandoPorPartes.length == 5) {
            min = Integer.parseInt(comandoPorPartes[3]);
            max = Integer.parseInt(comandoPorPartes[4]);

            if (min >= max) {
                return null;
            }
        }

        HashMap<String, Integer> porcentagemPorNomeFilme = new HashMap<>();
        HashMap<String, Character> generoPorNomeFilme = new HashMap<>();
        String nomeFilmeAtual;
        int numMasculino;
        int numFeminino;
        int porcentagem;
        char genero;

        // Procuro por filmes lançados no mesmo ano que o anoAlvo.
        // Depois, analiso se esse filme tem pelo menos 11 ou mais atores. Caso não houver, procuro por outro filme.
        // Se houver os atores necessários, faço uma porcentagem da quantidade de atores feminos com os masculinos.
        // Guardo o gênero que tiver a maior quantidade ('M' ou 'F').
        for (Filme filme : listaFilmes) {
            if (filme.getMovieReleaseOnlyYear().equals(anoAlvo)) {
                if (min <= filme.getQuantAllActors() && filme.getQuantAllActors() <= max) {
                    if (max < 11) {
                        continue;
                    }
                }

                numMasculino = filme.getNumMaleActors();
                numFeminino = filme.getNumFemaleActors();
                porcentagem = (int) Math.round((Math.max(numMasculino, numFeminino) * 100.0) / (numMasculino + numFeminino));
                genero = (numMasculino > numFeminino) ? 'M' : 'F';
                nomeFilmeAtual = filme.getMovieName();

                porcentagemPorNomeFilme.put(nomeFilmeAtual, porcentagem);
                generoPorNomeFilme.put(nomeFilmeAtual, genero);
            }
        }

        // Ordeno pelas ocorrências (do maior ao menor).
        ArrayList<String> porcentagemOrdenada = new ArrayList<>(porcentagemPorNomeFilme.keySet());
        porcentagemOrdenada.sort(Comparator.comparing((String nomeFilme) -> porcentagemPorNomeFilme.get(nomeFilme)).reversed());

        return retornaFormatoIdealStringPor2ArrayList(porcentagemOrdenada, porcentagemPorNomeFilme, generoPorNomeFilme, Math.min(porcentagemPorNomeFilme.size(), numMaximo));
    }
    //
    //
    public static ArrayList<String> top6DirectorsWithinFamily(String[] comandoPorPartes) {
        int anoInicio = Integer.parseInt(comandoPorPartes[1]);
        int anoFim = Integer.parseInt(comandoPorPartes[2]);
        int numMaximo = 6;
        HashMap<String, Integer> ocorrenciasPorNomeDiretor = new HashMap<>(); // key - nome do diretor ; value - ocorrências
        int anoAtual;
        int ocorrencias;

        for (Filme filme : listaFilmes) {
            // Apenas analiso os filmes com 2 ou mais diretores.
            if (filme.getAllDirectorsName().size() < 2) {
                continue;
            }

            anoAtual = Integer.parseInt(filme.getMovieReleaseOnlyYear());
            if (anoInicio <= anoAtual && anoAtual <= anoFim) {
                for (String nomeDiretor1 : filme.getAllDirectorsName()) {
                    String[] nomePartes1 = nomeDiretor1.split(" ");
                    String ultimoNome1 = nomePartes1[nomePartes1.length - 1];

                    for (String nomeDiretor2 : filme.getAllDirectorsName()) {
                        String[] nomePartes2 = nomeDiretor2.split(" ");
                        String ultimoNome2 = nomePartes2[nomePartes2.length - 1];

                        if (nomeDiretor2.equals(nomeDiretor1)) {
                            continue;
                        }

                        if (ultimoNome1.equals(ultimoNome2)) {
                            ocorrencias = ocorrenciasPorNomeDiretor.getOrDefault(nomeDiretor2, 0) + 1;
                            ocorrenciasPorNomeDiretor.put(nomeDiretor2, ocorrencias);
                        }
                    }
                }
            }
        }

        ArrayList<String> nomeDiretoresOrdenados = new ArrayList<>(ocorrenciasPorNomeDiretor.keySet());
        nomeDiretoresOrdenados.sort(Comparator.comparing((String nomeDiretor) -> ocorrenciasPorNomeDiretor.get(nomeDiretor)).reversed());

        return retornaFormatoIdealString(nomeDiretoresOrdenados, ocorrenciasPorNomeDiretor, Math.min(ocorrenciasPorNomeDiretor.size(), numMaximo));
    }
    //
    //
    public static boolean insertActor(String[] comandoPorPartes) {
        String[] dadosNovoAtor = comandoPorPartes[1].split(";");

        int id = Integer.parseInt(dadosNovoAtor[0].trim());
        boolean dadosValidos = true;

        // Vejo se já existe um ator com o id do novo ator.
        for (Ator ator : listaAtores) {
            if (ator.getActorId() == id) {
                dadosValidos = false;
                break;
            }
        }

        // id já existente.
        if (!dadosValidos) {
            return dadosValidos;
        }

        String nome = dadosNovoAtor[1];
        String genero = dadosNovoAtor[2];
        int movieId = Integer.parseInt(dadosNovoAtor[3]);

        Ator novoAtor = new Ator(id, nome, genero, movieId);
        listaAtores.add(novoAtor);

        // Adiciono o novo ator ao filme que atuou.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieId() == movieId) {
                filme.adicionarNovoAtor(id, nome, genero);
                break;
            }
        }

        return dadosValidos;
    }
    //
    //
    public static ArrayList<String> insertDirector(String[] comandoPorPartes) {
        String[] dadosNovoDiretor = comandoPorPartes[1].split(";");

        int id = Integer.parseInt(dadosNovoDiretor[0].trim());
        boolean dadosValidos = true;

        // Vejo se já existe um diretor com o id do novo diretor.
        for (Diretor diretor : listaDiretores) {
            if (diretor.getDirectorId() == id) {
                dadosValidos = false;
                break;
            }
        }

        // id já existente.
        if (!dadosValidos) {
            return null;
        }

        ArrayList<String> outrosRealizadores = new ArrayList<>();
        String nome = dadosNovoDiretor[1];
        int movieId = Integer.parseInt(dadosNovoDiretor[2]);
        Diretor novoDiretor = new Diretor(id, nome, movieId);
        listaDiretores.add(novoDiretor);

        // Adiciono o novo diretor ao filme que atuou.
        for (Filme filme : listaFilmes) {
            if (filme.getMovieId() == movieId) {
                for (String nomeDiretor : filme.getAllDirectorsName()) {
                    outrosRealizadores.add(nomeDiretor+"\n");
                }
                filme.adicionarNovoDiretor(id, nome);
                break;
            }
        }

        return outrosRealizadores;
    }
    //
    //
    public static int distanceBetweenActors(String[] comandoPorPartes) {
        String ator1 = comandoPorPartes[1].trim();
        String ator2 = comandoPorPartes[2].trim();
        int distancia = -1;

        for (Filme filmeAtual : listaFilmes) {
            // Os dois atores atuaram no mesmo filme.
            if (filmeAtual.getAllActorsName().contains(ator1) && filmeAtual.getAllActorsName().contains(ator2)) {
                distancia = 0;
                break;
            }

            // Procuro por atores que trabalharam com o ator1, logo descarto a busca no ator1 em outros filmes e tenho como atorAlvo o ator2.
            if (filmeAtual.getAllActorsName().contains(ator1)) {
                distancia = verificaAtoresAturamFilmesDiferentes(ator1, ator2, filmeAtual);
            }

            // Procuro por atores que trabalharam com o ator2, logo descarto a busca no ator2 em outros filmes e tenho como atorAlvo o ator1.
            if (filmeAtual.getAllActorsName().contains(ator2)) {
                distancia = verificaAtoresAturamFilmesDiferentes(ator2, ator1, filmeAtual);
            }

            if (distancia == 1) {
                break;
            }
        }

        return distancia;
    }
    //
    //
    public static void help() {
        System.out.println("""
                        \
                        Comandos disponíveis:
                        COUNT_MOVIES_MONTH_YEAR <month> <year>
                        COUNT_MOVIES_DIRECTOR <full-name>
                        COUNT_ACTORS_IN_2_YEARS <year-1> <year-2>
                        COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS <year-start> <year-end> <min> <max>
                        GET_MOVIES_ACTOR_YEAR <year> <full-name>
                        GET_MOVIES_WITH_ACTOR_CONTAINING <name>
                        GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING <search-string>
                        GET_ACTORS_BY_DIRECTOR <num> <full-name>
                        TOP_MONTH_MOVIE_COUNT <year>
                        TOP_VOTED_ACTORS <num> <year>
                        TOP_MOVIES_WITH_MORE_GENDER <num> <year> <gender>
                        TOP_MOVIES_WITH_GENDER_BIAS <num> <year>
                        TOP_6_DIRECTORS_WITHIN_FAMILY <year-start> <year-end>
                        INSERT_ACTOR <id>;<name>;<gender>;<movie-id>
                        INSERT_DIRECTOR <id>;<name>;<movie-id>
                        DISTANCE_BETWEEN_ACTORS <actor-1>,<actor-2>
                        HELP
                        QUIT""");
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =


    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Código criatividade
    // QUIZZ_TIME
    private static void quizzTime(String[] comandoPorParte) {
        if (comandoPorParte.length == 1) {
            System.out.println(quizz.quizzApresentacao());
            return;
        }

        switch (comandoPorParte[1]) {
            case "history":
                System.out.println(quizz.mostrarEstatisticaTodosJogadores());
                break;

            case "play":
                quizz.jogar();
                break;

            default:
                System.out.println("Comando inválido");
        }
    }
    //
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =


    static ArrayList getDirectorsByDuracao(double duracao) {
        ArrayList<String> diretores = new ArrayList<>();

        for (Filme filmeAtual : listaFilmes) {
            if (duracao == filmeAtual.getMovieDuration()) {
                for (Diretor diretor : listaDiretores) {
                    if (filmeAtual.getMovieId() == diretor.getMovieId()) {
                        diretores.add(diretor.toString());
                    }
                }
            }
        }

        return diretores;
    }



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private static int verificaAtoresAturamFilmesDiferentes(String atorParaRetirar, String atorAlvo, Filme filmeAlvo) {
        int distancia = -1;
        ArrayList<String> todosAtoresFilme = new ArrayList<>(filmeAlvo.getAllActorsName());

        todosAtoresFilme.remove(atorParaRetirar);

        // Procuro por filmes os atores que contenham o mesmo ator que trabalhou no filmeAlvo e que tenha atuado com o atorAlvo.
        for (Filme filmeAtual : listaFilmes) {
            // É o mesmo filme que o filme Alvo.
            if (filmeAlvo.getMovieId() == filmeAtual.getMovieId()) {
                continue;
            }

            for (String ator : todosAtoresFilme) {
                if (filmeAtual.getAllActorsName().contains(ator) && filmeAtual.getAllActorsName().contains(atorAlvo)) {
                    distancia = 1;
                    break;
                }
            }

            if (distancia == 1) {
                break;
            }
        }

        return distancia;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Retorna um novo array de String com cada elemento sendo separado pelo caracter ':'.
    private static ArrayList<String> retornaFormatoIdealString(ArrayList<String> array, HashMap<String, Integer> map, int limite) {
        ArrayList<String> mescla = new ArrayList<>();
        String elemento;

        for (int i = 0; i < limite; i++) {
            elemento = array.get(i);
            mescla.add(elemento+":"+map.get(elemento));
        }

        return mescla;
    }
    //
    // Função usada para aceitar o formato HashMap<String, Float>.
    private static ArrayList<String> retornaFormatoIdealStringFloat(ArrayList<String> array, HashMap<String, Float> map, int limite) {
        ArrayList<String> mescla = new ArrayList<>();
        String elemento;

        for (int i = 0; i < limite; i++) {
            elemento = array.get(i);
            mescla.add(elemento+":"+map.get(elemento));
        }

        return mescla;
    }
    //
    // Função que aceita dois HashMap.
    private static ArrayList<String> retornaFormatoIdealStringPor2ArrayList(ArrayList<String> array, HashMap<String, Integer> map1, HashMap<String, Character> map2, int limite) {
        ArrayList<String> mescla = new ArrayList<>();
        String elemento;

        for (int i = 0; i < limite; i++) {
            elemento = array.get(i);
            mescla.add(elemento+":"+map2.get(elemento)+":"+map1.get(elemento));
        }

        return mescla;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Corrigindo, deve ficar 06, por exemplo.
    private static String retornaMesCom2Digitos(String mes) {
        return (mes.length() == 1) ? ("0" + mes) : mes;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =





    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // Retorna o nome passado pelo parâmetro.
    // Verificamos se o nome é grande (com três ou dois nomes simples) ou um nome curto (com apenas um nome).
    // index começa no index do comandoPorPartes onde tem o nome.
    private static String retornaNomeParametro(String[] nome, int index) {
        return switch (nome.length - index) {
            case 1 -> nome[index];
            case 2 -> nome[index] + " " + nome[index + 1];
            case 3 -> nome[index] + " " + nome[index + 1] + " " + nome[index + 2];
            default -> "";
        };
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =


    public static void main(String[] args) {
        String line;
        Scanner in = new Scanner(System.in);
        parseFiles(new File("test-files"));
        execute("HELP");
        Result resultado;

        do {
            System.out.print("> ");
            line = in.nextLine();

            if (line != null && !line.equals("QUIT")) {
                long start = System.currentTimeMillis();
                resultado = execute(line);
                long end = System.currentTimeMillis();
                System.out.println("Comando demorou: " + (end - start) + "ms");

                if (!resultado.getSuccess()) {
                    System.out.println("Error: " + resultado.getError());
                } else {
                    System.out.println(resultado.getResult());
                }
            }
        } while (line != null && !line.equals("QUIT"));
    }
}
