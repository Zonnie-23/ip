package task;

import java.time.LocalDateTime;

import parser.Parser;

/**
 *  Represents an event task with a start time and an end time.
 */
public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * Initialises a new Event with the start date and time and end date and time. Is automatically marked as
     * incomplete.
     *
     * @param description Description of the deadline.
     * @param startTime Start date and time of the event.
     * @param endTime  End date and time of the event.
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs an Event object from retrieved saved information.
     *
     * @param description Description of the deadline.
     * @param startTime Start date and time of the event.
     * @param endTime  End date and time of the event.
     * @param isDone Completion status.
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime,  boolean isDone) {
        super(description, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), Parser.outputDateTime(startTime), Parser.outputDateTime(endTime));
    }

    /**
     * Converts the task into a CSV format.
     *
     * @return a CSV representation in the order "<task type>,</task><completion status>,<description>".
     */
    @Override
    public String toCsv() {
        return "E," + super.toCsv() + "," + startTime + "," + endTime;
    }
}
