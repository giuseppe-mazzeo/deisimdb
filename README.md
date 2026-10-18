# deisIMDB

An in-memory movie query engine written in **Java 17+**, built for the *Algorithms and Data Structures* (AED) course at **Universidade Lusófona de Lisboa** (academic year 2024/25).

The program loads six CSV files (movies, actors, directors, genres, genre-movie links and ratings), builds an in-memory model of roughly **44,000 movies, 91,000 actor entries and 47,000 director entries**, and answers a set of statistical queries typed into an interactive console. It also supports in-memory inserts and includes a custom, quiz-style command of my own design.

> **Final grade for the course: 17/20.** This project carried a large weight in that grade.

---

## Table of contents

- [About this repository](#about-this-repository)
- [Highlights](#highlights)
- [Tech stack](#tech-stack)
- [Project structure](#project-structure)
- [Dataset](#dataset)
- [Supported commands](#supported-commands)
- [Examples](#examples)
- [Design and data structures](#design-and-data-structures)
- [Creative command: `QUIZZ_TIME`](#creative-command-quizz_time)
- [Testing](#testing)
- [Getting started](#getting-started)
- [Performance](#performance)
- [Possible improvements](#possible-improvements)
- [Use of AI tools](#use-of-ai-tools)

---

## About this repository

This is a university project that I am sharing publicly as part of my **portfolio**, so recruiters and other professionals can see how I structure code, choose data structures and test my work.

A few notes on that:

- The assignment was a graded university project, and this is my own solution. If you are a student taking a similar course, please treat this as reference reading only and follow your institution's academic integrity rules. Submitting someone else's code as your own is plagiarism, and instructors do check.
- This repository does not reproduce the course statement. It only summarises what the program does.
- The CSV files at the repository root are the dataset used for development; `test-files/` contains small hand-made files used by the unit tests.

---

## Highlights

- **Complete CSV ingestion** with handling of malformed lines. Lines with a wrong number of fields are skipped; the program counts valid and invalid lines per file and records the first invalid line number (`INPUT_INVALIDO` report).
- **16 query and update commands** over the data, plus `HELP`, `QUIT` and a custom command.
- **Single-pass parsing** that builds per-movie lookup tables (`HashMap`/`HashSet`) so each `Filme` object already carries its actors, directors, genres, rating and male/female counters when queries run.
- **Unit tests with JUnit 5** (45 tests) covering parsing, `toString` formats, most query commands and the custom command's scoreboard.
- **No streams and no `java.util.concurrent`**, as required by the course: only classic collections, loops and comparators.
- **Interactive console** that prints how long each command took.

---

## Tech stack

| Area | Details |
|---|---|
| Language | Java (developed with IntelliJ IDEA; uses switch expressions and text blocks, so JDK 17 or newer) |
| Testing | JUnit 5 (Jupiter) |
| Build | Plain `javac` / IntelliJ module (no Maven or Gradle) |
| Collections | `ArrayList`, `HashMap`, `HashSet`, `Collections.sort`, `Comparator` |

---

## Project structure

```
.
├── AUTHORS.txt
├── README.md
├── usodeia.txt                  # AI-usage log and creative-command description
├── movies.csv, actors.csv, ...  # full dataset (6 CSV files)
├── test-files/                  # small CSV files used by the unit tests
└── src/pt/ulusofona/aed/deisimdb/
    ├── Main.java                # file parsing, command dispatcher, all queries, console loop
    ├── Result.java              # command result: success flag, error, result text
    ├── Filme.java               # movie entity with precomputed per-movie data
    ├── Ator.java                # actor entity
    ├── Diretor.java             # director entity
    ├── Genero.java              # genre entity
    ├── GeneroFilme.java         # genre <-> movie link
    ├── VotosFilme.java          # movie rating entity
    ├── TipoEntidade.java        # enum used by getObjects(...)
    ├── Quizz.java               # QUIZZ_TIME game logic
    ├── JogadorQuizz.java        # quiz player and scoreboard row
    └── test/testClasses/        # JUnit 5 test classes
```

---

## Dataset

The program expects six CSV files in the folder it loads from:

| File | Content |
|---|---|
| `movies.csv` | `movieId, movieName, movieDuration, movieBudget, movieReleaseDate` (dd-MM-yyyy) |
| `actors.csv` | `actorId, actorName, actorGender, movieId` |
| `directors.csv` | `directorId, directorName, movieId` |
| `genres.csv` | `genreId, genreName` |
| `genres_movies.csv` | `genreId, movieId` |
| `movie_votes.csv` | `movieId, movieRating, movieRatingCount` |

`parseFiles(File folder)` returns `false` if the folder does not exist or a required file is missing. Lines with the wrong number of fields are skipped and counted, and duplicated movie ids are ignored.

---

## Supported commands

Commands are typed one per line in the console and executed by `Main.execute(String)`, which returns a `Result` object (`success`, `error`, `result`). Multi-line results are separated by `\n`.

### Counting

| Command | Description |
|---|---|
| `COUNT_MOVIES_MONTH_YEAR <month> <year>` | Number of movies released in the given month and year. |
| `COUNT_MOVIES_DIRECTOR <full-name>` | Number of movies directed by the given director. |
| `COUNT_ACTORS_IN_2_YEARS <year-1> <year-2>` | Number of distinct actors who appear in movies released in **both** years. |
| `COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS <year-start> <year-end> <min> <max>` | Number of movies released strictly between the two years whose cast size is strictly between `min` and `max`. |

### Searching and rankings

| Command | Description |
|---|---|
| `GET_MOVIES_ACTOR_YEAR <year> <full-name>` | Movies from the given year featuring the actor, ordered by release date. |
| `GET_MOVIES_WITH_ACTOR_CONTAINING <name>` | Movies (alphabetical) with at least one actor whose name contains the given text. |
| `GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING <search-string>` | Up to four years with the most movies whose title contains the text, as `year:count`. Ties are broken by the earlier year. |
| `GET_ACTORS_BY_DIRECTOR <num> <full-name>` | Actors who worked with the director in at least `num` movies, as `actor:count`. |
| `TOP_MONTH_MOVIE_COUNT <year>` | The month with the most releases in the year, as `month:count`. |
| `TOP_VOTED_ACTORS <num> <year>` | Up to `num` actors ranked by the ratings of the year's movies they appear in, as `actor:rating`. |
| `TOP_MOVIES_WITH_MORE_GENDER <num> <year> <M\|F>` | Movies of the year with the most actors of the given gender, as `movie:count`. Ties are broken alphabetically. |
| `TOP_MOVIES_WITH_GENDER_BIAS <num> <year> [<min> <max>]` | Movies of the year with the most unbalanced male/female cast, as `movie:gender:percentage`. |
| `TOP_6_DIRECTORS_WITHIN_FAMILY <year-start> <year-end>` | Up to six directors who co-directed movies with another director sharing their last name, as `director:count`. |
| `DISTANCE_BETWEEN_ACTORS <actor-1>,<actor-2>` | `0` if the actors share a movie, `1` if they share a common co-star, otherwise `No results`. |

### In-memory updates

These change the data in memory only; the CSV files are never modified.

| Command | Description |
|---|---|
| `INSERT_ACTOR <id>;<name>;<gender>;<movie-id>` | Adds an actor to a movie. Returns `OK`, or `Erro` if the id already exists. |
| `INSERT_DIRECTOR <id>;<name>;<movie-id>` | Adds a director to a movie and returns the names of the movie's other directors. Returns `Erro` if the id already exists. |

### Utility

| Command | Description |
|---|---|
| `HELP` | Lists all available commands. |
| `QUIT` | Exits the program. |
| `QUIZZ_TIME [history\|play]` | The custom quiz command (see below). |

Invalid commands return `success = false` with an error message.

---

## Examples

Real output when running against the full dataset in the repository root:

```
> COUNT_MOVIES_MONTH_YEAR 11 1996
59

> COUNT_MOVIES_DIRECTOR King Vidor
30

> GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING the
2014:164
2012:162
2015:161
2011:160

> TOP_MONTH_MOVIE_COUNT 2002
1:161

> TOP_MOVIES_WITH_MORE_GENDER 3 2011 F
Real Steel:29
Footloose:17
The Dilemma:16

> TOP_6_DIRECTORS_WITHIN_FAMILY 1970 1994
Tulsi Ramsay:7
Shyam Ramsay:7
Paolo Taviani:6
Vittorio Taviani:6
Stephen Quay:5
Timothy Quay:5
```

---

## Design and data structures

**Parsing.** `parseFiles` reads each file once with a `BufferedReader`. While reading, it fills lookup tables keyed by `movieId`:

| Table | Purpose |
|---|---|
| `HashMap<Integer, HashSet<Ator>>` | actors of each movie |
| `HashMap<Integer, HashSet<Diretor>>` | directors of each movie |
| `HashMap<Integer, HashSet<String>>` | genre names of each movie (resolved through a `genreId -> genreName` map) |
| `HashMap<Integer, Float>` | rating of each movie |
| `HashSet<Integer>` | movie ids already seen, to ignore duplicates in `O(1)` instead of scanning the movie list |

When `movies.csv` is read, each `Filme` receives its actors, directors, genres and rating straight from those tables. This avoids nested loops over the large files, which would be quadratic.

**Precomputed data inside `Filme`.** The release day, month and year are split once in the constructor (also stored as `int`), and the male and female actor counts are maintained as the cast changes. Queries that filter by year or rank by cast gender therefore do not re-parse strings or re-count actors.

**Query pattern.** Most queries make one pass over the movie list, filter by the parameters, and accumulate results in a `HashMap` counter (`getOrDefault` + `put`). The keys are then copied into an `ArrayList` and sorted with a `Comparator` that applies the required tie-breaking rule (count descending, then year or name ascending), and finally truncated to the requested limit.

**Choices worth mentioning**

- `HashSet` for per-movie actors and directors, so a repeated row cannot count the same person twice.
- Sorting alphabetically first and then stably by count, which gives the "count descending, name ascending" order without a custom multi-field comparator.
- `INSERT_*` commands update both the global lists and the affected `Filme`, and refresh its cached `toString`, so later queries immediately reflect the change.
- The quiz reuses the same query functions (for example `countMoviesMonthYear`) to compute correct answers, so there is a single source of truth.

---

## Creative command: `QUIZZ_TIME`

A movie trivia game inspired by the TV show *Show do Milhão*, whose questions are generated from the loaded data.

| Command | What it does |
|---|---|
| `QUIZZ_TIME` | Shows the welcome screen and the available sub-commands. |
| `QUIZZ_TIME play` | Asks for a player name (max. 10 characters), then asks **10 questions picked at random from 18 question templates**. Each question has 3 or 4 options. Returning players are welcomed back and their attempt number increases. |
| `QUIZZ_TIME history` | Shows a scoreboard sorted by correct answers (descending), with the shortest time as tie-breaker. Columns: name, correct answers, time in seconds, attempt. |

Example questions generated from the full dataset:

```
1) How many movies were released in month 11 of 1996?
2) Which of these movies had the highest budget?
3) How many movies has the director King Vidor made?
4) Has Calvin Poon Yuen-Leung ever worked with Wanda De Jesus?
5) Which movie released in 2011 had the most female actors?
```

*(The game's prompts are written in Portuguese; they are translated here for readability. The full description and sample sessions are in [`usodeia.txt`](usodeia.txt).)*

---

## Testing

The project has **45 JUnit 5 tests** in `src/pt/ulusofona/aed/deisimdb/test/testClasses/`:

| Test class | Covers |
|---|---|
| `TestMain` | file parsing (missing folder, empty folder, repeated calls), `getObjects(...)` for every entity type, the invalid-input report, and the main count, get and top-N query commands, each run with different parameters |
| `TestFilme`, `TestAtor`, `TestDiretor`, `TestGenero`, `TestGeneroFilmes` | `toString` formats and entity behaviour |
| `TestQuizz` | the `QUIZZ_TIME history` scoreboard: empty state and ordering by score and time (the interactive `play` mode needs console input, so it is not unit-tested) |

Each query test calls `parseFiles` on the small files in `test-files/`, runs the command with different parameters, and asserts on the returned `Result`. The `INSERT_*`, `DISTANCE_BETWEEN_ACTORS` and `TOP_6_DIRECTORS_WITHIN_FAMILY` commands are not covered by unit tests yet.

---

## Getting started

**Requirements:** JDK 17 or newer.

```bash
# Compile (from the repository root)
mkdir -p out
javac -d out $(find src -name "*.java" ! -path "*/test/*")

# Run the interactive console
java -cp out pt.ulusofona.aed.deisimdb.Main
```

Run the program from the repository root so the relative paths resolve.

By default `main()` loads the small sample files in `test-files/`. To query the full dataset, change this line in `Main.main`:

```java
parseFiles(new File("test-files"));   // sample data
parseFiles(new File("."));            // full dataset in the repository root
```

Then type `HELP` to list the commands, for example:

```
> COUNT_MOVIES_DIRECTOR King Vidor
> QUIZZ_TIME play
> QUIT
```

**Running the tests:** open the project in IntelliJ IDEA (the module is configured with JUnit 5.8.1) and run the test classes. Tests must run with the repository root as the working directory because they read from `test-files/`.

---

## Performance

On the full dataset (about 44,000 movies), a cold run on a JDK 21 cloud sandbox gave approximately:

| Operation | Time |
|---|---|
| Parsing all six files and building the lookup tables | ~1.2 s |
| Most count and ranking queries | ~10–60 ms |
| `GET_MOVIES_WITH_ACTOR_CONTAINING` (substring match over every actor name) | ~0.6 s |
| `DISTANCE_BETWEEN_ACTORS` | ~0.3 s |

These are indicative, single-run numbers; they will vary by machine. The interactive console prints the exact time of every command you run.

---

## Possible improvements

I would approach these differently with more time:

- **Pre-built indexes.** Most queries still scan the whole movie list. Indexes by release year, month, actor name and director name would make lookups close to constant time at the cost of memory and a slightly slower load.
- **`DISTANCE_BETWEEN_ACTORS`** only distinguishes distance 0 and 1. A breadth-first search over an actor graph would support any distance.
- **Avoid defensive copies in hot paths.** `Filme.getAllActorsName()` builds a new `HashSet` on every call, which adds up inside loops.
- **Name parameters** are limited to names of up to three words, and the main loop reads the sample folder by default instead of taking the folder from the command line.
- **Build tooling.** Adding Maven or Gradle would make compiling and testing a one-command job.

---

## Use of AI tools

The course encouraged using LLMs as a complement and not as a replacement, with the instruction to ask for explanations and alternatives rather than finished code, and to evaluate the answers critically. I used **ChatGPT** for small, focused questions and documented each interaction (prompt, answer and my critical analysis) in [`usodeia.txt`](usodeia.txt). Examples:

- Printing an `ArrayList<String>` without brackets: compared `String.join`, manual loops and string replacement, and picked `String.join`.
- Iterating over a `HashMap`: compared iterating keys, values or entries, and picked `entrySet()` because I needed both key and value.
- A `ClassCastException` from casting `HashMap.values()` to a `HashSet`: learned the cast is invalid and built a new `HashSet<>(map.values())` instead.
- Percentage difference between two counts: compared formulas and adopted one based on `Math.max` and `100.0` to avoid integer division.

---

*Author: Giuseppe Mazzeo — Universidade Lusófona de Lisboa, 2024/25.*
