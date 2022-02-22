package co.com.sofka.cinema.domain.cinema.command;

import co.com.sofka.cinema.domain.generic.Command;

import java.util.List;
import java.util.Map;

public class CreateCinema extends Command {

    private String cinemaId;
    private Map<String, List<List<String>>> movies;

    public CreateCinema() {}

    public String getId() {
        return cinemaId;
    }

    public void setId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Map<String, List<List<String>>> getMovies() {
        return movies;
    }

    public void setMovies(Map<String, List<List<String>>> movies) {
        this.movies = movies;
    }

}
