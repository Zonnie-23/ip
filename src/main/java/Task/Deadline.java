package Task;

import Parser.Parser;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime dueTime;

    public Deadline(String description, LocalDateTime dueTime) {
        super(description);
        this.dueTime = dueTime;
    }

    public Deadline(String description, LocalDateTime dueTime, boolean isDone) {
        super(description, isDone);
        this.dueTime = dueTime;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), Parser.outputDateTime(dueTime));
    }

    @Override
    public String toCsv() {
        return "D," + super.toCsv() + "," + dueTime;
    }
}
