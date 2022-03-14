package co.com.sofka.cinema.infra.materialize;

import co.com.sofka.cinema.domain.cinema.event.CinemaCreated;
import co.com.sofka.cinema.domain.cinema.event.MoviesAdded;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
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
    void consumeCinemaCreated(CinemaCreated event) {
        Map<String, Object> document = new HashMap<>();

        document.put("_id", event.getAggregateId());
        document.put("name", event.getName());

        mongoClient.getDatabase("queries")
                .getCollection("cinema")
                .insertOne(new Document(document));
    }

    @ConsumeEvent(value = "sofka.cinema.moviesadded", blocking = true)
    void consumeMoviesAdded(MoviesAdded event) {
        BasicDBObject document = new BasicDBObject();
        document.put("movies", event.getMovies());

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", document);

        mongoClient.getDatabase("queries")
                .getCollection("cinema")
                .updateOne(Filters.eq("_id", event.getAggregateId()), updateObject);
    }

}