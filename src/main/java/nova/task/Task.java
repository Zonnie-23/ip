package nova.task;

import java.time.LocalDateTime;

/**
 * Represents a gentask with a description and a completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initialises a new Task that is marked as incomplete.
     *
     * @param description Description oftask.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a Task object from retrieved saved information.
     *
     * @param description Description of the task.
     * @param isDone Completion status.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param done true if the task is completed, false otherwise.
     */
    public void setStatus(boolean done) {
        isDone = done;
    }

    /**
     * Checks whether the task's description contains the specified keyword.
     * <p>
     * This method searches the task's description for the occurrence of the provided keyword.
     * It returns {@code true} if the keyword is found within the description, and {@code false} otherwise.
     * </p>
     *
     * @param keyword the keyword to search for within the description
     * @return {@code true} if the description contains the keyword, {@code false} otherwise
     */
    public boolean contains(String keyword) {
        return description.contains(keyword);
    }

    public abstract LocalDateTime getDateTime();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts task into a CSV format.
     *
     * @return a CSV representation in the order "<completion status>,<description>".
     */
    public String toCsv() {
        return String.format("%s,%s", (isDone ? "1" : "0"), description);
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
}
