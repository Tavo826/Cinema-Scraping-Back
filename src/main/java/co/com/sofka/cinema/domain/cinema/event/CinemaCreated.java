package co.com.sofka.cinema.domain.cinema.event;

import co.com.sofka.cinema.domain.generic.DomainEvent;

import java.util.List;
import java.util.Map;

public class CinemaCreated extends DomainEvent {

    private final String cinemaId;
    private final String name;

    public CinemaCreated(String cinemaId, String name) {
        super("sofka.cinema.cinemacreated");
        this.cinemaId = cinemaId;
        this.name = name;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public String getName() {
        return name;
    }
}
