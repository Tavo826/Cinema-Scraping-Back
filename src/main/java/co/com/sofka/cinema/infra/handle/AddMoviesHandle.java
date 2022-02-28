package co.com.sofka.cinema.infra.handle;

import co.com.sofka.cinema.domain.cinema.command.AddMovies;
import co.com.sofka.cinema.infra.generic.UseCaseHandle;
import co.com.sofka.cinema.usecases.ExtractMoviesUseCase;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddMoviesHandle extends UseCaseHandle {

    private final ExtractMoviesUseCase useCase;

    public AddMoviesHandle(ExtractMoviesUseCase useCase) {
        this.useCase = useCase;
    }

    @ConsumeEvent(value = "sofka.cinema.addmovies")
    void consumeBlockingG(AddMovies command) {
        var events = useCase.apply(command);
        process(command.getCinemaId(), events);
    }

}
