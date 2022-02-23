package co.com.sofka.cinema.infra.handle;

import co.com.sofka.cinema.domain.cinema.command.CreateCinema;
import co.com.sofka.cinema.infra.generic.UseCaseHandle;
import co.com.sofka.cinema.usecases.CreateCinemaUseCase;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateCinemaHandle extends UseCaseHandle {

    private final CreateCinemaUseCase useCase;

    public CreateCinemaHandle(CreateCinemaUseCase useCase) {
        this.useCase = useCase;
    }

    @ConsumeEvent(value = "sofka.cinema.create")
    void consumeBlocking(CreateCinema command) {
        var events = useCase.apply(command);
        process(command.getCinemaId(), events);
    }

}
