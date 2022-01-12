package fr.umlv.movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Movies {
	public static List<Movie> movies(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
        	return lines.map(line -> {
        		var tokens = line.split(";");
        		var title = tokens[0];
        		var actors = Arrays.stream(tokens).skip(1).toList();
        		return new Movie(title, actors);
        	}).toList();
        }
    }
	
	public static Map<String, Movie> movieMap(List<Movie> movies) {
        return movies.stream()
            .collect(Collectors.toUnmodifiableMap(Movie::title, Function.identity()));
    }

    public static long numberOfUniqueActors(List<Movie> movies) {
        return movies.stream()
            .flatMap(movie -> movie.actors().stream())
            .distinct()
            .count();
    }

    public static Map<String, Long> numberOfMoviesByActor(List<Movie> movies) {
        return movies.stream()
            .flatMap(movie -> movie.actors().stream())
            .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
            ));
    }

    public static Optional<ActorMovieCount> actorInMostMovies(Map<String, Long> numberOfMoviesByActor) {
        return numberOfMoviesByActor.entrySet().stream()
            .collect(Collectors.maxBy(Comparator.comparing(Map.Entry::getValue)))
            .map(elem -> new ActorMovieCount(elem.getKey(), elem.getValue()));
    }
}
