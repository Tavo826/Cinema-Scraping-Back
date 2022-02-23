package co.com.sofka.cinema.domain.cinema.command;

import co.com.sofka.cinema.domain.generic.Command;

import java.util.List;
import java.util.Map;

public class CreateCinema extends Command {

    private String cinemaId;
    private String name;


    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
