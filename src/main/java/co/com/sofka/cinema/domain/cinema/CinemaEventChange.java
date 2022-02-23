package co.com.sofka.cinema.domain.cinema;

import co.com.sofka.cinema.domain.cinema.event.CinemaCreated;
import co.com.sofka.cinema.domain.generic.EventChange;

import java.util.HashMap;

public class CinemaEventChange implements EventChange {

    protected CinemaEventChange(Cinema cinema) {
        listener((CinemaCreated event) -> {
            cinema.name = event.getName();
            cinema.movies = new HashMap<>();
        });
    }

}
