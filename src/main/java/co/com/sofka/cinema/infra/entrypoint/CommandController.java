package co.com.sofka.cinema.infra.entrypoint;

import co.com.sofka.cinema.domain.cinema.command.CreateCinema;
import io.vertx.core.eventbus.EventBus;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/cinema")
public class CommandController {

    private final EventBus bus;

    public CommandController(EventBus bus) {
        this.bus = bus;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response executor(CreateCinema command) {
        bus.publish(command.getType(), command);
        return Response.ok().build();
    }

}