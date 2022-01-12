package fr.umlv.movie;

import java.util.Objects;

public record ActorMovieCount(String actor, long movieCount) {
	public ActorMovieCount {
		Objects.requireNonNull(actor);
	}
}
