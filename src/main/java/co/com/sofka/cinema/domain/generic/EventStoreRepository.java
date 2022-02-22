package co.com.sofka.cinema.domain.generic;

import co.com.sofka.cinema.infra.generic.StoredEvent;
/*
    TODO: Error -> viola regla de la dependencia: el dominio está dependiendo de un elemento de la infraestructura
    Se soluciona con Inversión de Control IoC
*/

import java.util.List;

public interface EventStoreRepository {

    List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId);


    void saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent);

}