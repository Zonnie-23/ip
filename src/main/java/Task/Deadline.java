package Task;

public class Deadline extends Task {
    private String dueTime;

    public Deadline(String description, String dueTime) {
        super(description);
        this.dueTime = dueTime;
    }

    public Deadline(String description, boolean isDone, String time) {
        super(description, isDone);
        this.dueTime = time;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), dueTime);
    }

    @Override
    public String toCSV() {
        return "D," + super.toCSV() + "," + dueTime;
    }
}
