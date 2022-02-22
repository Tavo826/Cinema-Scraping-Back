package co.com.sofka.cinema.infra.generic;

import co.com.sofka.cinema.domain.generic.DomainEvent;
import co.com.sofka.cinema.domain.generic.EventStoreRepository;
import co.com.sofka.cinema.infra.message.BusService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public abstract class UseCaseHandle {

    @Inject
    private EventStoreRepository repository;

    @Inject
    private BusService busService;

    public void process(String cinemaId, List<DomainEvent> events) {
        events.stream().map(event -> {
            String eventBody = EventSerializer.instance().serialize(event);
            return new StoredEvent(event.getClass().getTypeName(), new Date(), eventBody);
        }).forEach(storedEvent -> repository.saveEvent("cinema", cinemaId, storedEvent));

        events.forEach(busService::send);
    }

}
