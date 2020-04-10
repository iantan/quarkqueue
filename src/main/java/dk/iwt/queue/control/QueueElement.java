package dk.iwt.queue.control;

import java.time.LocalDateTime;

public class QueueElement {

    private Integer number;
    private LocalDateTime start;

    public QueueElement() {
    }

    public QueueElement(Integer number) {
        this.number = number;
        this.start = LocalDateTime.now();
    }

    public Integer getNumber() {
        return number;
    }
    
    public LocalDateTime getStart() {
        return start;
    }

}
