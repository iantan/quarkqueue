package dk.iwt.queue.control;

import java.util.LinkedList;
import java.util.UUID;
import java.util.Queue;

public class QuarkQueue {

    private UUID id;
    private String name;
    private Integer counter;
    private Queue<QueueElement> queue;

    public QuarkQueue(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.counter = 0;
        this.queue = new LinkedList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCounter(){
        return counter;
    }
    
    public Integer getSize() {
        return queue.size();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public QueueElement add() {
        QueueElement element = new QueueElement(++counter);
        this.queue.add(element);
        return element;
    }

    public QueueElement poll() {
        return this.queue.poll();
    }

    public QueueElement peak() {
        return this.queue.peek();
    }
    
    public void reset() {
        this.counter = 0;
        this.queue = new LinkedList<>();
    }
    
}
