package co.com.sofka.cinema.infra.handle;

import co.com.sofka.cinema.domain.cinema.command.CreateCinema;
import co.com.sofka.cinema.infra.generic.UseCaseHandle;
import co.com.sofka.cinema.usecases.CreateCinemaUseCase;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateCinemaHandle extends UseCaseHandle {

    private CreateCinemaUseCase useCase;

    public CreateCinemaHandle(CreateCinemaUseCase useCase) {
        this.useCase = useCase;
    }

    /*Consume el comando publicado en el entrypoint ("/scrapmovie")*/
    @ConsumeEvent(value = "sofka.cinema.create")
    void consumeBlocking(CreateCinema command) {
        var events = useCase.apply(command);
        process(command.getType(), events);
    }

}
