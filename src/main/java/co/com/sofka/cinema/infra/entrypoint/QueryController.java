package co.com.sofka.cinema.infra.entrypoint;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

@Path("/api/cinema")
public class QueryController {

    private final MongoClient mongoClient;

    public QueryController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Response get() {
        List<Document> documentList = new ArrayList<>();
        Bson sort = descending("_id");
        mongoClient.getDatabase("queries")
                .getCollection("cinema")
                .find().sort(sort).limit(1)
                .forEach(documentList::add);
        return Response.ok(documentList.stream().findAny()).build();
    }

}
