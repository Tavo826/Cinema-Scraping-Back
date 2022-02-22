package co.com.sofka.cinema.infra.entrypoint;

import co.com.sofka.cinema.domain.generic.DomainEvent;
import co.com.sofka.cinema.infra.generic.EventSerializer;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/retrieve/{correlationId}")
@ApplicationScoped
public class SocketService {

    private static final Logger LOG = Logger.getLogger(SocketService.class);

    /*Conexión entre el back y el front*/
    Map<String, Map<String, Session>> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("correlationId") String correlationId) {
        var map = sessions.getOrDefault(correlationId, new HashMap<>());
        map.put(session.getId(), session);
        sessions.put(correlationId, map);
    }

    @OnClose
    public void onClose(Session session, @PathParam("correlationId") String correlationId) {
        sessions.get(correlationId).remove(session.getId());
    }

    @OnError
    public void onError(Session session, @PathParam("correlationId") String correlationId, Throwable throwable) {
        sessions.get(correlationId).remove(session.getId());
        LOG.error("onError", throwable);
    }

    /*El back envía información al cliente con el id de correlación*/
    public void send(String correlationId, DomainEvent event){
        var message = EventSerializer.instance().serialize(event);
        if(sessions.containsKey(correlationId)){
            sessions.get(correlationId).values()
                    .forEach(session -> session.getAsyncRemote().sendObject(message));
        }
    }

}