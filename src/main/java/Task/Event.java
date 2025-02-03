package Task;

import Parser.Parser;

import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime,  boolean isDone) {
        super(description, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), Parser.outputDateTime(startTime), Parser.outputDateTime(endTime));
    }

    @Override
    public String toCsv() {
        return "E," + super.toCsv() + "," + startTime + "," + endTime;
    }
}
