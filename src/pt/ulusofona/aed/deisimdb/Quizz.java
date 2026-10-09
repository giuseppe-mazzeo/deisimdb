package pt.ulusofona.aed.deisimdb;

import java.util.*;

public class Quizz {
    ArrayList<Filme> listaFilmes = Main.listaFilmes;
    ArrayList<GeneroFilme> listaGeneroFilmes = Main.listaGeneroFilmes;
    ArrayList<Ator> listaAtores = Main.listaAtores;
    ArrayList<Diretor> listaDiretores = Main.listaDiretores;
    public ArrayList<JogadorQuizz> listaJogadores = new ArrayList<>();

    public Quizz() {}


    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    public void jogar() {
        JogadorQuizz jogador = comecarNovoJogo();
        ArrayList<String[]> perguntas = gerar10Perguntas();
        int numPergunta = 1;
        int perguntasCertas = 0;
        long comecou;
        long acabou;

        comecou = System.currentTimeMillis();
        for (String[] perguntaAtual : perguntas) {
            System.out.println("\n" + numPergunta + ") " + perguntaAtual[0]);
            System.out.print(perguntaAtual[1]);
            perguntasCertas += verificarResposta(perguntaAtual[2]);
            numPergunta++;
        }
        acabou = System.currentTimeMillis();
        System.out.println("Obrigado por jogar!");

        jogador.setPerguntasAcertadas(perguntasCertas);
        jogador.setTempo((acabou - comecou) / 1000);
        listaJogadores.add(jogador);
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    public JogadorQuizz comecarNovoJogo() {
        JogadorQuizz novoJogador;
        Scanner scanner = new Scanner(System.in);
        String nome;
        boolean jogadorExistente = false;

        System.out.println("\nOlá jogador, poderia dizer-me o seu nome?");
        do {
            System.out.print("> ");
            nome = scanner.nextLine();
            if (nome.length() > 10) {
                System.out.println("Nome muito grande");
            }
        } while (nome.length() > 10);

        for (JogadorQuizz jogadorAtual : listaJogadores) {
            if (jogadorAtual.getNomeJogador().equals(nome)) {
                System.out.println("Olá " + nome + ", é bom vê-lo de volta. Boa sorte!");
                jogadorExistente = true;
                break;
            }
        }

        novoJogador = new JogadorQuizz(nome);

        if (jogadorExistente) {
            novoJogador.accTentativa();
        }

        return novoJogador;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    public String quizzApresentacao() {
        return """
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                                   Bem-vindo ao QUIZZ TIME!

                    Prepare-se para desafiar seus conhecimentos sobre o universo
                    do entretenimento: filmes, atores, diretores e muito mais!

                    Convide seus amigos e entre na disputa para descobrir
                    quem realmente é o mestre da sétima arte.
                
                    Dica: Use ficheiros grandes para ser mais divertido.

                    Comandos disponíveis:
                    QUIZZ_TIME history
                    QUIZZ_TIME play
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                """;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    public String mostrarEstatisticaTodosJogadores() {
        if (listaJogadores.isEmpty()) {
            return """
                Nenhum histórico de jogador
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                     NOME    PERGUNTAS CERTAS    TEMPO (seg.)    TENTATIVA
                     ----         ---                ---            ---
                - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                """;
        }

        listaJogadores.sort(Comparator.comparingInt(JogadorQuizz::getPerguntasAcertadas).reversed().thenComparingLong(JogadorQuizz::getTempo));

        String string = "";

        for (JogadorQuizz jogador : listaJogadores) {
            string += jogador.toString() + "\n";
        }

        return """
        - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
             NOME    PERGUNTAS CERTAS    TEMPO (seg.)    TENTATIVA
        """ +
                string
        + """
        - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        """;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private String[] retorna4OpcoesDeRespostas(int respostaCerta) {
        Random random = new Random();
        ArrayList<String> respostas = new ArrayList<>();
        String novaResposta;
        int posicao = 1;
        boolean jaExiste;

        respostas.add(respostaCerta+"");
        while (posicao < 4) {
            if (respostaCerta < 10) {
                novaResposta = random.nextInt(10) + "";
            } else {
                novaResposta = random.nextInt(respostaCerta) + "";
            }

            jaExiste = false;
            for (int i = 0; i < posicao; i++) {
                if (respostas.get(i).equals(novaResposta)) {
                    jaExiste = true;
                    break;
                }
            }

            if (!jaExiste) {
                respostas.add(novaResposta);
                posicao++;
            }
        }

        return aleatorizar4Respostas(respostas, respostaCerta+"");
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private String[] aleatorizar4Respostas(ArrayList<String> respostas, String respostaCerta) {
        Collections.shuffle(respostas);
        String formatoResposta = "";
        String opcaoRespostaCerta = "";

        for (int i = 0, j = 1; i < respostas.size(); i++, j++) {
            if (j % 2 == 0) {
                formatoResposta += j + "- " + respostas.get(i) + "\n";
            } else {
                formatoResposta += j + "- " + respostas.get(i) + "\t\t";
            }

            if (respostas.get(i).equals(respostaCerta)) {
                opcaoRespostaCerta = j+"";
            }
        }


        return new String[]{formatoResposta, opcaoRespostaCerta};
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private String[] aleatorizar4RespostasHashMap(ArrayList<String> respostas, String respostaCerta, HashMap<String, String> map, String frase1, String frase2) {
        Collections.shuffle(respostas);
        String formatoResposta = "";
        String opcaoRespostaCerta = "";

        for (int i = 0, j = 1; i < respostas.size(); i++, j++) {
            if (j % 2 == 0) {
                formatoResposta += j + "- " + respostas.get(i) + frase1 + map.get(respostas.get(i)) + frase2 + "\n";
            } else {
                formatoResposta += j + "- " + respostas.get(i) + frase1 + map.get(respostas.get(i)) + frase2 + "\t\t";
            }

            if (respostas.get(i).equals(respostaCerta)) {
                opcaoRespostaCerta = j+"";
            }
        }


        return new String[]{formatoResposta, opcaoRespostaCerta};
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private int[] aleatorizarDoisValores(int maiorValor, int menorValor) {
        Random random = new Random();
        int[] valores = new int[2];

        valores[0] = random.nextInt(maiorValor - menorValor + 1) + menorValor;
        do {
            valores[1] = random.nextInt(maiorValor - menorValor + 1) + menorValor;
        } while (valores[1] < valores[0]);

        return valores;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private int[] aleatorizar4NumerosDiferentes(int tamanhoArray) {
        Random random = new Random();
        int[] numerosAleatorios = new int[4];
        int numAleatorio;
        boolean jaExiste;
        int posicao = 0;

        while (posicao < 4) {
            jaExiste = false;
            numAleatorio = random.nextInt(tamanhoArray);

            for (int i = 0; i < posicao; i++) {
                if (numerosAleatorios[i] == numAleatorio) {
                    jaExiste = true;
                    break;
                }
            }

            if (!jaExiste) {
                numerosAleatorios[posicao] = numAleatorio;
                posicao++;
            }
        }

        return numerosAleatorios;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    private Filme[] aleatorizar4Filmes() {
        Filme[] filmesAleatorios = new Filme[4];
        int[] numerosAleatorios = aleatorizar4NumerosDiferentes(listaFilmes.size());

        for (int i = 0; i < 4; i++) {
            filmesAleatorios[i] = listaFilmes.get(numerosAleatorios[i]);
        }
        return filmesAleatorios;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    public ArrayList<String[]> gerar10Perguntas() {
        ArrayList<String[]> perguntasERespostas = new ArrayList<>();
        ArrayList<String> retorno = new ArrayList<>();
        Random random = new Random();
        Filme filme;
        Diretor diretor;
        Ator ator;
        String pergunta;
        String resposta;
        int ano;
        int valorRetorno;


        // Pergunta 1
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
        String mesFilme = filme.getMovieReleaseOnlyMonth();
        String anoFilme = filme.getMovieReleaseOnlyYear();
        valorRetorno = Main.countMoviesMonthYear(new String[]{"", mesFilme, anoFilme});
        pergunta = "Quantos filmes foram realizados no mês " + mesFilme + " do ano " + anoFilme + "?";
        String[] respostas = retorna4OpcoesDeRespostas(valorRetorno);
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 2
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        int maiorAno = listaFilmes.get(0).getMovieReleaseOnlyYearInt();
        int menorAno = listaFilmes.get(0).getMovieReleaseOnlyYearInt();
        int numMaisAtores = listaFilmes.get(0).getQuantAllActors();
        int numMenosAtores = listaFilmes.get(0).getQuantAllActors();
        int numAtores;
        for (Filme filmeAtual : listaFilmes) {
            ano = filmeAtual.getMovieReleaseOnlyYearInt();
            numAtores = filmeAtual.getQuantAllActors();

            if (maiorAno < ano) {
                maiorAno = ano;
            }
            if (menorAno > ano) {
                menorAno = ano;
            }
            if (numMaisAtores < numAtores) {
                numMaisAtores = numAtores;
            }
            if (numMenosAtores > numAtores) {
                numMenosAtores = numAtores;
            }
        }

        int[] anosAleatorio = aleatorizarDoisValores(maiorAno, menorAno);
        int[] numAtorAleatorio = aleatorizarDoisValores(numMaisAtores, numMenosAtores);

        valorRetorno = Main.countMoviesBetweenYearsWithNActors(new String[]{"", anosAleatorio[0]+"", anosAleatorio[1]+"", numAtorAleatorio[0]+"", numAtorAleatorio[1]+""});

        pergunta = "Quantos filmes foram realizados entre os anos " + anosAleatorio[0] + " e " + anosAleatorio[1] + ", e que tenha atuado entre " + numAtorAleatorio[0] + " e " + numAtorAleatorio[1] + " atores?";
        respostas = retorna4OpcoesDeRespostas(valorRetorno);
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 3
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
        pergunta = "Em que ano foi lançado o filme " + filme.getMovieName() + "?";
        respostas = retorna4OpcoesDeRespostas(filme.getMovieReleaseOnlyYearInt());
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 4
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        Filme[] filmesAleatorios = aleatorizar4Filmes();
        double maiorDuracao = 0.00;
        String nomeFilmeMaiorDuracao = "";
        double duracaoFilmeAtual;
        for (Filme filmeAtual : filmesAleatorios) {
            duracaoFilmeAtual = filmeAtual.getMovieDuration();
            if (maiorDuracao < duracaoFilmeAtual) {
                maiorDuracao = duracaoFilmeAtual;
                nomeFilmeMaiorDuracao = filmeAtual.getMovieName();
            }

            retorno.add(filmeAtual.getMovieName());
        }

        pergunta = "Qual desses filmes tem a maior duração?";
        respostas = aleatorizar4Respostas(retorno, nomeFilmeMaiorDuracao);
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 5
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        ator = listaAtores.get(random.nextInt(listaAtores.size()));
        ano = -1;
        for (Filme filmeAtual : listaFilmes) {
            if (filmeAtual.getAllActorsName().contains(ator.getActorName())) {
                ano = filmeAtual.getMovieReleaseOnlyYearInt();
                break;
            }
        }

        String nomeAtor = ator.getActorName();
        pergunta = "Qual filme, lançado em " + ano + ", o ator " + nomeAtor + " participou?";
        retorno = Main.getMoviesActorYear(new String[]{"", ano+"", ator.getActorName()});
        retorno.subList(1, retorno.size()).clear();

        for (Filme filmeAtual : listaFilmes) {
            if (!retorno.contains(filmeAtual.getMovieName())) {
                retorno.add(filmeAtual.getMovieName());
                if (retorno.size() == 4) {
                    break;
                }
            }
        }

        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 6
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        ator = listaAtores.get(random.nextInt(listaAtores.size()));

        pergunta = "Qual filme o ator " + ator.getActorName() + " participou?";
        retorno = Main.getMoviesWithActorContaining(new String[]{"", ator.getActorName()});
        retorno.subList(1, retorno.size()).clear();

        for (Filme filmeAtual : listaFilmes) {
            if (!retorno.contains(filmeAtual.getMovieName()) && !filmeAtual.getAllActorsName().contains(ator.getActorName())) {
                retorno.add(filmeAtual.getMovieName());
                if (retorno.size() == 4) {
                    break;
                }
            }
        }

        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        // Pergunta 7
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
        anoFilme = filme.getMovieReleaseOnlyYear();
        retorno = new ArrayList<>();

        pergunta = "Qual o mês com mais filmes lançados no ano " + anoFilme + "?";
        resposta = Main.topMonthMovieCount(new String[]{"", anoFilme});
        retorno.add(resposta.split(":")[0]);

        for (int i = 0; i < 13; i++) {
            if (!retorno.contains(i+"")) {
                retorno.add(i+"");
            }
            if (retorno.size() == 4) {
                break;
            }
        }

        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 8
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        int numGeneroAleatorio = random.nextInt(2);
        String genero = (numGeneroAleatorio == 0) ? "M" : "F";
        filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
        anoFilme = filme.getMovieReleaseOnlyYear();
        retorno = new ArrayList<>();

        ArrayList<String> temp = Main.topMoviesWithGender(new String[]{"", 1000+"", anoFilme, genero});
        retorno.add(temp.get(0));
        HashMap<String, String> numAtoresPorNomeFilme = new HashMap<>();
        ArrayList<String> nomeFilmes = new ArrayList<>();
        nomeFilmes.add(retorno.get(0).split(":")[0]);

        for (Filme filmeAtual : listaFilmes) {
            if (!nomeFilmes.contains(filmeAtual.getMovieName())) {
                numAtores = (genero.equals("M")) ? filmeAtual.getNumMaleActors() : filmeAtual.getNumFemaleActors();
                retorno.add(filmeAtual.getMovieName()+":"+numAtores);
                nomeFilmes.add(filmeAtual.getMovieName());
            }
            if (retorno.size() == 4) {
                break;
            }
        }

        String[] separa;
        temp = new ArrayList<>();
        for (String retornoAtual : retorno) {
            separa = retornoAtual.split(":");
            numAtoresPorNomeFilme.put(separa[0], separa[1]);
            temp.add(separa[0]);
        }

        pergunta = "Qual foi o filme lançado em " + anoFilme + " com mais atores do gênero " + genero + "?";

        respostas = aleatorizar4RespostasHashMap(temp, temp.get(0), numAtoresPorNomeFilme, ", com ", " atores");
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 9
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        Filme outroFilme = null;

        for (Filme filmeAtual : listaFilmes) {
            if (filmeAtual.getQuantAllActors() >= 11) {
                outroFilme = filmeAtual;
            }
        }
        if (outroFilme != null) {
            retorno = new ArrayList<>();
            temp = Main.topMoviesWithGenderBias(new String[]{"", 1000+"", outroFilme.getMovieReleaseOnlyYear()});

            retorno.add(temp.get(0).split(":")[0]);
            for (Filme filmeAtual : listaFilmes) {
                if (!retorno.contains(filmeAtual.getMovieName())) {
                    retorno.add(filmeAtual.getMovieName());
                }
                if (retorno.size() == 4) {
                    break;
                }
            }

            pergunta = "O filme " + outroFilme.getMovieName() + " é realizado por mais atores do sexo masculino ou feminino?";
            respostas = aleatorizar4Respostas(retorno, retorno.get(0));
            perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        }
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 10
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        long maiorOrcamento = 0;
        String nomeFilme = "";
        retorno = new ArrayList<>();

        for (Filme filmeAtual : listaFilmes) {
            if (maiorOrcamento < filmeAtual.getMovieBudget()) {
                maiorOrcamento = filmeAtual.getMovieBudget();
                nomeFilme = filmeAtual.getMovieName();
            }
        }

        retorno.add(nomeFilme);

        do {
            filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
            if (!retorno.contains(filme.getMovieName())) {
                retorno.add(filme.getMovieName());
            }
        } while (retorno.size() < 4);

        pergunta = "Qual o filme que teve o maior orçamento?";
        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 11
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        float maiorVotacao = 0.0f;
        retorno = new ArrayList<>();

        for (Filme filmeAtual : listaFilmes) {
            if (maiorVotacao < filmeAtual.getMovieVote()) {
                maiorVotacao = filmeAtual.getMovieVote();
                nomeFilme = filmeAtual.getMovieName();
            }
        }

        retorno.add(nomeFilme);

        do {
            filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
            if (!retorno.contains(filme.getMovieName())) {
                retorno.add(filme.getMovieName());
            }
        } while (retorno.size() < 4);

        pergunta = "Qual desses filmes teve a maior votação?";
        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 12
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        diretor = listaDiretores.get(random.nextInt(listaDiretores.size()));
        pergunta = "Quantos filme o diretor " + diretor.getDirectorName() + " já fez?";
        valorRetorno = Main.countMoviesDirector(new String[]{"", diretor.getDirectorName()});
        respostas = retorna4OpcoesDeRespostas(valorRetorno);
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 13
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        diretor = listaDiretores.get(random.nextInt(listaDiretores.size()));
        retorno = new ArrayList<>();
        int anoAleatorio = random.nextInt(11);

        temp = Main.getActorsByDirector(new String[]{"", anoAleatorio+"", diretor.getDirectorName()});

        if (temp.isEmpty()) {
            retorno.add("Nenhum");
        } else {
            for (String nome : temp) {
                retorno.add(nome.split(":")[0]);
            }
        }
        String nomesAtores = "";

        if (retorno.size() < 4) {
            do {
                filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
                if (!retorno.contains(filme.getAllDirectorsName().toString())) {
                    nomesAtores = "";
                    for (String nome : filme.getAllActorsName()) {
                        nomesAtores += nome + ", ";
                    }
                }
                retorno.add(nomesAtores);
            } while (retorno.size() < 4);
        }

        pergunta = "Quais os atores que já trabalharam " + anoAleatorio + " ou mais com o diretor " + diretor.getDirectorName() + "?";
        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 14
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        diretor = listaDiretores.get(random.nextInt(listaDiretores.size()));
        retorno = new ArrayList<>();

        for (Filme filmeAtual : listaFilmes) {
            if (filmeAtual.getAllDirectorsName().contains(diretor.getDirectorName())) {
                retorno.add(filmeAtual.getMovieName());
                break;
            }
        }

        do {
            filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
            if (!filme.getAllDirectorsName().contains(diretor.getDirectorName())) {
                retorno.add(filme.getMovieName());
            }
        } while (retorno.size() < 4);

        pergunta = "Qual desses filmes foi dirigido por " + diretor.getDirectorName() + "?";
        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 15
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        maiorAno = listaFilmes.get(0).getMovieReleaseOnlyYearInt();
        menorAno = listaFilmes.get(0).getMovieReleaseOnlyYearInt();

        for (Filme filmeAtual : listaFilmes) {
            ano = filmeAtual.getMovieReleaseOnlyYearInt();

            if (maiorAno < ano) {
                maiorAno = ano;
            }
            if (menorAno > ano) {
                menorAno = ano;
            }
        }

        anosAleatorio = aleatorizarDoisValores(maiorAno, menorAno);

        pergunta = "Quantos atores participaram em filmes lançados nos anos " + anosAleatorio[0] + " e " + anosAleatorio[1] + "?";
        valorRetorno = Main.countActorsIn2Years(new String[]{"", anosAleatorio[0] +"", anosAleatorio[1]+""});
        respostas = retorna4OpcoesDeRespostas(valorRetorno);
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 16
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        maiorAno = listaFilmes.get(0).getMovieReleaseOnlyYearInt();
        menorAno = listaFilmes.get(0).getMovieReleaseOnlyYearInt();
        retorno = new ArrayList<>();

        for (Filme filmeAtual : listaFilmes) {
            ano = filmeAtual.getMovieReleaseOnlyYearInt();

            if (maiorAno < ano) {
                maiorAno = ano;
            }
            if (menorAno > ano) {
                menorAno = ano;
            }
        }

        anoAleatorio = random.nextInt(maiorAno - menorAno + 1) + menorAno;

        temp = Main.topVotedActors(new String[]{"", 1000+"", anoAleatorio+""});
        String maiorVoto;
        if (temp.isEmpty()) {
            retorno.add("Nenhum");
            maiorVoto = random.nextInt(11)+".0";
        } else {
            maiorVoto = temp.get(0).split(":")[1];
            retorno.add(temp.get(0).split(":")[0]);
        }

        do {
            filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
            if (!filme.getAllActorsName().contains(retorno.get(0))) {
                retorno.add(filme.getMovieName());
            }
        } while (retorno.size() < 4);

        pergunta = "Quais atores participaram no filme de " + anoAleatorio + " com a classificação de " + maiorVoto + "?";
        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 16
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        Ator ator1 = listaAtores.get(random.nextInt(listaAtores.size()));
        Ator ator2 = listaAtores.get(random.nextInt(listaAtores.size()));
        valorRetorno = Main.distanceBetweenActors(new String[]{"", ator1.getActorName(), ator2.getActorName()});

        if (valorRetorno == 0) {
            valorRetorno++;
        } else {
            valorRetorno = 3;
        }
        pergunta = ator1.getActorName() + " já contracenou alguma vez com " + ator2.getActorName() + "?";
        resposta =  "1- Sim, no mesmo filme\t\t";
        resposta += "2- Não, mas eles têm um ator em que já trabalharam em comum\n";
        resposta +=  "3- Nenhuma das anteriores\n";
        perguntasERespostas.add(new String[]{pergunta, resposta, valorRetorno+""});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Pergunta 17
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        GeneroFilme generoo = listaGeneroFilmes.get(random.nextInt(listaGeneroFilmes.size()));
        retorno = new ArrayList<>();

        do {
            filme = listaFilmes.get(random.nextInt(listaFilmes.size()));
            if (retorno.isEmpty()) {
                if (filme.getGenerosAssociados().contains(generoo.getGenreName())) {
                    retorno.add(filme.getMovieName());
                }
                continue;
            }
            if (!filme.getGenerosAssociados().contains(generoo.getGenreName()) && !retorno.contains(filme.getMovieName())) {
                retorno.add(filme.getMovieName());
            }
        } while (retorno.size() < 4);

        pergunta = "Qual desses filmes pertence ao gênero " + generoo.getGenreName() + "?";
        respostas = aleatorizar4Respostas(retorno, retorno.get(0));
        perguntasERespostas.add(new String[]{pergunta, respostas[0], respostas[1]});
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        Collections.shuffle(perguntasERespostas);
        perguntasERespostas.subList(10, perguntasERespostas.size()).clear();

        return perguntasERespostas;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =



    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    public int verificarResposta(String respostaCorreta) {
        Scanner scanner = new Scanner(System.in);
        String respostaJogador;
        boolean opcaoValida;
        boolean acertouPergunta = false;

        do {
            System.out.print("> ");
            respostaJogador = scanner.nextLine();
            opcaoValida = respostaJogador.equals("1") || respostaJogador.equals("2") || respostaJogador.equals("3") || respostaJogador.equals("4");

            if (opcaoValida) {
                if (respostaJogador.equals(respostaCorreta)) {
                    System.out.println("Resposta certa");
                    acertouPergunta = true;
                } else {
                    System.out.println("Resposta errada");
                }
            } else {
                System.out.println("Resposta inválida");
            }
        } while (!opcaoValida);

        return (acertouPergunta) ? 1 : 0;
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
}
