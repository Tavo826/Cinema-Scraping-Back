package co.com.sofka.cinema.domain.cinema;

import co.com.sofka.cinema.domain.cinema.event.CinemaCreated;
import co.com.sofka.cinema.domain.generic.AggregateRoot;
import co.com.sofka.cinema.domain.generic.DomainEvent;
import co.com.sofka.cinema.domain.generic.EventChange;

import java.util.List;
import java.util.Map;

public class Cinema extends AggregateRoot implements EventChange {

    protected Map<String, List<List<String>>> movies;
    protected String name;

    public Cinema(String cinemaId, String name) {
        super(cinemaId);
        subscribe(new CinemaEventChange(this));
        appendChange(new CinemaCreated(cinemaId, name)).apply();
    }

    private Cinema(String id) {
        super(id);
        subscribe(this);
    }

    public static Cinema from(String cinemaId, List<DomainEvent> events) {
        var movie = new Cinema(cinemaId);
        events.forEach(movie::applyEvent);
        return movie;
    }


    public Map<String, List<List<String>>> movies() {
        return movies;
    }

}
