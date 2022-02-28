package co.com.sofka.cinema.domain.cinema.command;

import co.com.sofka.cinema.domain.generic.Command;

import java.util.List;
import java.util.Map;

public class AddMovies extends Command {

    private String cinemaId;

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }
}
