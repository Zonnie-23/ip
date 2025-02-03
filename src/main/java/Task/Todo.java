package Task;

/**
 * Represents a simple Todo task.
 */
public class Todo extends Task {
    /**
     * Initialises a new Todo that is marked as incomplete.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a Todo object from retrieved saved information.
     *
     * @param description Description of the task.
     * @param isDone Completion status.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts the task into a CSV format.
     *
     * @return a CSV representation in the order "<task type>,</task><completion status>,<description>".
     */
    @Override
    public String toCsv() {
        return "T," + super.toCsv();
    }
}
