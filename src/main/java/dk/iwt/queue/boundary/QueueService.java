package dk.iwt.queue.boundary;

import dk.iwt.queue.control.QuarkQueue;
import dk.iwt.queue.control.QueueElement;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;
import javax.annotation.PostConstruct;

@ApplicationScoped
public class QueueService {

    private Map<UUID, QuarkQueue> queueMap;
    private QuarkQueue defaultQueue;
    private final String DEFAULT = "default"; 
    
    @PostConstruct
    public void init(){
        this.defaultQueue = new QuarkQueue(DEFAULT);
        
        this.queueMap = new HashMap<>();
        this.queueMap.put(defaultQueue.getId(), defaultQueue);
    }
    
    public QueueElement add(){
        return this.defaultQueue.add();
    }

    public QueueElement add(UUID uuid){
        QuarkQueue q = this.queueMap.get(uuid);
        if (q == null){
            throw new RuntimeException("Queue not found: " + uuid.toString());
        }
        return q.add();
    }
    
    public QuarkQueue getQueue() {
        return this.defaultQueue;
    }
    
    public QueueElement poll() {
        return this.defaultQueue.poll();
    }

    public QueueElement peak() {
        return this.defaultQueue.peak();
    }
    
    public void reset(){
        this.defaultQueue.reset();
    }

}
