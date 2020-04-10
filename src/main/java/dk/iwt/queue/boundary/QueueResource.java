package dk.iwt.queue.boundary;

import dk.iwt.queue.control.QuarkQueue;
import dk.iwt.queue.control.QueueElement;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/queue")
@Produces(MediaType.APPLICATION_JSON)
public class QueueResource {

    @Inject
    QueueService queueService;
    
    @PUT
    @Path("/add")
    public QueueElement add() {
        return queueService.add();
    }
    
    @GET
    public QuarkQueue getSize(){
        return queueService.getQueue();
    }
    
    @GET
    @Path("/peak")
    public QueueElement peak(){
        return queueService.peak();
    }

    @DELETE
    @Path("/poll")
    public QueueElement poll(){
        return queueService.poll();
    }

}
