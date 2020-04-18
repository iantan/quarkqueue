package dk.iwt.queue.boundary;

import dk.iwt.queue.control.QuarkQueue;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/queue-socket/{username}", encoders = {JsonEncoder.class})
@ApplicationScoped
public class QueueWebSocket {
    Map<String, Session> sessions = new ConcurrentHashMap<>(); 

    @Inject
    QueueService queueService;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        System.out.println("onOpen: " + username);
        sessions.put(username, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        System.out.println("onMessage: " + message);
        broadcast();
    }

    private void broadcast() {
        QuarkQueue queue = queueService.getQueue();
        System.out.println("broadcast: " + queue.getCounter());
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(parse(queue), result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
    
    private JsonObject parse(QuarkQueue queue){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        return builder.add("count", queue.getCounter())
                .add("lastestCustomerNumber", queue.getPeak().getNumber() - 1)
                .add("size", queue.getSize())
                .build();
    }
}
