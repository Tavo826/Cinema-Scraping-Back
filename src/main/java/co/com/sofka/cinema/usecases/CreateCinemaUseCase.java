package co.com.sofka.cinema.usecases;

import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import co.com.sofka.cinema.domain.cinema.Cinema;
import co.com.sofka.cinema.domain.cinema.command.CreateCinema;
import co.com.sofka.cinema.domain.generic.DomainEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Dependent
public class CreateCinemaUseCase implements Function<CreateCinema, List<DomainEvent>> {

    @Override
    public List<DomainEvent> apply(CreateCinema command) {
        var cinema = new Cinema(command.getCinemaId(), command.getName());
        return cinema.getUncommittedChanges();
    }
}
