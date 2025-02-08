package nova.task;

import java.time.LocalDateTime;

import nova.parser.Parser;


/**
 * Represent a task with a deadline.
 */
public class Deadline extends Task {
    private final LocalDateTime dueTime;

    /**
     * Initialises a new Deadline with the due date and time. Is automatically marked as incomplete.
     *
     * @param description Description of the deadline.
     * @param dueTime Due date and time of the deadline.
     */
    public Deadline(String description, LocalDateTime dueTime) {
        super(description);
        this.dueTime = dueTime;
    }


    /**
     * Constructs a Deadline object from retrieved saved information.
     *
     * @param description Description of the deadline.
     * @param dueTime Due date and time of the deadline.
     * @param isDone Completion status.
     */
    public Deadline(String description, LocalDateTime dueTime, boolean isDone) {
        super(description, isDone);
        this.dueTime = dueTime;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), Parser.outputDateTime(dueTime));
    }

    /**
     * Converts the task into a CSV format.
     *
     * @return a CSV representation in the order "<task type>,</task><completion status>,<description>".
     */
    @Override
    public String toCsv() {
        return "D," + super.toCsv() + "," + dueTime;
    }
}
