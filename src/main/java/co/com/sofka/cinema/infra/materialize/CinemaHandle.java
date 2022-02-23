package co.com.sofka.cinema.infra.materialize;

import co.com.sofka.cinema.domain.cinema.event.CinemaCreated;
import com.mongodb.client.MongoClient;
import io.quarkus.vertx.ConsumeEvent;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CinemaHandle {
    private final MongoClient mongoClient;

    public CinemaHandle(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }


    @ConsumeEvent(value = "sofka.cinema.cinemacreated", blocking = true)
    void consumeProgramCreated(CinemaCreated event) {
        Map<String, Object> document = new HashMap<>();

        document.put("_id", event.getAggregateId());
        document.put("name", event.getName());

        mongoClient.getDatabase("queries")
                .getCollection("cinema")
                .insertOne(new Document(document));
    }

}