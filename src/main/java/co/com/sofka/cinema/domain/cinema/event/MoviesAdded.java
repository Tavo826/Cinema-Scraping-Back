package co.com.sofka.cinema.domain.cinema.event;

import co.com.sofka.cinema.domain.generic.DomainEvent;

import java.util.List;
import java.util.Map;

public class MoviesAdded extends DomainEvent {

    private final Map<String, List<List<String>>> movies;

    public MoviesAdded(Map<String, List<List<String>>> movies) {
        super("sofka.cinema.moviesadded");
        this.movies = movies;
    }

    public Map<String, List<List<String>>> getMovies() {
        return movies;
    }
}
