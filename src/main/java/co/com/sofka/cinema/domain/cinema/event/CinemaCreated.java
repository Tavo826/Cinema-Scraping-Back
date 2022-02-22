package co.com.sofka.cinema.domain.cinema.event;

import co.com.sofka.cinema.domain.generic.DomainEvent;

import java.util.List;
import java.util.Map;

public class CinemaCreated extends DomainEvent {

    private final Map<String, List<List<String>>> movies;

    public CinemaCreated(Map<String, List<List<String>>> movies) {
        super("sofka.cinema.cinemacreated");
        this.movies = movies;
    }

    public Map<String, List<List<String>>> getMovies() {
        return movies;
    }
}
